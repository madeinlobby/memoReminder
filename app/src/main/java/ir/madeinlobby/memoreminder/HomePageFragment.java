package ir.madeinlobby.memoreminder;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.madeinlobby.memoreminder.utilities.BaseController;

public class HomePageFragment extends Fragment {
    Context context;
    RelativeLayout layout;

    public HomePageFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getPosts();
        layout = (RelativeLayout) inflater.inflate(R.layout.home_page, container, false);
        RecyclerView recyclerView = layout.findViewById(R.id.recycleViewForPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        PostsGeneralAdaptor postsGeneralAdaptor = new PostsGeneralAdaptor(BaseController.getUserPosts(),context);
        recyclerView.setAdapter(postsGeneralAdaptor);
        return layout;
    }

    private void getPosts() {
    }
}
