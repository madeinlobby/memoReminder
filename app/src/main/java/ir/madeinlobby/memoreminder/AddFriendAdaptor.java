package ir.madeinlobby.memoreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
;import java.util.ArrayList;
import java.util.HashMap;

import ir.madeinlobby.memoreminder.utilities.BaseController;
import ir.madeinlobby.memoreminder.utilities.HttpUtility;


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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.text.setText(data.get(position));
        if (!BaseController.getFriends().contains(data.get(position))) {
            holder.imageView.setImageResource(R.drawable.send_request);
            holder.imageView.setTag("add");
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainPage mainPage = (MainPage) layoutInflater.getContext();
                if (holder.imageView.getTag().equals("add")) {
                    mainPage.sendFriendRequest2(holder.text.getText().toString());
                } else {
                    mainPage.removeFriend(holder.text.getText().toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
