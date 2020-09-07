package ir.madeinlobby.memoreminder.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

public class Comment {
    private String id;
    private String sender;
    private String content;
    private String postId;
    private String date;


    public Comment(String id, String sender, String content, String postId, String date) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.postId = postId;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public String getPostId() {
        return postId;
    }

    public String getDate() {
        return date;
    }
}
