package ir.madeinlobby.memoreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

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
        String userName = textUserName.getText().toString();
        String passWord = textPassword.getText().toString();
        String firstName = textFirstName.getText().toString();
        String lastName = textLastName.getText().toString();
    }
}