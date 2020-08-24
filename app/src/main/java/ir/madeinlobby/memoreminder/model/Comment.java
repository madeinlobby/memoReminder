package ir.madeinlobby.memoreminder.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

public class Comment {
    private String id;
    private String sender;
    private String content;
    private String postId;
    private LocalDate date;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Comment(String sender, String content, String postId) {
        this.sender = sender;
        this.content = content;
        this.postId = postId;
        this.date = LocalDate.now();
        this.id = RandomString.getAlphaNumericString();
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

    public LocalDate getDate() {
        return date;
    }
}
