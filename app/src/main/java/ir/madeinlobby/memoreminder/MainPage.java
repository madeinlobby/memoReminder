package ir.madeinlobby.memoreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.azeesoft.lib.colorpicker.ColorPickerDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainPage extends AppCompatActivity {
    ScrollView scrollView;
    String tagColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        scrollView = findViewById(R.id.main_layout);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.posts:
                        scrollView.removeAllViews();
                        posts();
                        return true;

                    case R.id.contacts:
                        scrollView.removeAllViews();
                        contacts();
                        return true;

                    case R.id.tags:
                        scrollView.removeAllViews();
                        showTagsPage();
                        return true;
                    case R.id.tagged:
                        scrollView.removeAllViews();
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
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.tags_page, null);
        scrollView.addView(layout);
    }

    public void colorPickerClicked(View view) {
        ColorPickerDialog colorPickerDialog = ColorPickerDialog.createColorPickerDialog(MainPage.this, R.style.AppTheme);
        colorPickerDialog.setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
            @Override
            public void onColorPicked(int color, String hexVal) {
                Log.d("color", hexVal);
                tagColor = hexVal;
            }
        });
        colorPickerDialog.show();
    }

    public void addTag(View view) {
        EditText editText = findViewById(R.id.tagName);
        String tagTitle = editText.getText().toString();

    }

    private void contacts() {
    }

    private void posts() {
    }

    private void logout() {
        finish();
    }
}