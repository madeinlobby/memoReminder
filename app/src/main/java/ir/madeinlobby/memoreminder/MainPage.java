package ir.madeinlobby.memoreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import petrov.kristiyan.colorpicker.ColorPicker;

public class MainPage extends AppCompatActivity {
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        scrollView = findViewById(R.id.main_layout);
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
        LayoutInflater inflater;
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.tags_page , null);
        scrollView.addView(layout);
    }

    public void colorPickerClicked(View view){
        ColorPicker colorPicker = new ColorPicker(MainPage.this);
        colorPicker.setRoundColorButton(true);
        colorPicker.setColorButtonMargin(10,10,10,10);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position,int color) {
                
            }

            @Override
            public void onCancel(){
                // put code
            }
        });
    }

    private void contacts() {
    }

    private void posts() {
    }

    private void logout() {
        finish();
    }
}