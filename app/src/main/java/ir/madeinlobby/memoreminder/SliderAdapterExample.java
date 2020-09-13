package ir.madeinlobby.memoreminder;

import android.content.Context;

import com.bumptech.glide.Glide;

import android.graphics.Color;
import android.icu.util.LocaleData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.SliderViewAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapterExample extends
        SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private List<String> imageUrls = new ArrayList<>();
    private String modificationDate;

    public SliderAdapterExample(Context context, String date) {
        this.context = context;
        this.modificationDate = date;
    }

    public void setItems(List<String> images) {
        this.imageUrls.addAll(images);
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        String url = imageUrls.get(position);
        Glide.with(viewHolder.itemView)
                .load(url)
                .fitCenter()
                .into(viewHolder.imageViewBackground);
        viewHolder.textView.setText(modificationDate);
        viewHolder.textView.setTextSize(16);
        viewHolder.textView.setTextColor(Color.WHITE);

//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return imageUrls.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textView;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textView = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }

}