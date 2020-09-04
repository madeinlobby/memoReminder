package ir.madeinlobby.memoreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import ir.madeinlobby.memoreminder.utilities.BaseController;

public class TagFriendsInPostActivity extends AppCompatActivity {
    public static ArrayList<String> friendsSelected= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_friends_in_post);
        RecyclerView recyclerView=findViewById(R.id.contactsSelectableList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FriendsSelectableAdaptor adaptor=new FriendsSelectableAdaptor(BaseController.getFriends(),TagFriendsInPostActivity.this);
        recyclerView.setAdapter(adaptor);
    }

    public void doneAddFriends(View view) {
        finish();
    }
}