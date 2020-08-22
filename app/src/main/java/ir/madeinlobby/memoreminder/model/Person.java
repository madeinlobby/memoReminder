package ir.madeinlobby.memoreminder.model;

import java.util.ArrayList;

public class Person {
    private String username;
    private String password;
    private ArrayList<String> allPostsIds;
    private ArrayList<Tag> allTags;
    private String firstName;
    private String lastName;

    public Person(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.allPostsIds = new ArrayList<>();
        this.allTags = new ArrayList<>();
    }
}
