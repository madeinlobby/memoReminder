package ir.madeinlobby.memoreminder;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.madeinlobby.memoreminder.utilities.BaseController;

public class TagsPageFragment extends Fragment {

    Context context = null;
    RelativeLayout layout = null;

    public TagsPageFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = (RelativeLayout) inflater.inflate(R.layout.tags_page,container,false);
        RecyclerView recyclerView = layout.findViewById(R.id.recycleViewForTags);
        recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        TagsAdaptor tagsAdaptor = new TagsAdaptor(BaseController.getTags(),context);
        recyclerView.setAdapter(tagsAdaptor);
//        return inflater.inflate(R.layout.tags_page,container,false);
        return layout;
    }
}
