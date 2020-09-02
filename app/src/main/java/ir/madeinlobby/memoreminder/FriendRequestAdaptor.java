package ir.madeinlobby.memoreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
;import java.util.ArrayList;


public class FriendRequestAdaptor extends RecyclerView.Adapter<FriendRequestAdaptor.ViewHolder> {
    private ArrayList<String> data;
    private LayoutInflater layoutInflater;

    public FriendRequestAdaptor(ArrayList<String> data, Context context) {
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        Button acceptButton,rejectButton;
        ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.requestUsername);
            acceptButton = itemView.findViewById(R.id.acceptRequest);
            rejectButton = itemView.findViewById(R.id.deleteRequest);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.single_friend_request, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.text.setText(data.get(position));
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainPage mainPage = (MainPage) layoutInflater.getContext();
                mainPage.acceptFriendRequest(data.get(position));
            }
        });
        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainPage mainPage = (MainPage) layoutInflater.getContext();
                mainPage.rejectFriendRequest(data.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
