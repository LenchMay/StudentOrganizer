package lench.may.studentorganizer;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

import lench.may.studentorganizer.data.DBHelper;
import lench.may.studentorganizer.data.SubjContract;
import lench.may.studentorganizer.data.ScheduleContract;

public class LessonAddUpdDel extends AppCompatActivity {
    //дни недели в выпадающем списке
    String[] daysOfTheWeek = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};
    Integer[] hours = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    Integer[] minutes = {00, 05, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
    //выпадающие списки
    Spinner spinnerDays;
    Spinner spinnerSubj;
    //время
    Spinner chooseStartTimeH;
    Spinner chooseStartTimeM;
    Spinner chooseEndTimeH;
    Spinner chooseEndTimeM;
    Calendar dateAndTime = Calendar.getInstance();
    //кнопки
    Button delButton;
    Button saveButton;
    //сообщение об ошибке
    TextView error;
    //бд
    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor lessonCursor;
    long lessonId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_add_upd_del);

        //привязка переменных к элментам на странице
        spinnerDays = (Spinner) findViewById(R.id.daysSpinner);
        spinnerSubj = (Spinner) findViewById(R.id.subjectSpinner);
        chooseStartTimeH = (Spinner) findViewById(R.id.hStart);
        chooseStartTimeM = (Spinner) findViewById(R.id.mStart);
        chooseEndTimeH = (Spinner) findViewById(R.id.hEnd);
        chooseEndTimeM = (Spinner) findViewById(R.id.mEnd);
        saveButton = (Button) findViewById(R.id.buttonAdd);
        delButton = (Button) findViewById(R.id.buttonDel);
        //setStartTime();
        //setEndTime();

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapterDays = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, daysOfTheWeek);
        // Определяем разметку для использования при выборе элемента
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinnerDays.setAdapter(adapterDays);

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<Integer> adapterHours = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, hours);
        // Определяем разметку для использования при выборе элемента
        adapterHours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        chooseStartTimeH.setAdapter(adapterHours);
        chooseEndTimeH.setAdapter(adapterHours);

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<Integer> adapterMin = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, minutes);
        // Определяем разметку для использования при выборе элемента
        adapterMin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        chooseStartTimeM.setAdapter(adapterMin);
        chooseEndTimeM.setAdapter(adapterMin);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        ArrayList<String> listSubj = dbHelper.getAllNames();
        ArrayAdapter<String> adapterSubj = new ArrayAdapter<String>(this, R.layout.spinner_layout,R.id.text1,listSubj);
        spinnerSubj.setAdapter(adapterSubj);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            lessonId = extras.getLong("id");
        }
        // если 0, то добавление
        if (lessonId > 0){
            // получаем элемент по id из бд
            lessonCursor = db.rawQuery("select * from " + SubjContract.AddSubj.TABLE_NAME + " where " +
                    SubjContract.AddSubj._ID + "=?", new String[]{String.valueOf(lessonId)});
            lessonCursor.moveToFirst();
            spinnerDays.setSelection(0);
            spinnerSubj.setSelection(0);
            chooseStartTimeH.setSelection(8);
            chooseStartTimeM.setSelection(0);
            chooseEndTimeH.setSelection(9);
            chooseEndTimeM.setSelection(0);
            lessonCursor.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }
    /*
    // установка начального времени
    private void setStartTime(){
        chooseStartTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
    }
    private void setEndTime(){
        chooseEndTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
    }

    // отображаем диалоговое окно для выбора времени
    public void setTime(View v){
        new TimePickerDialog(LessonAddUpdDel.this, t, dateAndTime.get(Calendar.HOUR_OF_DAY), dateAndTime.get(Calendar.MINUTE), true).show();
    }
    public void setEndTime(View v){
        new TimePickerDialog(LessonAddUpdDel.this, et, dateAndTime.get(Calendar.HOUR_OF_DAY), dateAndTime.get(Calendar.MINUTE), true).show();
    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setStartTime();
        }
    };
    TimePickerDialog.OnTimeSetListener et = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setEndTime();
        }
    };
*/
    public void save(View view) {
        ContentValues cv = new ContentValues();
        cv.put(ScheduleContract.AddLesson.COLUMN_DAY, spinnerDays.getSelectedItem().toString());
        cv.put(ScheduleContract.AddLesson.COLUMN_SUBJ, spinnerSubj.getSelectedItem().toString());
        cv.put(ScheduleContract.AddLesson.COLUMN_START_H, (Integer) chooseStartTimeH.getSelectedItem());
        cv.put(ScheduleContract.AddLesson.COLUMN_START_M, (Integer) chooseStartTimeM.getSelectedItem());
        cv.put(ScheduleContract.AddLesson.COLUMN_END_H, (Integer) chooseEndTimeH.getSelectedItem());
        cv.put(ScheduleContract.AddLesson.COLUMN_END_M, (Integer) chooseEndTimeM.getSelectedItem());

        if (lessonId > 0) {
            db.update(ScheduleContract.AddLesson.TABLE_NAME, cv, ScheduleContract.AddLesson._ID + "=" + String.valueOf(lessonId), null);
        } else {
            db.insert(ScheduleContract.AddLesson.TABLE_NAME, null, cv);
        }
        goHome();
    }

    public void delete(View view){
        db.delete(ScheduleContract.AddLesson.TABLE_NAME, "_ID = ?", new String[]{String.valueOf(lessonId)});
        goHome();
    }

    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}