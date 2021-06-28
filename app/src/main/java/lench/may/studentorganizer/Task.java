package lench.may.studentorganizer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import lench.may.studentorganizer.data.DBHelper;
import lench.may.studentorganizer.data.TaskContract;

public class Task extends AppCompatActivity{
    ListView tasksList;
    DBHelper databaseHelper;
    SQLiteDatabase db;
    Cursor taskCursor;
    SimpleCursorAdapter taskAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);

        tasksList = (ListView)findViewById(R.id.taskList);
        tasksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), TaskAddUpdDel.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        databaseHelper = new DBHelper(getApplicationContext());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.tasksBtn);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.calendBtn:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.subjBtn:
                        startActivity(new Intent(getApplicationContext(),Subjects.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.tasksBtn:
                        return true;
                    case R.id.helpBtn:
                        startActivity(new Intent(getApplicationContext(),Account.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    public void onResume() {
        super.onResume();
        db = databaseHelper.getReadableDatabase();

        taskCursor = db.rawQuery("select * from " + TaskContract.AddTask.TABLE_NAME, null);
        String[] headers = new String[]{TaskContract.AddTask.COLUMN_NAME};
        taskAdapter = new SimpleCursorAdapter(this, android.R.layout.activity_list_item,
                taskCursor, headers, new int[]{android.R.id.text1}, 0);
        tasksList.setAdapter(taskAdapter);
    }

    public void add(View view){
        Intent intent = new Intent(this, TaskAddUpdDel.class);
        startActivity(intent);
    }

    public void onDestroy(){
        super.onDestroy();
        db.close();
        taskCursor.close();
    }
}





