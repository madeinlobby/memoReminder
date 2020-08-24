package ir.madeinlobby.memoreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

import ir.madeinlobby.memoreminder.utilities.HttpUtility;

public class RegisterActivity extends AppCompatActivity {
    private static final String server = "localhost:80";

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
        try {
            HttpUtility.openConnection(server + "/register.php");
            HashMap<String, String> fields = new HashMap<>();
            fields.put("username", userName);
            fields.put("password", password);
            fields.put("firstName", firstName);
            fields.put("lastName", lastName);
            fields.put("email", email);
            HttpUtility.sendPostRequest(fields);
            String response = HttpUtility.readResponse();
            if (response.startsWith("error")) {
                if (response.equals("error: register_error, username_already_taken")) {
                    Toast.makeText(RegisterActivity.this, "username taken already", Toast.LENGTH_LONG).show();
                } else if (response.equals("error: register_error, empty_required_field")) {
                    Toast.makeText(RegisterActivity.this, "every fields should be filled", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(RegisterActivity.this, "invalid username or password", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(RegisterActivity.this, "register successful successful", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        } catch (IOException e) {
            Toast.makeText(RegisterActivity.this, "error in opening connection", Toast.LENGTH_LONG).show();
        }
    }
}