package ir.madeinlobby.memoreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.madeinlobby.memoreminder.model.Comment;

public class CommentsAdaptor extends RecyclerView.Adapter<CommentsAdaptor.ViewHolder> {
    private ArrayList<Comment> data;
    private LayoutInflater layoutInflater;

    public CommentsAdaptor(ArrayList<Comment> data, Context context) {
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sender,date,content;

        ViewHolder(View itemView) {
            super(itemView);
            sender = itemView.findViewById(R.id.commentSender);
            date = itemView.findViewById(R.id.commentDate);
            content = itemView.findViewById(R.id.commentContent);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.sender.setText(data.get(position).getSender());
        holder.date.setText(data.get(position).getDate());
        holder.content.setText(data.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}