package ir.madeinlobby.memoreminder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import ir.madeinlobby.memoreminder.model.Tag;
import ir.madeinlobby.memoreminder.utilities.BaseController;
import ir.madeinlobby.memoreminder.utilities.HttpUtility;

public class DetailsForPostActivity extends AppCompatActivity {
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_for_post_acitivity);
        TagFriendsInPostActivity.friendsSelected = new ArrayList<>();
        AddTagsForPostActivity.tagsSelected = new ArrayList<>();
    }

    public void backFromAddDetails(View view) {
        finish();
    }

    public void createPost(View view) {
        EditText title = findViewById(R.id.postTitle);
        if (title.getText().toString().equals("")) {
            BaseController.showError(DetailsForPostActivity.this, "you must write a title for your post");
            return;
        }
        final HashMap<String, String> fields2 = new HashMap<>();
        fields2.put("token", BaseController.getToken());
        fields2.put("title", title.getText().toString());
        //location
        if (!AddTagsForPostActivity.tagsSelected.isEmpty()) {
            fields2.put("tags", new Gson().toJson(AddTagsForPostActivity.tagsSelected));
        }
        if (!TagFriendsInPostActivity.friendsSelected.isEmpty()) {
            fields2.put("mentions", new Gson().toJson(TagFriendsInPostActivity.friendsSelected));
        }
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                final String response = HttpUtility.sendPostRequest(BaseController.server + "/addPost.php", fields2, AddPostActivity.getFilesSelected());
                Log.d("serverResponse", response);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(DetailsForPostActivity.this, getString(R.string.error_connection_server));
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DetailsForPostActivity.this, "post created successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(DetailsForPostActivity.this, MainPage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
                }
            }
        }).start();
    }

    public void addLocation(View view) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = builder.build((Activity) getApplicationContext());
            startActivityForResult(intent,PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PLACE_PICKER_REQUEST) {
            Place place = PlacePicker.getPlace(data,this);
            String address = place.getAddress().toString();
        }
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
                            BaseController.showError(DetailsForPostActivity.this, getString(R.string.error_connection_server));
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
                            Intent intent = new Intent(DetailsForPostActivity.this, TagFriendsInPostActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }).start();
    }

    public void addTags(View view) {
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
                            BaseController.showError(DetailsForPostActivity.this, getString(R.string.error_connection_server));
                        }
                    });
                } else {
                    ArrayList<Tag> tags = new Gson().fromJson(response, new TypeToken<ArrayList<Tag>>() {
                    }.getType());
                    BaseController.getTags().clear();
                    BaseController.getTags().addAll(tags);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(DetailsForPostActivity.this, AddTagsForPostActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AddTagsForPostActivity.tagsSelected.size() != 0) {
            TextView textView = findViewById(R.id.numberOfTagsWhoAdded);
            textView.setText(AddTagsForPostActivity.tagsSelected.size() + " tags used");
        }
        if (TagFriendsInPostActivity.friendsSelected.size() != 0) {
            TextView textView = findViewById(R.id.numberOfPeopleWhoTaggedInPost);
            textView.setText(TagFriendsInPostActivity.friendsSelected.size() + " people tagged");
        }
    }

    public void openMap(View view) {
        Intent intent = new Intent(DetailsForPostActivity.this, MapsActivity.class);
        startActivity(intent);
    }
}