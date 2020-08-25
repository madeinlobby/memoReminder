package ir.madeinlobby.memoreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

import ir.madeinlobby.memoreminder.utilities.BaseController;
import ir.madeinlobby.memoreminder.utilities.HttpUtility;

public class RegisterActivity extends AppCompatActivity {
    private static final String server = "http://10.0.2.2/memoReminder";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onRegisterClicked(View view) {
        EditText textUserName = findViewById(R.id.usernameForRegister);
        EditText textPassword = findViewById(R.id.passwordForRegister);
        EditText textFirstName = findViewById(R.id.firstNameForRegister);
        EditText textLastName = findViewById(R.id.lastNameForRegister);
        EditText textEmail = findViewById(R.id.emailForRegister);
        String userName = textUserName.getText().toString();
        String password = textPassword.getText().toString();
        String firstName = textFirstName.getText().toString();
        String lastName = textLastName.getText().toString();
        String email = textEmail.getText().toString();
        final HashMap<String, String> fields = new HashMap<>();
        fields.put("username", userName);
        fields.put("password", password);
        fields.put("firstName", firstName);
        fields.put("lastName", lastName);
        fields.put("email", email);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = HttpUtility.sendPostRequest(server + "/register.php",fields);
                if (response.startsWith("error")) {
                    if (response.startsWith("error: register_error, username_already_taken")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BaseController.showError(RegisterActivity.this,"username taken already");
//                                Toast.makeText(RegisterActivity.this, "username taken already", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else if (response.startsWith("error: register_error, empty_required_field")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BaseController.showError(RegisterActivity.this,"every fields should be filled");
//                                Toast.makeText(RegisterActivity.this, "every fields should be filled", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "register was successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }).start();
    }
}