package com.delacalle.delacalle.delacalleapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
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
//import com.uxcam.UXCam;

public class menu_pestanas_delacalleactivity extends AppCompatActivity {

    private Toolbar mToolbar;
    Bitmap pic;
    private ParseQueryAdapter<ParseObject> restaurantesQueryAdapter;

    ArrayList<String> precio;

    ArrayList<String> descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pestanas_delacalleactivity);
     //   UXCam.startWithKey("081340b7b1a4968");   //ux cam analytics
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        /*StarterApplication application = (StarterApplication) getApplication();
         = application.getDefaultTracker();

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());*/
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here
            Target viewtarget = new ViewTarget(R.id.tabs,this);

            new ShowcaseView.Builder(this)
                    .setTarget(viewtarget)
                    .setContentTitle("Pestañas")
                    .setContentText("Puedes hacer swipe y deslizarte por la pestaña de restaurantes mejor puntuados y la lista de todos los restaurantes")
                    .hideOnTouchOutside()
                    .build();

            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }



       /* .setTarget(viewtarget2)
                .setContentTitle("buscador")
                .setContentText("Puedes buscar por dirección, precio, descripción, plato o nombre de restaurante")*/

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

        final ArrayList<String> precio = new ArrayList<String>();

        descripcion = new ArrayList<String>();



    }


    @Override
    public void onStart() {
        super.onStart();

    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
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

        try {
            final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(final String titulo) {
                    // perform query here
                    ParseQuery<ParseObject> querybuscar = ParseQuery.getQuery("restaurante");
                    querybuscar.whereContains("nombre", titulo);

                    querybuscar.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                ProgressDialog.show(menu_pestanas_delacalleactivity.this, "Buscando", "Espera mientras busca " + titulo,true,true);
                                Intent intent = new Intent(menu_pestanas_delacalleactivity.this, busquedanombre_delacalleactivity.class);
                                intent.putExtra("titulo", titulo);
                                startActivity(intent);
                                Log.d("delacalle", "nombre de restaurante encontrado");
                                Toast.makeText(menu_pestanas_delacalleactivity.this, "Lo has encontrado!", Toast.LENGTH_SHORT).show();
                            } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                Log.d("delacalle", "nombre de restaurante no encontrado");
                           //     Toast.makeText(menu_pestanas_delacalleactivity.this, "Lo siento, no lo has encontrado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    ParseQuery<ParseObject> queryprecio = new ParseQuery<ParseObject>("carta");
                    queryprecio.whereEqualTo("precio", titulo);
                    queryprecio.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject pre, ParseException e) {
                            if (e == null) {
                                ProgressDialog.show(menu_pestanas_delacalleactivity.this, "Buscando", "Espera mientras busca precios hasta " + titulo,true,true);
                                Intent intent = new Intent(menu_pestanas_delacalleactivity.this, busquedaprecio_delacalleactivity.class);
                                intent.putExtra("titulo", titulo);
                                startActivity(intent);
                                Log.d("delacalle", "precio de plato encontrado");
                                //      Toast.makeText(menu_pestanas_delacalleactivity.this, "¡Lo has encontrado!,restaurantes con precios hasta " + titulo + " pesos", Toast.LENGTH_SHORT).show();


                            } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                Log.d("delacalle", "precio de plato no encontrado");
                            //         Toast.makeText(menu_pestanas_delacalleactivity.this, "Lo siento, no lo has encontrado", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    ParseQuery<ParseObject> querydescrip = ParseQuery.getQuery("restaurante");
                    querydescrip.whereContains("descripcion", titulo);
                    querydescrip.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                ProgressDialog.show(menu_pestanas_delacalleactivity.this, "Buscando", "Espera mientras busca " + titulo,true,true);
                                Intent intent = new Intent(menu_pestanas_delacalleactivity.this, busquedadescripcion_delacalleactivity.class);
                                intent.putExtra("titulo", titulo);
                                startActivity(intent);
                                Log.d("delacalle", "descripcion encontrado");
                                Toast.makeText(menu_pestanas_delacalleactivity.this, "Lo has encontrado!", Toast.LENGTH_SHORT).show();
                            } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                Log.d("delacalle", "descripcion no encontrado");
                                //     Toast.makeText(menu_pestanas_delacalleactivity.this, "Lo siento, no lo has encontrado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    ParseQuery<ParseObject> querydir = ParseQuery.getQuery("restaurante");
                    querydir.whereContains("direccion", titulo);
                    querydir.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                ProgressDialog.show(menu_pestanas_delacalleactivity.this, "Buscando", "Espera mientras busca " + titulo,true,true);
                                Intent intent = new Intent(menu_pestanas_delacalleactivity.this, busquedadireccion_delacalleactivity.class);
                                intent.putExtra("titulo", titulo);
                                startActivity(intent);
                                Log.d("delacalle", "direccion encontrada");
                                Toast.makeText(menu_pestanas_delacalleactivity.this, "Lo has encontrado!", Toast.LENGTH_SHORT).show();
                            } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                Log.d("delacalle", "direccion no encontrada");
                                //     Toast.makeText(menu_pestanas_delacalleactivity.this, "Lo siento, no lo has encontrado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                    ParseQuery<ParseObject> querycartan = new ParseQuery<ParseObject>("carta");
                    querycartan.whereEqualTo("nombre", titulo);
                    querycartan.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject car, ParseException e) {
                            if (e == null) {
                                ProgressDialog.show(menu_pestanas_delacalleactivity.this, "Buscando", "Espera mientras busca " + titulo,true,true);
                                Intent intent = new Intent(menu_pestanas_delacalleactivity.this, busquedacarta_delacalleactivity.class);
                                intent.putExtra("titulo", titulo);
                                startActivity(intent);
                                Log.d("delacalle", "nombre de plato encontrado");
                                Toast.makeText(menu_pestanas_delacalleactivity.this, "¡Lo has encontrado!,cartas con platos de " + titulo, Toast.LENGTH_SHORT).show();


                            } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                Log.d("delacalle", "nombre de plato no encontrado");
                         //       Toast.makeText(menu_pestanas_delacalleactivity.this, "Lo siento, no lo has encontrado", Toast.LENGTH_SHORT).show();

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

        } catch (Exception e) {
            e.getStackTrace();
            Log.d("delacalle", "error en buscar");
        }

        return super.onCreateOptionsMenu(menu);

    }


    // Ocultar el teclado
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                    INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        } catch (Exception e) {
            e.getStackTrace();
            Log.d("delacalle", "Error en ocultar teclado");

        }

        return true;
    }


}
