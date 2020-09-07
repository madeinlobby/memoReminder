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
    private String dateCreated;
    private String lastDateEdited;
    private String location;
    private ArrayList<Tag> tags;
    private ArrayList<String> usersWhoBeenTagged;
    private ArrayList<Comment> comments;
    private ArrayList<String> filesAddresses;

    public Post(String id, String title, String usernameWhoBelong, String dateCreated, String lastDateEdited, String location, ArrayList<Tag> tags, ArrayList<String> usersWhoBeenTagged, ArrayList<Comment> comments, ArrayList<String> filesAddresses) {
        this.id = id;
        this.title = title;
        this.usernameWhoBelong = usernameWhoBelong;
        this.dateCreated = dateCreated;
        this.lastDateEdited = lastDateEdited;
        this.location = location;
        this.tags = tags;
        this.usersWhoBeenTagged = usersWhoBeenTagged;
        this.comments = comments;
        this.filesAddresses = filesAddresses;
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

    public String getDateCreated() {
        return dateCreated;
    }

    public String getLastDateEdited() {
        return lastDateEdited;
    }

    public String getLocation() {
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

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<String> getFilesAddresses() {
        return filesAddresses;
    }
}
