package lench.may.studentorganizer;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import lench.may.studentorganizer.data.DBHelper;
import lench.may.studentorganizer.data.SubjContract;

public class SubjectAddUpdDel extends AppCompatActivity {

    EditText nameBox;
    EditText pedBox;
    EditText roomBox;
    Button delButton;
    Button saveButton;
    TextView error;

    DBHelper sqlHelper;
    SQLiteDatabase db;
    Cursor subjCursor;
    long subjId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_add_upd_del);

        nameBox = (EditText) findViewById(R.id.SubjName);
        pedBox = (EditText) findViewById(R.id.SubjPed);
        roomBox = (EditText) findViewById(R.id.subjRoom);
        delButton = (Button) findViewById(R.id.buttonDel);
        saveButton = (Button) findViewById(R.id.buttonAdd);
        error = (TextView) findViewById(R.id.textError);

        error.setVisibility(View.GONE);
        sqlHelper = new DBHelper(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            subjId = extras.getLong("id");
        }
        // если 0, то добавление
        if (subjId > 0){
            // получаем элемент по id из бд
            subjCursor = db.rawQuery("select * from " + SubjContract.AddSubj.TABLE_NAME + " where " +
                    SubjContract.AddSubj._ID + "=?", new String[]{String.valueOf(subjId)});
            subjCursor.moveToFirst();
            nameBox.setText(subjCursor.getString(1));
            pedBox.setText(subjCursor.getString(2));
            roomBox.setText(subjCursor.getString(3));
            subjCursor.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }
    }
    public void save(View view) {
        ContentValues cv = new ContentValues();

        String check = nameBox.getText().toString();

        if (check.replaceAll(" ", "").length() == 0) {
            error.setVisibility(View.VISIBLE);
        } else {
            cv.put(SubjContract.AddSubj.COLUMN_NAME, nameBox.getText().toString());
            cv.put(SubjContract.AddSubj.COLUMN_PED, pedBox.getText().toString());
            cv.put(SubjContract.AddSubj.COLUMN_ROOM, roomBox.getText().toString());

            if (subjId > 0) {
                db.update(SubjContract.AddSubj.TABLE_NAME, cv, SubjContract.AddSubj._ID + "=" + String.valueOf(subjId), null);
            } else {
                db.insert(SubjContract.AddSubj.TABLE_NAME, null, cv);
            }
            goHome();
        }
    }

    public void delete(View view){
        db.delete(SubjContract.AddSubj.TABLE_NAME, "_ID = ?", new String[]{String.valueOf(subjId)});
        goHome();
    }

    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, Subjects.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}