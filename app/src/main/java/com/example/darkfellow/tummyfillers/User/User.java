package com.example.darkfellow.tummyfillers.User;

/**
 * Created by darkfellow on 4/8/18.
 */

public class User {
    private String Name;
    private String Password;

    public User(String s, String toString) {
    }

    public User(String s, String email, String name, String password) {
        Name = name;
        Password = password;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
