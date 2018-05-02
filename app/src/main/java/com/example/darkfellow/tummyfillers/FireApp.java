package com.example.darkfellow.tummyfillers;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by darkfellow on 4/14/18.
 */

public class FireApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
