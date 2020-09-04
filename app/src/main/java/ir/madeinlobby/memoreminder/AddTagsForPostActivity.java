package ir.madeinlobby.memoreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import ir.madeinlobby.memoreminder.model.Tag;
import ir.madeinlobby.memoreminder.utilities.BaseController;

public class AddTagsForPostActivity extends AppCompatActivity {
    public static ArrayList<Tag> tagsSelected = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tags_for_post_acitivity);
        RecyclerView recyclerView = findViewById(R.id.tagsSelectableList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TagsSelectableAdaptor adaptor = new TagsSelectableAdaptor(BaseController.getTags(), AddTagsForPostActivity.this);
        recyclerView.setAdapter(adaptor);
    }

    public void doneAddTags(View view) {
        finish();
    }
}