package ir.madeinlobby.memoreminder.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import ir.madeinlobby.memoreminder.MainActivity;

public class BaseController {
    public static final String server = "localhost:80";
    private static String token = "";

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
}
