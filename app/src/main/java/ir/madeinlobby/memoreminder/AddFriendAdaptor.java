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

import ir.madeinlobby.memoreminder.utilities.BaseController;


public class AddFriendAdaptor extends RecyclerView.Adapter<AddFriendAdaptor.ViewHolder> {
    private ArrayList<String> data;
    private LayoutInflater layoutInflater;

    public AddFriendAdaptor(ArrayList<String> data, Context context) {
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.friendName);
            imageView = itemView.findViewById(R.id.imageViewForContactRow);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.contact_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText(data.get(position));
        if (!BaseController.getFriends().contains(data.get(position))) {
            holder.imageView.setImageResource(R.drawable.send_request);
            holder.imageView.setTag("add");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
