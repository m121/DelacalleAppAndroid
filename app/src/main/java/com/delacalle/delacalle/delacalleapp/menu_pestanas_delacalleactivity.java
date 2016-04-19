package com.delacalle.delacalle.delacalleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseRole;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class menu_pestanas_delacalleactivity extends AppCompatActivity {

    private Toolbar mToolbar;
    Bitmap pic;
    private ParseQueryAdapter<ParseObject> restaurantesQueryAdapter;

    ArrayList<String> precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pestanas_delacalleactivity);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setLogo(R.mipmap.ic_logo);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /// Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);

    final ArrayList<String>   precio = new ArrayList<String>();

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

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_pestanas_delacalleactivity, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String titulo) {
                // perform query here
                ParseQuery<ParseObject> querybuscar = ParseQuery.getQuery("restaurante");
                querybuscar.whereEqualTo("nombre", titulo);
                querybuscar.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            Intent intent = new Intent(menu_pestanas_delacalleactivity.this,busquedanombre_delacalleactivity.class);
                            intent.putExtra("titulo", titulo);
                            startActivity(intent);
                            Log.d("delacalle","nombre de restaurante encontrado");
                            Toast.makeText(menu_pestanas_delacalleactivity.this, "Lo has encontrado!", Toast.LENGTH_SHORT).show();
                        }
                         else if (e.getCode() == ParseException.OBJECT_NOT_FOUND)
                        {
                            Log.d("delacalle","nombre de restaurante no encontrado");
                            Toast.makeText(menu_pestanas_delacalleactivity.this, "Lo siento, no lo has encontrado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                ParseQuery<ParseObject> queryprecio = new ParseQuery<ParseObject>("carta");
                queryprecio.whereEqualTo("precio", titulo);
                queryprecio.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject pre, ParseException e) {
                        if (e == null) {
                            Intent intent = new Intent(menu_pestanas_delacalleactivity.this, busquedaprecio_delacalleactivity.class);
                            intent.putExtra("titulo", titulo);
                            startActivity(intent);
                            Log.d("delacalle", "precio de plato encontrado");
                            Toast.makeText(menu_pestanas_delacalleactivity.this, "¡Lo has encontrado!,restaurantes con precios hasta " + titulo + " pesos", Toast.LENGTH_SHORT).show();


                        } else if(e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                            Log.d("delacalle", "precio de plato no encontrado");
                            Toast.makeText(menu_pestanas_delacalleactivity.this, "Lo siento, no lo has encontrado", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                ParseQuery<ParseObject> querycartan = new ParseQuery<ParseObject>("carta");
                querycartan.whereEqualTo("nombre", titulo);
                querycartan.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject car, ParseException e) {
                        if (e == null) {
                            Intent intent = new Intent(menu_pestanas_delacalleactivity.this, busquedacarta_delacalleactivity.class);
                            intent.putExtra("titulo", titulo);
                            startActivity(intent);
                            Log.d("delacalle", "nombre de plato encontrado");
                            Toast.makeText(menu_pestanas_delacalleactivity.this, "¡Lo has encontrado!,cartas con platos de " + titulo , Toast.LENGTH_SHORT).show();


                        } else if(e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                            Log.d("delacalle", "nombre de plato no encontrado");
                              Toast.makeText(menu_pestanas_delacalleactivity.this, "Lo siento, no lo has encontrado", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected  void onResume()
    {
        super.onResume();


    }
}
