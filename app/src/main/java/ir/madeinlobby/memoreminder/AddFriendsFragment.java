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

public class AddFriendsFragment extends Fragment {
    Context context;
    RelativeLayout layout = null;
    AddFriendAdaptor addFriendAdaptor = null;

    public AddFriendsFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = (RelativeLayout) inflater.inflate(R.layout.add_friend, container, false);
        RecyclerView recyclerView = layout.findViewById(R.id.recycleViewForUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        addFriendAdaptor = new AddFriendAdaptor(BaseController.getSearchedUsers(), context);
        recyclerView.setAdapter(addFriendAdaptor);
        MainPage.addFriendAdaptor = addFriendAdaptor;
        return layout;
    }


}
