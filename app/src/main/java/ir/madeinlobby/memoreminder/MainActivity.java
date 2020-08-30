package ir.madeinlobby.memoreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import ir.madeinlobby.memoreminder.model.Tag;
import ir.madeinlobby.memoreminder.utilities.BaseController;
import ir.madeinlobby.memoreminder.utilities.HttpUtility;

public class MainActivity extends AppCompatActivity {
    public static Activity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.register);
        SpannableString content = new SpannableString("create an account");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);
//        Intent intent = new Intent(MainActivity.this, MainPage.class);
//        startActivity(intent);
    }

    public void registerPressed(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void loginClicked(View view) {
        EditText textUserName = findViewById(R.id.usernameForLogin);
        EditText textPassword = findViewById(R.id.passwordForLogin);
        String userName = textUserName.getText().toString();
        String password = textPassword.getText().toString();
        final HashMap<String, String> fields = new HashMap<>();
        fields.put("username", userName);
        fields.put("password", password);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = HttpUtility.sendPostRequest(BaseController.server + "/login.php", fields);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(MainActivity.this, getString(R.string.error_for_login));
//                            Toast.makeText(MainActivity.this, "invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    String token = response.substring(4);
                    BaseController.setToken(token.trim());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            getTags();
                            Toast.makeText(MainActivity.this, getString(R.string.login_successful), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, MainPage.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }).start();
    }

    private void getTags() {
        final HashMap<String, String> fields2 = new HashMap<>();
        fields2.put("token", BaseController.getToken());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = HttpUtility.sendPostRequest(BaseController.server + "/getTags.php", fields2);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(MainActivity.this, getString(R.string.error_connection_server));
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<Tag> tags = new Gson().fromJson(response, new TypeToken<ArrayList<Tag>>() {
                            }.getType());
                            BaseController.getTags().clear();
                            BaseController.getTags().addAll(tags);
                            Toast.makeText(MainActivity.this, getString(R.string.login_successful), Toast.LENGTH_LONG).show();
                            Log.d("test","hi");
                            Intent intent = new Intent(MainActivity.this, MainPage.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }).start();
    }
}