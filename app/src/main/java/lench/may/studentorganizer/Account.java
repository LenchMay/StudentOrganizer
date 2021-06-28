package lench.may.studentorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
//dgfds
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.helpBtn);
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
                        startActivity(new Intent(getApplicationContext(),Task.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.helpBtn:
                        return true;
                }
                return false;
            }
        });

    }
}