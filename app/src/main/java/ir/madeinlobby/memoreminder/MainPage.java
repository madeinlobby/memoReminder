package ir.madeinlobby.memoreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;


import com.azeesoft.lib.colorpicker.ColorPickerDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

import ir.madeinlobby.memoreminder.utilities.BaseController;
import ir.madeinlobby.memoreminder.utilities.HttpUtility;


public class MainPage extends AppCompatActivity {
    ScrollView scrollView;
    String tagColor = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        scrollView = findViewById(R.id.main_layout);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPart, new HomePageFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new HomePageFragment();
                        break;
                    case R.id.nav_contacts:
                        contacts();
                        break;
                    case R.id.nav_tags:
                        selectedFragment = new TagsPageFragment();
                        break;
                    case R.id.nav_tagged:
                        showTaggedPage();
                        break;

                    case R.id.nav_logout:
//                        scrollView.removeAllViews();
                        logout();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPart, selectedFragment).commit();
                return true;
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
        if (tagColor.equals("")) {
            BaseController.showError(MainPage.this, getString(R.string.error_for_pick_color));
            return;
        }
        EditText editText = findViewById(R.id.tagName);
        String tagTitle = editText.getText().toString();
        final HashMap<String, String> fields = new HashMap<>();
        fields.put("token", BaseController.getToken());
        fields.put("text", tagTitle);
        fields.put("color", tagColor);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = HttpUtility.sendPostRequest(BaseController.server + "/addTag.php", fields);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(MainPage.this, getString(R.string.error_for_add_tag));
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainPage.this, getString(R.string.tag_added_sucessful), Toast.LENGTH_LONG).show();
                            tagColor = "";
                        }
                    });
                }
            }
        }).start();
        //sendTag
    }

    private void contacts() {
    }

    private void posts() {
    }

    private void logout() {
        BaseController.setToken("");
        finish();
    }
}