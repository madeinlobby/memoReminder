package ir.madeinlobby.memoreminder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import ir.madeinlobby.memoreminder.R;

public class AddPostActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private Uri imageUri = null;
    ImageView imageView = null;
    GridLayout photosGrid = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_activity);
    }

    public void addPost(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
        photosGrid = findViewById(R.id.gridForPhotos);
        imageView = new ImageView(this);
        imageView.setMaxHeight(278);
        imageView.setMinimumHeight(278);
        imageView.setMaxWidth(278);
        imageView.setMinimumHeight(278);
        imageView.setBackground(getResources().getDrawable(R.drawable.border_for_photo));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(280, 280);
        lp.setMargins(10, 10, 10, 10);
        imageView.setPadding(4,4,4,4);
        imageView.setLayoutParams(lp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            photosGrid.addView(imageView);
        }
    }

    public void backFromAddPhotos(View view) {
        finish();
    }

    public void goToCreatePost(View view) {
        Intent intent = new Intent();
        startActivity(intent);
    }
}
