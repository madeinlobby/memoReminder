package ir.madeinlobby.memoreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.azeesoft.lib.colorpicker.ColorPickerDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import ir.madeinlobby.memoreminder.controller.MainManager;
import ir.madeinlobby.memoreminder.model.Tag;
import ir.madeinlobby.memoreminder.utilities.BaseController;
import ir.madeinlobby.memoreminder.utilities.HttpUtility;


public class MainPage extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    ScrollView scrollView;
    String tagColor = "";
    String contactPage = "";
    public static TagsAdaptor tagsAdaptor = null;
    public static AddFriendAdaptor addFriendAdaptor = null;
    public static FriendRequestAdaptor friendRequestAdaptor = null;
    FloatingActionButton floatingActionButton;
    public static FriendsAdaptor friendsAdaptor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        scrollView = findViewById(R.id.main_layout);
        floatingActionButton = findViewById(R.id.fab);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPart, new HomePageFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new HomePageFragment();
                        break;
                    case R.id.nav_contacts:
                        if (contactPage.equals("")) {
                            floatingActionButton.setVisibility(View.VISIBLE);
                            selectedFragment = new ContactPageFragment(MainPage.this);
                        } else if (contactPage.equals("friendRequests")) {
                            floatingActionButton.setVisibility(View.GONE);
                            selectedFragment = new FriendRequestsFragment(MainPage.this);
                        } else if (contactPage.equals("addFriend")) {
                            floatingActionButton.setVisibility(View.GONE);
                            selectedFragment = new AddFriendsFragment(MainPage.this);
                        }
                        break;
                    case R.id.nav_tags:
                        floatingActionButton.setVisibility(View.GONE);
                        selectedFragment = new TagsPageFragment(MainPage.this);
                        break;
                    case R.id.nav_tagged:
                        floatingActionButton.setVisibility(View.GONE);
                        showTaggedPage();
                        break;

                    case R.id.nav_logout:
                        logout();
                        return true;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPart, selectedFragment).commit();
                return true;
            }
        });
    }

    private void showTaggedPage() {
    }

    private void showTagsPage() {
        LayoutInflater inflater;
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.tags_page, null);
        scrollView.addView(layout);
    }

    public void colorPickerClicked(View view) {
        ColorPickerDialog colorPickerDialog = ColorPickerDialog.createColorPickerDialog(MainPage.this, R.style.AppTheme);
        colorPickerDialog.setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
            @Override
            public void onColorPicked(int color, String hexVal) {
                Log.d("color", hexVal);
                tagColor = hexVal;
            }
        });
        colorPickerDialog.show();
    }

    public void addTag(View view) {
        if (tagColor.equals("")) {
            BaseController.showError(MainPage.this, getString(R.string.error_for_pick_color));
            return;
        }
        EditText editText = findViewById(R.id.tagName);
        final String tagTitle = editText.getText().toString();
        final HashMap<String, String> fields = new HashMap<>();
        fields.put("token", BaseController.getToken());
        fields.put("text", tagTitle);
        fields.put("color", tagColor);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = HttpUtility.sendPostRequest(BaseController.server + "/addTag.php", fields);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(MainPage.this, getString(R.string.error_for_add_tag));
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainPage.this, getString(R.string.tag_added_successful), Toast.LENGTH_LONG).show();
                            BaseController.getTags().add(new Tag(tagTitle, tagColor));
                            tagsAdaptor.notifyDataSetChanged();
                            tagColor = "";
                        }
                    });
                }
            }
        }).start();
    }

    public void sendFriendRequest(View view) {
        TextView textView = findViewById(R.id.friendUsername);
        final String friendUsername = textView.getText().toString();
        final HashMap<String, String> fields = new HashMap<>();
        fields.put("token", BaseController.getToken());
        fields.put("username", friendUsername);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = HttpUtility.sendPostRequest(BaseController.server + "/sendFriendRequest.php", fields);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(MainPage.this, getString(R.string.send_request_error));
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainPage.this, getString(R.string.request_sent_successfully), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();

    }

    public void addFriendButtonClicked(View view) {
        floatingActionButton.setVisibility(View.GONE);
        contactPage = "addFriend";
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPart, new AddFriendsFragment(MainPage.this)).commit();
    }

    private void logout() {
        final HashMap<String, String> fields = new HashMap<>();
        fields.put("token", BaseController.getToken());
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = HttpUtility.sendPostRequest(BaseController.server + "/logout.php", fields);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(MainPage.this, getString(R.string.error_connection_server));
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainPage.this, getString(R.string.logout_successful), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
        BaseController.getTags().clear();
        BaseController.getFriends().clear();
        BaseController.getFriendsRequests().clear();
        BaseController.getSearchedUsers().clear();
        BaseController.setToken("");
        finish();
    }

    public void showFriendRequests(View view) {
        contactPage = "friendRequests";
        floatingActionButton.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPart, new FriendRequestsFragment(MainPage.this)).commit();
    }

    public void searchUsername(View view) {
        final HashMap<String, String> fields2 = new HashMap<>();
        EditText searchedUsername = findViewById(R.id.searchedUsername);
        fields2.put("search", searchedUsername.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = HttpUtility.sendPostRequest(BaseController.server + "/searchUsername.php", fields2);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(MainPage.this, getString(R.string.send_request_error));
                        }
                    });
                } else {
                    ArrayList<String> users = new Gson().fromJson(response, new TypeToken<ArrayList<String>>() {
                    }.getType());
                    BaseController.getSearchedUsers().clear();
                    if (!users.isEmpty()) {
                        BaseController.getSearchedUsers().addAll(users);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BaseController.showError(MainPage.this, "this user name doesn't exist");
                            }
                        });
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addFriendAdaptor.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
    }

    public void backFromFriendRequestsPage(View view) {
        contactPage = "";
        floatingActionButton.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPart, new ContactPageFragment(MainPage.this)).commit();
    }

    public void backFromSearchUserPage(View view) {
        contactPage = "";
        floatingActionButton.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPart, new ContactPageFragment(MainPage.this)).commit();
    }

