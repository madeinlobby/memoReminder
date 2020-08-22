package ir.madeinlobby.memoreminder.controller;

import java.util.ArrayList;

import ir.madeinlobby.memoreminder.model.Comment;
import ir.madeinlobby.memoreminder.model.Person;
import ir.madeinlobby.memoreminder.model.Post;

public class MainManager {
    private ArrayList<Person> allPersons;
    private ArrayList<Comment> allComments;
    private ArrayList<Post> allPosts;
    private static MainManager instance = new MainManager();

    public ArrayList<Person> getAllPersons() {
        return allPersons;
    }

    public ArrayList<Comment> getAllComments() {
        return allComments;
    }

    public ArrayList<Post> getAllPosts() {
        return allPosts;
    }

    public static MainManager getInstance() {
        return instance;
    }

    private MainManager() {
        this.allComments = new ArrayList<>();
        this.allPosts = new ArrayList<>();
        this.allPersons = new ArrayList<>();
    }
}
