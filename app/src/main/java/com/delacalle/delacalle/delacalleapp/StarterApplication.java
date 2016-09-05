package com.delacalle.delacalle.delacalleapp;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseRole;
import com.parse.ParseUser;
import io.fabric.sdk.android.Fabric;

/**
 * Created by pc on 31/01/2016.
 */
public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());



        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
       /* ParseObject.registerSubclass(categoriesClass.class);
        ParseObject.registerSubclass(tagsClass.class);
        ParseObject.registerSubclass(userinfoClass.class);
        ParseObject.registerSubclass(frienduserClass.class);
        ParseObject.registerSubclass(postClass.class);
        ParseObject.registerSubclass(favoritesclass.class);
        ParseObject.registerSubclass(clasejemplopost.class);
        ParseCrashReporting.enable(this);*/
        // ParseAnalytics.trackAppOpenedInBackground();
        //    Parse.enableLocalDatastore(this);
        // Add your initialization code here
                                       //Aplication id                               //client key
        Parse.initialize(this, "PUfCianytiiGSlcFQtt8Y58AFA4BHJKBJhbq6N9L", "ryvz9ligk0xsP1QOTMLFosx0ELBisLZaLP4HmNQN");
        ParseInstallation.getCurrentInstallation().saveInBackground();


        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
        // By specifying no write privileges for the ACL, we can ensure the role cannot be altered.
        /*ParseACL roleACL = new ParseACL();
        roleACL.setPublicReadAccess(true);
        roleACL.setPublicWriteAccess(true);
        ParseRole role = new ParseRole("Administrador", roleACL);
        role.getUsers().add(ParseUser.getCurrentUser());
        role.saveInBackground();*/












        //     ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }


}
