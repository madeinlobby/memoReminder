package ir.madeinlobby.memoreminder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import ir.madeinlobby.memoreminder.model.Tag;
import ir.madeinlobby.memoreminder.utilities.BaseController;
import ir.madeinlobby.memoreminder.utilities.HttpUtility;

public class ContactPageFragment extends Fragment {

    Context context;
    RelativeLayout layout = null;
    FriendsAdaptor friendsAdaptor = null;


    public ContactPageFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getFriends();
        layout = (RelativeLayout) inflater.inflate(R.layout.contacts_page, container, false);
        RecyclerView recyclerView = layout.findViewById(R.id.recycleViewForFriends);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        friendsAdaptor = new FriendsAdaptor(BaseController.getFriends(), context);
        recyclerView.setAdapter(friendsAdaptor);
        return layout;
    }

    private void getFriends() {
        final HashMap<String, String> fields2 = new HashMap<>();
        fields2.put("token", BaseController.getToken());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = HttpUtility.sendPostRequest(BaseController.server + "/getFriends.php", fields2);
                if (response.startsWith("error")) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(context, getString(R.string.error_connection_server));
                        }
                    });
                } else {
                    ArrayList<String> friends = new Gson().fromJson(response, new TypeToken<ArrayList<String>>() {
                    }.getType());
                    BaseController.getFriends().clear();
                    BaseController.getFriends().addAll(friends);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            friendsAdaptor.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
    }

}
