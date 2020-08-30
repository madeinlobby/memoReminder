package ir.madeinlobby.memoreminder.model;

import java.util.ArrayList;

public class Person {
    private String username;
    private String password;
    private ArrayList<String> allPostsIds;
    private ArrayList<String> friendRequestsReceived;
    private ArrayList<String> friends;
    private ArrayList<Tag> allTags;
    private String firstName;
    private String lastName;
    private String email;

    public Person(String username, String password, String firstName, String lastName, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.allPostsIds = new ArrayList<>();
        this.allTags = new ArrayList<>();
        this.email = email;
        this.friendRequestsReceived = new ArrayList<>();
        this.friends = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getAllPostsIds() {
        return allPostsIds;
    }

    public ArrayList<Tag> getAllTags() {
        return allTags;
    }
}
