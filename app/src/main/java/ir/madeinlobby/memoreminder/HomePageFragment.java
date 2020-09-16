package ir.madeinlobby.memoreminder;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import ir.madeinlobby.memoreminder.model.Post;
import ir.madeinlobby.memoreminder.utilities.BaseController;
import ir.madeinlobby.memoreminder.utilities.HttpUtility;

public class HomePageFragment extends Fragment {
    Context context;
    RelativeLayout layout;
    PostsGeneralAdaptor postsGeneralAdaptor;

    public HomePageFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        getPosts();
        layout = (RelativeLayout) inflater.inflate(R.layout.home_page, container, false);
        RecyclerView recyclerView = layout.findViewById(R.id.recycleViewForPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        postsGeneralAdaptor = new PostsGeneralAdaptor(BaseController.getUserPosts(), context);
        MainActivity.postsGeneralAdaptor = postsGeneralAdaptor;
        recyclerView.setAdapter(postsGeneralAdaptor);
        return layout;
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(context, getString(R.string.error_connection_server));
                        }
                    });
                } else {
                    ArrayList<Post> posts = new Gson().fromJson(response, new TypeToken<ArrayList<Post>>() {
                    }.getType());
                    BaseController.getUserPosts().clear();
                    BaseController.getUserPosts().addAll(posts);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            postsGeneralAdaptor.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
    }
}
