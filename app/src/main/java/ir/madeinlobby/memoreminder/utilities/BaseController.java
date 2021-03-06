package ir.madeinlobby.memoreminder.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.ArrayList;

import ir.madeinlobby.memoreminder.model.Post;
import ir.madeinlobby.memoreminder.model.Tag;


public class BaseController {
    //    public static final String server = "http://10.0.2.2/memoReminder";           //local
    public static final String server = "https://memoreminder.000webhostapp.com/control";     // online
//    public static final String server = "http://taha7900.gigfa.com/control";     // online
    private static String token = "";
    private  static ArrayList<Tag> tags=new ArrayList<>();
    private static ArrayList<String> searchedUsers = new ArrayList<>();
    private static  ArrayList<String> friendsRequests= new ArrayList<>();
    private static ArrayList<String> friends = new ArrayList<>();
    private static ArrayList<Post> userPosts = new ArrayList<>();
    private static ArrayList<Post> taggedPosts = new ArrayList<>();

    public static void showError(Context context, String message) {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Error happened!")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        BaseController.token = token;
    }

    public static ArrayList<Tag> getTags() {
        return tags;
    }

    public static ArrayList<String> getFriendsRequests() {
        return friendsRequests;
    }

    public static ArrayList<String> getSearchedUsers() {
        return searchedUsers;
    }

    public static ArrayList<String> getFriends() {
        return friends;
    }

    public static ArrayList<Post> getUserPosts() {
        return userPosts;
    }

    public static ArrayList<Post> getTaggedPosts() {
        return taggedPosts;
    }
}
