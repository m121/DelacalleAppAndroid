package com.delacalle.delacalle.delacalleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.SaveCallback;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class busquedaprecio_delacalleactivity  extends AppCompatActivity {



    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;




    private Toolbar mToolbar;
    Bitmap pic;

    String id;
      ArrayList<String> precio;

 //   String precio;

    String titulo;

    String restaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busquedaprecio_delacalleactivity);

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        Tracker tracker = analytics.newTracker("UA-77841203-3");
        tracker.setScreenName("busquedaprecio");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        try
        {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            titulo = bundle.getString("titulo");

        }
        else
        {
            Log.d("delacalle", "Error al pasar el titulo " + titulo);
        }

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        precio = new ArrayList<String>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("carta");
        query.whereLessThanOrEqualTo("precio", titulo);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject restaurantes : objects) {
                        String carta = restaurantes.getString("nombre");
                        precio.add(carta);
                    }
                    mPager.setAdapter(mPagerAdapter);
                }
            }
        });



        }catch(Exception e)
        {
            e.getStackTrace();
            Log.d("delacalle", "error en mostrar busqueda precio");
        }











    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_busquedaprecio_delacalleactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

      /*  //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }


    public boolean onSupportNavigateUp(){
        finish();
        // or call onBackPressed()
        return true;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlideCartaFragment.create(position+1,precio.get(position).toString());
        }

        @Override
        public int getCount() {
            return precio.size();
        }
    }
}

