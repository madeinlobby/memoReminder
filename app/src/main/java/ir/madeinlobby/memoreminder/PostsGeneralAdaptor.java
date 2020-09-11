package ir.madeinlobby.memoreminder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ir.madeinlobby.memoreminder.model.Post;

public class PostsGeneralAdaptor extends RecyclerView.Adapter<PostsGeneralAdaptor.ViewHolder> {
    private ArrayList<Post> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public PostsGeneralAdaptor(ArrayList<Post> data, Context context) {
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Post post = data.get(position);
        holder.location.setText(post.getLocation());
        holder.title.setText(post.getTitle());
        holder.date.setText(post.getDateCreated());
        Picasso.get().load(post.getFilesAddresses().get(0)).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowSinglePost.post = data.get(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
