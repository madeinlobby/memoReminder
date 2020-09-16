package ir.madeinlobby.memoreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import ir.madeinlobby.memoreminder.model.Post;
import ir.madeinlobby.memoreminder.model.Tag;
import ir.madeinlobby.memoreminder.utilities.BaseController;
import ir.madeinlobby.memoreminder.utilities.HttpUtility;

public class MainActivity extends AppCompatActivity {
    public static Activity mainActivity;
    CircularProgressButton circularProgressButton;
    static PostsGeneralAdaptor postsGeneralAdaptor;
    static TagsAdaptor tagsAdaptor;
    static PostsGeneralAdaptor postsGeneralAdaptor2;
    static FriendsAdaptor friendsAdaptor;
    static FriendRequestAdaptor friendRequestAdaptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpUtility.getCookies(this);

        TextView textView = findViewById(R.id.register);
        SpannableString content = new SpannableString("create an account");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);

        circularProgressButton = findViewById(R.id.loginButton);
    }

    public void registerPressed(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void loginClicked(View view) {
        circularProgressButton.startAnimation();
        EditText textUserName = findViewById(R.id.usernameForLogin);
        EditText textPassword = findViewById(R.id.passwordForLogin);
        String userName = textUserName.getText().toString();
        String password = textPassword.getText().toString();
        final HashMap<String, String> fields = new HashMap<>();
        fields.put("username", userName);
        fields.put("password", password);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = HttpUtility.sendPostRequest(BaseController.server + "/login.php", fields);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(MainActivity.this, getString(R.string.error_for_login));
//                            Toast.makeText(MainActivity.this, "invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    String token = response.substring(4);
                    BaseController.setToken(token.trim());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getTags();
                        }
                    });
                }
            }
        }).start();
    }

    private void getPosts() {
        final HashMap<String, String> fields2 = new HashMap<>();
        fields2.put("token", BaseController.getToken());
        Log.d("token11", BaseController.getToken());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = HttpUtility.sendPostRequest(BaseController.server + "/getPosts.php", fields2);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(MainActivity.this, getString(R.string.error_connection_server));
                        }
                    });
                } else {
                    ArrayList<Post> posts = new Gson().fromJson(response, new TypeToken<ArrayList<Post>>() {
                    }.getType());
                    BaseController.getUserPosts().clear();
                    BaseController.getUserPosts().addAll(posts);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            postsGeneralAdaptor.notifyDataSetChanged();
                            getTaggedPosts1();
                        }
                    });
                }
            }
        }).start();
    }

    private void getTags() {
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
                            BaseController.showError(MainActivity.this, getString(R.string.error_connection_server));
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
//                            tagsAdaptor.notifyDataSetChanged();
                            getFriends();
                        }
                    });
                }
            }
        }).start();
    }

    private void getTaggedPosts1() {
        final HashMap<String, String> fields2 = new HashMap<>();
        fields2.put("token", BaseController.getToken());
        fields2.put("mentioned", "true");
        Log.d("token11", BaseController.getToken());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = HttpUtility.sendPostRequest(BaseController.server + "/getPosts.php", fields2);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(MainActivity.this, getString(R.string.error_connection_server));
                        }
                    });
                } else {
                    ArrayList<Post> posts = new Gson().fromJson(response, new TypeToken<ArrayList<Post>>() {
                    }.getType());
                    BaseController.getTaggedPosts().clear();
                    BaseController.getTaggedPosts().addAll(posts);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            postsGeneralAdaptor2.notifyDataSetChanged();
                            circularProgressButton.stopAnimation();
                            Toast.makeText(MainActivity.this, getString(R.string.login_successful), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, MainPage.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }).start();
    }

    private void getFriends() {
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
                            BaseController.showError(MainActivity.this, getString(R.string.error_connection_server));
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
//                            friendsAdaptor.notifyDataSetChanged();
                            getFriendRequests();
                        }
                    });
                }
            }
        }).start();
    }

    private void getFriendRequests() {
        final HashMap<String, String> fields2 = new HashMap<>();
        fields2.put("token", BaseController.getToken());
        fields2.put("type", "received");
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = HttpUtility.sendPostRequest(BaseController.server + "/getFriendRequest.php", fields2);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(MainActivity.this, getString(R.string.error_connection_server));
                        }
                    });
                } else {
                    ArrayList<String> friends = new Gson().fromJson(response, new TypeToken<ArrayList<String>>() {
                    }.getType());
                    BaseController.getFriendsRequests().clear();
                    BaseController.getFriendsRequests().addAll(friends);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            friendRequestAdaptor.notifyDataSetChanged();
                            getPosts();
                        }
                    });
                }
            }
        }).start();
    }
}