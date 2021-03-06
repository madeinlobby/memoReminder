package ir.madeinlobby.memoreminder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.Rectangle;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
;import java.util.ArrayList;

import ir.madeinlobby.memoreminder.model.Tag;

public class TagsAdaptor extends RecyclerView.Adapter<TagsAdaptor.ViewHolder> {
    private ArrayList<Tag> data;
    private LayoutInflater layoutInflater;

    public TagsAdaptor(ArrayList<Tag> data, Context context) {
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        TextView color;
        RelativeLayout relativeLayout;
        ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.shapeForTag);
            text = itemView.findViewById(R.id.tagName);
            color = itemView.findViewById(R.id.colorBoxForTags);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.single_tag_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText(data.get(position).getTitle());
        int colorInt = Color.parseColor(data.get(position).getColorHex());
        holder.color.getBackground().setTint(colorInt);
        GradientDrawable backgroundGradient = (GradientDrawable) holder.relativeLayout.getBackground();
        backgroundGradient.setStroke(5,colorInt);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
