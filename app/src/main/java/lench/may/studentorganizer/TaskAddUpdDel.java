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
import lench.may.studentorganizer.data.TaskContract;

public class TaskAddUpdDel extends AppCompatActivity {

    EditText nameBox;
    EditText descriptionBox;
    Button delButton;
    Button saveButton;
    TextView error;

    DBHelper sqlHelper;
    SQLiteDatabase db;
    Cursor taskCursor;
    long taskId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_add_upd_del);

        nameBox = (EditText) findViewById(R.id.TaskName);
        descriptionBox = (EditText) findViewById(R.id.TaskDescr);
        delButton = (Button) findViewById(R.id.buttonDel);
        saveButton = (Button) findViewById(R.id.buttonAdd);
        error = (TextView) findViewById(R.id.textError);

        error.setVisibility(View.GONE);
        sqlHelper = new DBHelper(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            taskId = extras.getLong("id");
        }
        if (taskId > 0){
            taskCursor = db.rawQuery("select * from " + TaskContract.AddTask.TABLE_NAME + " where " +
                    TaskContract.AddTask._ID + "=?", new String[]{String.valueOf(taskId)});
            taskCursor.moveToFirst();
            nameBox.setText(taskCursor.getString(1));
            descriptionBox.setText(taskCursor.getString(2));
            taskCursor.close();
        } else {
            delButton.setVisibility(View.GONE);
        }
    }
    public void save(View view) {
        ContentValues cv = new ContentValues();
        String check = nameBox.getText().toString();

        if (check.replaceAll(" ", "").length() == 0) {
            error.setVisibility(View.VISIBLE);
        } else {
            cv.put(TaskContract.AddTask.COLUMN_NAME, nameBox.getText().toString());
            cv.put(TaskContract.AddTask.COLUMN_DESCRIPTION, descriptionBox.getText().toString());

            if (taskId > 0) {
                db.update(TaskContract.AddTask.TABLE_NAME, cv, TaskContract.AddTask._ID + "=" + String.valueOf(taskId), null);
            } else {
                db.insert(TaskContract.AddTask.TABLE_NAME, null, cv);
            }
            goHome();
        }
    }

    public void delete(View view){
        db.delete(TaskContract.AddTask.TABLE_NAME, "_ID = ?", new String[]{String.valueOf(taskId)});
        goHome();
    }

    private void goHome(){
        db.close();
        Intent intent = new Intent(this, Task.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}