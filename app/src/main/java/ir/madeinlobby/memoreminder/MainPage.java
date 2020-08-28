package ir.madeinlobby.memoreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.posts:
                        posts();
                        return true;

                    case R.id.contacts:
                        contacts();
                        return true;

                    case R.id.tags:
                        showTagsPage();
                        return true;
                    case R.id.tagged:
                        showTaggedPage();
                        return true;

                    case R.id.logout:
                        logout();
                        return true;

                }
                return false;
            }
        });
    }

    private void showTaggedPage() {
    }

    private void showTagsPage() {
    }

    private void contacts() {
    }

    private void posts() {
    }

    private void logout() {
        finish();
    }
}