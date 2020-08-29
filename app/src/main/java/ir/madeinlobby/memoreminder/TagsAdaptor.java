package ir.madeinlobby.memoreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        TextView subjectTextView;
        ImageView image;
        ViewHolder(View itemView) {
            super(itemView);
//            senderTextView = itemView.findViewById(R.id.senderInRow);
//            subjectTextView = itemView.findViewById(R.id.subjectInRow);
//            image = itemView.findViewById(R.id.imageViewEmail);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.single_tag_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.senderTextView .setText(data.get(position).getSender());
//        holder.subjectTextView.setText(data.get(position).getSubject());
//        Picasso.get().load(data.get(position).getImageURL()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
