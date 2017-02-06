package com.mobile.group22;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseUser;


public class StarterApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        //기기마다 고유 번호 가지게 함.
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
