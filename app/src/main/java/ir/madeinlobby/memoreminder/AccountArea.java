package ir.madeinlobby.memoreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ir.madeinlobby.memoreminder.utilities.BaseController;

public class AccountArea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_area);
    }

    public void logOutClicked(View view) {
        BaseController.setToken("");
        Intent intent = new Intent(AccountArea.this, MainActivity.class);
        startActivity(intent);
    }
}