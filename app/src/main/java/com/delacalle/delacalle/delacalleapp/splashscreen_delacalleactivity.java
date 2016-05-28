package com.delacalle.delacalle.delacalleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.parse.ParseUser;

import java.util.Timer;
import java.util.TimerTask;

public class splashscreen_delacalleactivity extends AppCompatActivity {

    // Set the duration of the splash screen
    private static final long SPLASH_SCREEN_DELAY = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen_delacalleactivity);



        try
        {
        ParseUser currentUser = ParseUser.getCurrentUser();
        // Keep log in
        if (currentUser != null) {
            // Start an intent for the logged in activity
            startActivity(new Intent(this, menu_pestanas_delacalleactivity.class));
        } else {
            // Start and intent for the logged out activity
            TimerTask task = new TimerTask() {
                @Override
                public void run() {

                    // Start the next activity
                    Intent mainIntent = new Intent().setClass(
                            splashscreen_delacalleactivity.this, intro_delacalleactivity.class);
                    startActivity(mainIntent);

                    // Close the activity so the user won't able to go back this
                    // activity pressing Back button
                    finish();
                }
            };

            // Simulate a long loading process on application startup.
            Timer timer = new Timer();
            timer.schedule(task, SPLASH_SCREEN_DELAY);
        }
        }catch(Exception e)
        {
            e.getStackTrace();
            Log.d("delacalle", "error en splashscreen");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splashscreen_delacalleactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
