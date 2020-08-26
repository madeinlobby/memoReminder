package ir.madeinlobby.memoreminder.model;

import android.location.Location;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;

public class Post {
    private String id;
    private String title;
    private String usernameWhoBelong;
    private LocalDate dateCreated;
    private LocalDate lastDateEdited;
    private Location location;
    private ArrayList<Tag> tags;
    private ArrayList<String> usersWhoBeenTagged;
    private ArrayList<Comment> comments;
    // image and video and voice

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Post(String title, String usernameWhoBelong,Location location) {
        this.title = title;
        this.dateCreated = LocalDate.now();
        this.lastDateEdited = LocalDate.now();
        this.usernameWhoBelong = usernameWhoBelong;
        this.id = RandomString.getAlphaNumericString();
        this.tags = new ArrayList<>();
        this.usersWhoBeenTagged = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUsernameWhoBelong() {
        return usernameWhoBelong;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public LocalDate getLastDateEdited() {
        return lastDateEdited;
    }

    public Location getLocation() {
        return location;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public ArrayList<String> getUsersWhoBeenTagged() {
        return usersWhoBeenTagged;
    }

    public ArrayList<Comment> getCommentsIds() {
        return comments;
    }
}
