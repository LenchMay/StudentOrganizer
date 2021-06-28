package lench.may.studentorganizer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import lench.may.studentorganizer.data.DBHelper;
import lench.may.studentorganizer.data.SubjContract;

public class Subjects extends AppCompatActivity {
    ListView subjList;
    DBHelper databaseHelper;
    SQLiteDatabase db;
    Cursor subjCursor;
    SimpleCursorAdapter subjAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjects);

        subjList = (ListView)findViewById(R.id.subjectsList);
        subjList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SubjectAddUpdDel.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        databaseHelper = new DBHelper(getApplicationContext());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.subjBtn);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.calendBtn: //переход на страницу "Расписание"
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.subjBtn: //текущая страница ("Занятия")
                        return true;
                    case R.id.tasksBtn: //переход на страницу "Задачи"
                        startActivity(new Intent(getApplicationContext(),Task.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.helpBtn: //переход на страницу "Помощь"
                        startActivity(new Intent(getApplicationContext(),Account.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
//fndk
    public void onResume() {
        super.onResume();
        db = databaseHelper.getReadableDatabase();

        subjCursor = db.rawQuery("select * from " + SubjContract.AddSubj.TABLE_NAME, null);
        String[] headers = new String[]{SubjContract.AddSubj.COLUMN_NAME};
        subjAdapter = new SimpleCursorAdapter(this, android.R.layout.activity_list_item,
                subjCursor, headers, new int[]{android.R.id.text1}, 0);
        subjList.setAdapter(subjAdapter);
    }

    public void add(View view){
        Intent intent = new Intent(this, SubjectAddUpdDel.class);
        startActivity(intent);
    }

    public void onDestroy(){
        super.onDestroy();
        db.close();
        subjCursor.close();
    }
}