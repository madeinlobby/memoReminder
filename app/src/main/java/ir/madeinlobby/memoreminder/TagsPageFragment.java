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
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import ir.madeinlobby.memoreminder.model.Tag;
import ir.madeinlobby.memoreminder.utilities.BaseController;
import ir.madeinlobby.memoreminder.utilities.HttpUtility;

public class TagsPageFragment extends Fragment {

    Context context;
    RelativeLayout layout = null;
    TagsAdaptor tagsAdaptor = null;

    public TagsPageFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        getTags();
        layout = (RelativeLayout) inflater.inflate(R.layout.tags_page, container, false);
        RecyclerView recyclerView = layout.findViewById(R.id.recycleViewForTags);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        tagsAdaptor = new TagsAdaptor(BaseController.getTags(), context);
        MainPage.tagsAdaptor = tagsAdaptor;
        MainActivity.tagsAdaptor = tagsAdaptor;
        recyclerView.setAdapter(tagsAdaptor);
//        return inflater.inflate(R.layout.tags_page,container,false);
        return layout;
    }

    private void getTags() {
        final HashMap<String, String> fields2 = new HashMap<>();
        fields2.put("token", BaseController.getToken());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = HttpUtility.sendPostRequest(BaseController.server + "/getTags.php", fields2);
                if (response.startsWith("error")) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(context, getString(R.string.error_connection_server));
                        }
                    });
                } else {
                    ArrayList<Tag> tags = new Gson().fromJson(response, new TypeToken<ArrayList<Tag>>() {
                    }.getType());
                    BaseController.getTags().clear();
                    BaseController.getTags().addAll(tags);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tagsAdaptor.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
    }
}
