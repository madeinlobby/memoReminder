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


public class FriendsAdaptor extends RecyclerView.Adapter<FriendsAdaptor.ViewHolder> {
    private ArrayList<String> data;
    private LayoutInflater layoutInflater;

    public FriendsAdaptor(ArrayList<String> data, Context context) {
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.text.setText(data.get(position));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainPage mainPage = (MainPage) layoutInflater.getContext();
                mainPage.removeFriend(holder.text.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
