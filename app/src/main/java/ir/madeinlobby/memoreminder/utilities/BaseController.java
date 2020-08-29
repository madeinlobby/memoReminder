package ir.madeinlobby.memoreminder.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.ArrayList;

import ir.madeinlobby.memoreminder.model.Tag;


public class BaseController {
    //    public static final String server = "http://10.0.2.2/memoReminder";           //local
    public static final String server = "https://memoreminder.000webhostapp.com";     // online
    private static String token = "";
    private  static ArrayList<Tag> tags=new ArrayList<>();

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

    public static void setTags(ArrayList<Tag> tags) {
        BaseController.tags = tags;
    }
}
