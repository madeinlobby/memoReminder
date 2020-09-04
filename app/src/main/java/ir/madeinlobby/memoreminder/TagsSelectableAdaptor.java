package ir.madeinlobby.memoreminder;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.madeinlobby.memoreminder.model.Tag;

public class TagsSelectableAdaptor extends RecyclerView.Adapter<TagsSelectableAdaptor.ViewHolder> {
    private ArrayList<Tag> data;
    private LayoutInflater layoutInflater;

    public TagsSelectableAdaptor(ArrayList<Tag> data, Context context) {
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckedTextView text;
        TextView color;

        ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tagNameSelectable);
            color = itemView.findViewById(R.id.colorBoxForTagsSelectable);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.single_tag_selectable, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.text.setText(data.get(position).getTitle());
        int colorInt = Color.parseColor(data.get(position).getColorHex());
        holder.color.getBackground().setTint(colorInt);
        if (isTagSelectedBefore(data.get(position).getTitle(),data.get(position).getColorHex())){
            holder.text.setChecked(true);
            holder.text.setCheckMarkDrawable(R.drawable.ic_baseline_check_24);
        } else{
            holder.text.setChecked(false);
            holder.text.setCheckMarkDrawable(R.drawable.ic_baseline_uncheked);
        }
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean value = holder.text.isChecked();
                if (value) {
                    holder.text.setChecked(false);
//                    AddTagsForPostActivity.tagsSelected.remove(data.get(position));
                    removeTagFromSelectedList(data.get(position).getTitle(),data.get(position).getColorHex());
                    holder.text.setCheckMarkDrawable(R.drawable.ic_baseline_uncheked);
                } else {
                    holder.text.setChecked(true);
                    holder.text.setCheckMarkDrawable(R.drawable.ic_baseline_check_24);
                    AddTagsForPostActivity.tagsSelected.add(data.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private boolean isTagSelectedBefore(String tagName, String color) {
        for (Tag tag : AddTagsForPostActivity.tagsSelected) {
            if (tag.getColorHex().equals(color) && tag.getTitle().equals(tagName))
                return true;
        }
        return false;
    }

    private void removeTagFromSelectedList(String tagName,String color){
        for (Tag tag : AddTagsForPostActivity.tagsSelected) {
            if (tag.getColorHex().equals(color) && tag.getTitle().equals(tagName)){
                AddTagsForPostActivity.tagsSelected.remove(tag);
                break;
            }
        }
    }
}
