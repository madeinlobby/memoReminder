package ir.madeinlobby.memoreminder;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import ir.madeinlobby.memoreminder.utilities.BaseController;
import ir.madeinlobby.memoreminder.utilities.HttpUtility;

public class FriendRequestsFragment extends Fragment {
    Context context;
    RelativeLayout layout = null;
    FriendRequestAdaptor friendRequestAdaptor = null;

    public FriendRequestsFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getFriendRequests();
        layout = (RelativeLayout) inflater.inflate(R.layout.freind_requests, container, false);
        RecyclerView recyclerView = layout.findViewById(R.id.recycleViewForFriendRequests);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        friendRequestAdaptor = new FriendRequestAdaptor(BaseController.getFriendsRequests(), context);
        recyclerView.setAdapter(friendRequestAdaptor);
        return layout;
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
                    BaseController.showError(context, getString(R.string.error_connection_server));
                } else {
                    ArrayList<String> friends = new Gson().fromJson(response, new TypeToken<ArrayList<String>>() {
                    }.getType());
                    BaseController.getFriendsRequests().clear();
                    BaseController.getFriendsRequests().addAll(friends);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            friendRequestAdaptor.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
    }

}
