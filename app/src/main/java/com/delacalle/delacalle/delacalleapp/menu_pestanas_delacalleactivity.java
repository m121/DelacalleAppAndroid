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

public class menu_pestanas_delacalleactivity extends AppCompatActivity {

    private Toolbar mToolbar;
    Bitmap pic;
    private ParseQueryAdapter<ParseObject> restaurantesQueryAdapter;

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
                querybuscar.whereEqualTo("titulo", titulo);
                querybuscar.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            // Show results  in listview with my own adapter ParseQueryAdapter
                            ParseQueryAdapter.QueryFactory<ParseObject> factory =
                                    new ParseQueryAdapter.QueryFactory<ParseObject>() {
                                        public ParseQuery<ParseObject> create() {
                                            ParseQuery<ParseObject> query = ParseQuery.getQuery("restaurante");
                                            query.whereExists(titulo);


                                            return query;
                                        }
                                    };

                            restaurantesQueryAdapter = new ParseQueryAdapter<ParseObject>(getApplication(), factory) {

                                @Override
                                public View getItemView(final ParseObject resta, View view, ViewGroup parent) {
                                    if (view == null) {
                                        view = View.inflate(getContext(), R.layout.plantilla_mostrarrestaurante_delacalle, null);
                                    }

                                    TextView titletxt = (TextView) view.findViewById(R.id.editTextnombremostrarrestaurante);
                                          TextView descriptiontxt = (TextView) view.findViewById(R.id.editTextdescripcionmostrarrestaurante);
                                    //         TextView menutxt = (TextView) view.findViewById(R.id.editTextmenumostrarrestaurante);
                                    final ImageView picimageview = (ImageView) view.findViewById(R.id.imageViewfotounomostrarrestaurante);
                                    RatingBar ratingbarres = (RatingBar) view.findViewById(R.id.ratingBarmostrarrestaurante);
                                    ParseFile picfile;

                                    titletxt.setText(resta.getString("titulo"));
                                          descriptiontxt.setText(resta.getString("descripcion"));
                                    //        menutxt.setText(resta.getString("menu"));
                                    picfile = resta.getParseFile("fotouno");
                                    picfile.getDataInBackground(new GetDataCallback() {
                                        @Override
                                        public void done(byte[] data, ParseException e) {
                                            pic = BitmapFactory.decodeByteArray(data, 0, data.length);
                                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                            pic.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                                            picimageview.setImageBitmap(pic);
                                        }
                                    });
                                    ratingbarres.setRating(resta.getInt("rating"));


                                    return view;
                                }
                            };

                            ListView restaListView = (ListView) findViewById(R.id.listViewrestaurantes);
                            restaListView.setAdapter(restaurantesQueryAdapter);
                            searchView.requestFocus();
                            Toast.makeText(menu_pestanas_delacalleactivity.this, "Lo has encontrado!", Toast.LENGTH_SHORT).show();
                        } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                            Toast.makeText(menu_pestanas_delacalleactivity.this, "Lo siento, no lo has encontrado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
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
}
