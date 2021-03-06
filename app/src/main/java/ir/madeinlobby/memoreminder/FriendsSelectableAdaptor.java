package ir.madeinlobby.memoreminder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FriendsSelectableAdaptor extends RecyclerView.Adapter<FriendsSelectableAdaptor.ViewHolder> {
    private ArrayList<String> data;
    private LayoutInflater layoutInflater;

    public FriendsSelectableAdaptor(ArrayList<String> data, Context context) {
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckedTextView text;

        ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.friendNameSelectable);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.contact_row_selectable, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.text.setText(data.get(position));
        if (TagFriendsInPostActivity.friendsSelected.contains(data.get(position))) {
            holder.text.setChecked(true);
            holder.text.setCheckMarkDrawable(R.drawable.ic_baseline_check_24);
        } else {
            holder.text.setChecked(false);
            holder.text.setCheckMarkDrawable(R.drawable.ic_baseline_uncheked);
        }
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean value = holder.text.isChecked();
                if (value) {
                    holder.text.setChecked(false);
                    TagFriendsInPostActivity.friendsSelected.remove(holder.text.getText().toString());
                    holder.text.setCheckMarkDrawable(R.drawable.ic_baseline_uncheked);
                } else {
                    holder.text.setChecked(true);
                    holder.text.setCheckMarkDrawable(R.drawable.ic_baseline_check_24);
                    TagFriendsInPostActivity.friendsSelected.add(holder.text.getText().toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