//    public void contactRowClicked(View view) {
//        ImageView imageView = findViewById(R.id.imageViewForContactRow);
//        String tag = (String) imageView.getTag();
//        if (tag.equals("remove")) {
//            Log.d("checkkk","remove");
//            new AlertDialog.Builder(MainPage.this)
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .setTitle("remove friend")
//                    .setMessage("are you sure you want to remove this friend?")
//                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            removeFriend();
//                        }
//                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                }
//            })
//                    .show();
//        } else {
//            Log.d("checkkk","add");
//            sendFriendRequest2(view);
//        }
//    }

    public void removeFriend(final String friendUsername) {
        new AlertDialog.Builder(MainPage.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("remove friend")
                .setMessage("are you sure you want to remove " + friendUsername + " from your friends")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final HashMap<String, String> fields = new HashMap<>();
                        fields.put("token", BaseController.getToken());
                        fields.put("username", friendUsername);
                        fields.put("type", "remove");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final String response = HttpUtility.sendPostRequest(BaseController.server + "/sendFriendRequest.php", fields);
                                if (response.startsWith("error")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            BaseController.showError(MainPage.this, response);
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainPage.this, getString(R.string.remove_friend), Toast.LENGTH_LONG).show();
                                            BaseController.getFriends().remove(friendUsername);
                                            friendsAdaptor.notifyDataSetChanged();
                                        }
                                    });
                                }
                            }
                        }).start();
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .show();
    }

    public void sendFriendRequest2(final String friendUsername) {
//        TextView textView = findViewById(R.id.friendName);
//        final String friendUsername = textView.getText().toString();
        final HashMap<String, String> fields = new HashMap<>();
        fields.put("token", BaseController.getToken());
        fields.put("username", friendUsername);
        fields.put("type", "add");
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = HttpUtility.sendPostRequest(BaseController.server + "/sendFriendRequest.php", fields);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(MainPage.this, response);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainPage.this, getString(R.string.request_sent_successfully), Toast.LENGTH_LONG).show();
                            BaseController.getSearchedUsers().remove(friendUsername);
                            addFriendAdaptor.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
    }

    public void acceptFriendRequest(final String userName) {
        final HashMap<String, String> fields = new HashMap<>();
        fields.put("token", BaseController.getToken());
        fields.put("username", userName);
        fields.put("type", "accept");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = HttpUtility.sendPostRequest(BaseController.server + "/sendFriendRequest.php", fields);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(MainPage.this, getString(R.string.send_request_error));
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainPage.this, getString(R.string.accept_friend_request), Toast.LENGTH_LONG).show();
                            BaseController.getFriendsRequests().remove(userName);
                            friendRequestAdaptor.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
    }

    public void rejectFriendRequest(final String userName) {
        final HashMap<String, String> fields = new HashMap<>();
        fields.put("token", BaseController.getToken());
        fields.put("username", userName);
        fields.put("type", "reject");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = HttpUtility.sendPostRequest(BaseController.server + "/sendFriendRequest.php", fields);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(MainPage.this, getString(R.string.send_request_error));
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainPage.this, getString(R.string.reject_friend_request), Toast.LENGTH_LONG).show();
                            BaseController.getFriendsRequests().remove(userName);
                            friendRequestAdaptor.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
    }

    public void addPost(View view) {
//        ImagePicker.Companion.with(this)
//                .crop()	    			//Crop image(Optional), Check Customization for more option
//                .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
//                .start();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
}