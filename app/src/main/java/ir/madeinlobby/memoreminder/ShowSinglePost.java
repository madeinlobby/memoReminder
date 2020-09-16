package ir.madeinlobby.memoreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.stone.vega.library.VegaLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

import ir.madeinlobby.memoreminder.model.Comment;
import ir.madeinlobby.memoreminder.model.Post;
import ir.madeinlobby.memoreminder.model.Tag;
import ir.madeinlobby.memoreminder.utilities.BaseController;
import ir.madeinlobby.memoreminder.utilities.HttpUtility;

public class ShowSinglePost extends AppCompatActivity {
    private static Post post;
    CommentsAdaptor commentsAdaptor;
    static PostsGeneralAdaptor postsGeneralAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_post);

        ImageView postDeleteIcon = findViewById(R.id.deletePost);
        if (BaseController.getTaggedPosts().contains(post)) {
            postDeleteIcon.setVisibility(View.GONE);
        }

        TextView textView = findViewById(R.id.postOwner);
        textView.setText(post.getUsernameWhoBelong());

        RecyclerView recyclerView = findViewById(R.id.recycleViewForTaggedPeople);
        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(ShowSinglePost.this).build();
        recyclerView.setLayoutManager(chipsLayoutManager);
        TaggedPeopleInAPostAdaptor taggedPeopleInAPostAdaptor = new TaggedPeopleInAPostAdaptor(post.getUsersWhoBeenTagged(), ShowSinglePost.this);
        recyclerView.setAdapter(taggedPeopleInAPostAdaptor);

        Log.d("comment1", post.getComments().size() + "");
        RecyclerView recyclerView2 = findViewById(R.id.recycleViewForComments);
        recyclerView2.setLayoutManager(new LinearLayoutManager(ShowSinglePost.this));
//        recyclerView2.setLayoutManager(new VegaLayoutManager());
//        recyclerView2.setLayoutManager(new VirtualLayoutManager(ShowSinglePost.this));
        commentsAdaptor = new CommentsAdaptor(post.getComments(), ShowSinglePost.this);
        recyclerView2.setAdapter(commentsAdaptor);


        SliderView sliderView = findViewById(R.id.imageSlider);

        SliderAdapterExample adapter = new SliderAdapterExample(this, post.getDateCreated());

        adapter.setItems(post.getFilesAddresses());

        TextView location = findViewById(R.id.singlePostLocation);
        location.setText(post.getLocation());

        TextView title = findViewById(R.id.postTopic);
        title.setText(post.getTitle());

        LinearLayout tags = findViewById(R.id.tagsForPost);
        for (Tag tag : post.getTags()) {
            TextView color = new TextView(this);
            color.setWidth(50);
            color.setHeight(50);
            color.setBackground(getResources().getDrawable(R.drawable.color_box));
            color.setGravity(Gravity.CENTER_VERTICAL);
            color.getBackground().setTint(Color.parseColor(tag.getColorHex()));
            tags.addView(color);
            TextView tagName = new TextView(this);
            tagName.setText(" " + tag.getTitle() + "   ");
            tags.addView(tagName);
            color.setTextSize(16);
            tagName.setTextSize(16);
        }
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

    }

    public static Post getPost() {
        return post;
    }

    public static void setPost(Post post) {
        ShowSinglePost.post = post;
    }

    public void addComment(View view) {
        final EditText editText = findViewById(R.id.AddComment);
        String comment = editText.getText().toString();
        final HashMap<String, String> fields2 = new HashMap<>();
        fields2.put("postId", post.getId());
        fields2.put("token", BaseController.getToken());
        fields2.put("content", comment);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = HttpUtility.sendPostRequest(BaseController.server + "/addComment.php", fields2);
                Log.d("comments1", response);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(ShowSinglePost.this, getString(R.string.error_connection_server));
                        }
                    });
                } else {
                    Comment comment1 = new Gson().fromJson(response, Comment.class);
                    post.getComments().add(comment1);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editText.setText("");
                            Toast.makeText(ShowSinglePost.this, "your comment added", Toast.LENGTH_LONG).show();
                            commentsAdaptor.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
    }

    public void deletePostClicked(View view) {
        final HashMap<String, String> fields2 = new HashMap<>();
        fields2.put("postId", post.getId());
        fields2.put("token", BaseController.getToken());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = HttpUtility.sendPostRequest(BaseController.server + "/deletePost.php", fields2);
                if (response.startsWith("error")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.showError(ShowSinglePost.this, getString(R.string.error_connection_server));
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BaseController.getUserPosts().remove(post);
                            Toast.makeText(ShowSinglePost.this, "post removed successfully", Toast.LENGTH_LONG).show();
                            postsGeneralAdaptor.notifyDataSetChanged();
                            finish();
                        }
                    });
                }
            }
        }).start();
    }
}