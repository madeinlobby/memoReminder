package ir.madeinlobby.memoreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ir.madeinlobby.memoreminder.model.Post;

public class PostsGeneralAdaptor extends RecyclerView.Adapter<PostsGeneralAdaptor.ViewHolder> {
    private ArrayList<Post> data;
    private LayoutInflater layoutInflater;

    public PostsGeneralAdaptor(ArrayList<Post> data, Context context) {
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, location;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.postRowTitle);
            date = itemView.findViewById(R.id.postRowModification);
            location = itemView.findViewById(R.id.postRowLocation);
            imageView = itemView.findViewById(R.id.postRowPicture);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = data.get(position);
        holder.location.setText(post.getLocation());
        holder.title.setText(post.getTitle());
        holder.date.setText(post.getDateCreated().toString());
        Picasso.get().load(post.getFilesAddresses().get(0)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
