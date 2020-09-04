package ir.madeinlobby.memoreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import ir.madeinlobby.memoreminder.utilities.BaseController;
import ir.madeinlobby.memoreminder.utilities.HttpUtility;

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

    public void addLocation(View view) {
    }

    public void tagFriends(View view) {
        final HashMap<String, String> fields2 = new HashMap<>();
        fields2.put("token", BaseController.getToken());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = HttpUtility.sendPostRequest(BaseController.server + "/getFriends.php", fields2);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(DetailsForPostAcitivity.this, getString(R.string.error_connection_server));
                        }
                    });
                } else {
                    ArrayList<String> friends = new Gson().fromJson(response, new TypeToken<ArrayList<String>>() {
                    }.getType());
                    BaseController.getFriends().clear();
                    if (friends != null) {
                        BaseController.getFriends().addAll(friends);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(DetailsForPostAcitivity.this,TagFriendsInPostActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }).start();
    }

    public void addTags(View view) {
        Intent intent = new Intent(DetailsForPostAcitivity.this,AddTagsForPostAcitivity.class);
        startActivity(intent);
    }
}