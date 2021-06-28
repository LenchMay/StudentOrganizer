package lench.may.studentorganizer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import lench.may.studentorganizer.data.DBHelper;
import lench.may.studentorganizer.data.ScheduleContract;

public class MainThu extends AppCompatActivity {
    ListView lessonsList;
    SQLiteDatabase db;
    DBHelper databaseHelper;
    Cursor lessonsCursor;
    SimpleCursorAdapter lessonsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_thu);

        lessonsList = (ListView)findViewById(R.id.lessonsList);
        lessonsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), LessonAddUpdDel.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        databaseHelper = new DBHelper(getApplicationContext());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.calendBtn);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.calendBtn:
                        return true;
                    case R.id.subjBtn:
                        startActivity(new Intent(getApplicationContext(),Subjects.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.tasksBtn:
                        startActivity(new Intent(getApplicationContext(),Task.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.helpBtn:
                        startActivity(new Intent(getApplicationContext(),Account.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.actionBtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LessonAddUpdDel.class));
                overridePendingTransition(0,0);
            }
        });
    }

    public void onResume() {
        super.onResume();
        db = databaseHelper.getReadableDatabase();

        lessonsCursor = db.rawQuery("select * from " + ScheduleContract.AddLesson.TABLE_NAME + " where "
                + ScheduleContract.AddLesson.COLUMN_DAY + " like 'Четверг' order by " + ScheduleContract.AddLesson.COLUMN_START_H, null);
        String[] headers = new String[]{ScheduleContract.AddLesson.COLUMN_SUBJ};
        lessonsAdapter = new SimpleCursorAdapter(this, android.R.layout.activity_list_item,
                lessonsCursor, headers, new int[]{android.R.id.text1}, 0);
        lessonsList.setAdapter(lessonsAdapter);
    }

    public void onDestroy(){
        super.onDestroy();
        db.close();
        lessonsCursor.close();
    }
}