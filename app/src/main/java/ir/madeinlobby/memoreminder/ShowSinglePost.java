package ir.madeinlobby.memoreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import ir.madeinlobby.memoreminder.model.Post;
import ir.madeinlobby.memoreminder.model.Tag;

public class ShowSinglePost extends AppCompatActivity {
    private static Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_post);

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
            tagName.setText(" "+tag.getTitle() + "   ");
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
}