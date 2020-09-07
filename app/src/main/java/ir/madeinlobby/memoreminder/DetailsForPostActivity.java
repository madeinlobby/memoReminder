package ir.madeinlobby.memoreminder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
//import com.google.android.gms.location.places.Place;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.libraries.places.widget.Autocomplete;
//import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import ir.madeinlobby.memoreminder.model.Tag;
import ir.madeinlobby.memoreminder.utilities.BaseController;
import ir.madeinlobby.memoreminder.utilities.HttpUtility;

public class DetailsForPostActivity extends AppCompatActivity {
    int PLACE_PICKER_REQUEST = 1;
    public static String location = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_for_post_acitivity);
        TagFriendsInPostActivity.friendsSelected = new ArrayList<>();
        AddTagsForPostActivity.tagsSelected = new ArrayList<>();
        location = "";
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
        if (!location.equals("")){
            fields2.put("location",location);
        }
        if (!AddTagsForPostActivity.tagsSelected.isEmpty()) {
            ArrayList<String> titles = new ArrayList<>();
            for (Tag tag : AddTagsForPostActivity.tagsSelected)
                titles.add(tag.getTitle());
            fields2.put("tags", new Gson().toJson(titles));
        }
        if (!TagFriendsInPostActivity.friendsSelected.isEmpty()) {
            fields2.put("mentions", new Gson().toJson(TagFriendsInPostActivity.friendsSelected));
        }
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                final String response = HttpUtility.sendPostRequest(getContentResolver(), BaseController.server + "/addPost.php", fields2, AddPostActivity.getFilesSelected());
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
            Intent intent = builder.build(DetailsForPostActivity.this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
//        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
//        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
//                .build(DetailsForPostActivity.this);
//        startActivityForResult(intent, PLACE_PICKER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PLACE_PICKER_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                Place place = Autocomplete.getPlaceFromIntent(data);
//                Log.i("tag", "Place: " + place.getName() + ", " + place.getId());
//            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
//                Status status = Autocomplete.getStatusFromIntent(data);
//                Log.i("tag", status.getStatusMessage());
//            } else if (resultCode == RESULT_CANCELED) {
//            }
//            return;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PLACE_PICKER_REQUEST) {
            Place place = PlacePicker.getPlace(this, data);
//            Log.d("aboots", String.valueOf(place));
//            Log.d("aboots", place.getId());
//            Log.d("aboots", place.getLatLng().latitude + "");
//            Log.d("aboots", place.getLatLng().longitude + "");
            Geocoder geocoder = new Geocoder(DetailsForPostActivity.this, Locale.getDefault());
            try {
                List<Address> adressess = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
                String address = adressess.get(0).getAddressLine(0);
//                Log.d("aboots", "compelete adress  " + adressess);
//                Log.d("aboots",adressess.get(0).getCountryName());
//                Log.d("aboots",adressess.get(0).getLocality());
//                Log.d("aboots",adressess.get(0).getAdminArea());
//                Log.d("aboots",adressess.get(0).getSubAdminArea());
                String locSaved = adressess.get(0).getCountryName() + ", " + adressess.get(0).getAdminArea();
                TextView textView = findViewById(R.id.location);
                textView.setText(locSaved);
//                TextView textView = findViewById(R.id.locationThatBeenChosen);
//                textView.setText(address);
                location = locSaved;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
}