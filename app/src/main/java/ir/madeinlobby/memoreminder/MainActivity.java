package ir.madeinlobby.memoreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

import ir.madeinlobby.memoreminder.utilities.HttpUtility;

public class MainActivity extends AppCompatActivity {
    private static final String server="localhost:80";

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
        try {
            HttpUtility.openConnection(server+"/login.php");
            HashMap<String,String> fields=new HashMap<>();
            fields.put("username",userName);
            fields.put("password",password);
            HttpUtility.sendPostRequest(fields);
            String response = HttpUtility.readResponse();
            if (response.startsWith("error")){
                Toast.makeText(MainActivity.this,"invalid username or password",Toast.LENGTH_LONG).show();
            }else{
                String token = response.substring(4);
                Toast.makeText(MainActivity.this,"login successful",Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            Toast.makeText(MainActivity.this,"error in opening connection",Toast.LENGTH_LONG).show();
        }
    }
}