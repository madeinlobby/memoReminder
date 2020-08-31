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

public class FriendRequestsFragment extends Fragment {
    Context context;
    RelativeLayout layout = null;
//    TagsAdaptor tagsAdaptor = null;

    public FriendRequestsFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = (RelativeLayout) inflater.inflate(R.layout.freind_requests, container, false);
//        RecyclerView recyclerView = layout.findViewById(R.id.recycleViewForTags);
//        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
//        tagsAdaptor = new TagsAdaptor(BaseController.getTags(), context);
//        MainPage.tagsAdaptor = tagsAdaptor;
//        recyclerView.setAdapter(tagsAdaptor);
        return layout;
    }
}
