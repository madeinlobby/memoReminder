package ir.madeinlobby.memoreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class DetailsForPostAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_for_post_acitivity);
    }

    public void backFromAddDetails(View view) {
        finish();
    }

    public void createPost(View view) {
    }
}