package ir.madeinlobby.memoreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SinglePost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_post);
    }

    public void addComment(View view) {
        EditText editText = findViewById(R.id.AddComment);
        String comment=editText.getText().toString();
    }
}