package ir.madeinlobby.memoreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;

import ir.madeinlobby.memoreminder.utilities.BaseController;
import ir.madeinlobby.memoreminder.utilities.HttpUtility;

public class MainActivity extends AppCompatActivity {
    private static final String server = "http://10.0.2.2/memoReminder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.register);
        SpannableString content = new SpannableString( "create an account" ) ;
        content.setSpan( new UnderlineSpan() , 0 , content.length() , 0 ) ;
        textView.setText(content) ;
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
                String response = HttpUtility.sendPostRequest(server + "/login.php", fields);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(MainActivity.this,"invalid username or password");
//                            Toast.makeText(MainActivity.this, "invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    String token = response.substring(4);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "login successful", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }
}