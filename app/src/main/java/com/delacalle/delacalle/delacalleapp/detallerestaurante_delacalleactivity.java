package com.delacalle.delacalle.delacalleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;

public class detallerestaurante_delacalleactivity extends AppCompatActivity {

    Bitmap pic;
    Bitmap pic2;
    Bitmap pic3;


    TextView titletxt;
    TextView descriptiontxt;
    TextView plato1txt;
    TextView plato2txt;
    TextView plato3txt;
    ImageView picimageview1;
    ImageView picimageview2;
    ImageView picimageview3;
    RatingBar ratingbarres;
    ParseFile picfile1;
    ParseFile picfile2;
    ParseFile picfile3;

    String id;
    private float ratingR;
    int votos;


    private int mPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallerestaurante_delacalleactivity);

        /*Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");

        }*/



        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpagerdetalle);
        viewPager.setAdapter(new FragmentPagerAdapterDetalle(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabsdetalle);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);



     /*   titletxt = (TextView) findViewById(R.id.editTextnombremostrarrestaurante);
        descriptiontxt = (TextView) findViewById(R.id.editTextdescripcionmostrarrestaurante);
        plato1txt = (TextView) findViewById(R.id.editTextplato1detallerestaurante);
        plato2txt = (TextView) findViewById(R.id.editTextplato2detallerestaurante);
        plato3txt = (TextView) findViewById(R.id.editTextplato3detallerestaurante);
        picimageview1 = (ImageView) findViewById(R.id.imageViewfotounodetalle);
        picimageview2 = (ImageView) findViewById(R.id.imageViewfotodosdetalle);
        picimageview3 = (ImageView) findViewById(R.id.imageViewfototresdetalle);
        ratingbarres = (RatingBar) findViewById(R.id.ratingBarmostrarrestaurante);
        ratingbarres.setClickable(true);





        ParseQuery<ParseObject> querymostrareditar = ParseQuery.getQuery("restaurante");
        querymostrareditar.whereEqualTo("objectId", id);
        querymostrareditar.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                if (e == null) {

                    ratingR = object.getInt("rating");
                    votos = object.getInt("votos");
                    titletxt.setText(object.getString("titulo"));
                    descriptiontxt.setText(object.getString("descripcion"));
                    plato1txt.setText(object.getString("plato1"));
                    plato2txt.setText(object.getString("plato2"));
                    plato3txt.setText(object.getString("plato3"));
                    picfile1 = object.getParseFile("fotouno");
                    picfile1.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            pic = BitmapFactory.decodeByteArray(data, 0, data.length);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            pic.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            picimageview1.setImageBitmap(Bitmap.createScaledBitmap(pic, 200, 70, false));
                        }
                    });
                    picfile2 = object.getParseFile("fotodos");
                    picfile2.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            pic2 = BitmapFactory.decodeByteArray(data, 0, data.length);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            pic2.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            picimageview2.setImageBitmap(Bitmap.createScaledBitmap(pic2, 200, 120, false));
                        }
                    });
                    picfile3 = object.getParseFile("fototres");
                    picfile3.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            pic3 = BitmapFactory.decodeByteArray(data, 0, data.length);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            pic3.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            picimageview3.setImageBitmap(Bitmap.createScaledBitmap(pic3, 200, 120, false));
                        }
                    });

                    ratingbarres.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            rating = (rating + ratingR) / votos;
                            final float rate = rating;

                            final ParseQuery<ParseObject> usuarioRvotarq = ParseQuery.getQuery("restaurante");
                            usuarioRvotarq.whereEqualTo("objectId", id);
                            usuarioRvotarq.whereEqualTo("usuarioid", ParseUser.getCurrentUser());
                            usuarioRvotarq.getFirstInBackground(new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject objs, ParseException e) {
                                    if (e == null) {
                                        Toast.makeText(detallerestaurante_delacalleactivity.this, "¡No puedes votar en tus restaurantes!", Toast.LENGTH_SHORT).show();
                                    } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                        ParseQuery<ParseObject> usuariovotoq = ParseQuery.getQuery("calificacion");
                                        usuariovotoq.whereEqualTo("idrestaurante", id);
                                        usuariovotoq.whereEqualTo("idusuario", ParseUser.getCurrentUser().getObjectId());
                                        usuariovotoq.whereEqualTo("voto", 1);
                                        usuariovotoq.getFirstInBackground(new GetCallback<ParseObject>() {
                                            @Override
                                            public void done(ParseObject obj, ParseException e) {
                                                if (e == null) {
                                                    Toast.makeText(detallerestaurante_delacalleactivity.this, "¡Solamente puedes calificar una vez este restaurante!", Toast.LENGTH_SHORT).show();
                                                } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {

                                                    object.increment("votos", 1);
                                                    object.put("rating", rate);
                                                    object.saveEventually();
                                                    Toast.makeText(detallerestaurante_delacalleactivity.this, "Gracias por votar", Toast.LENGTH_SHORT).show();

                                                    ParseObject votosusuario = new ParseObject("calificacion");
                                                    votosusuario.put("idrestaurante", id);
                                                    votosusuario.put("idusuario", ParseUser.getCurrentUser().getObjectId());
                                                    votosusuario.put("voto", 1);
                                                    votosusuario.saveInBackground();


                                                }
                                            }
                                        });
                                    }

                                }
                            });


                        }
                    });


                } else {
                    Toast.makeText(detallerestaurante_delacalleactivity.this, "Error en mostrar", Toast.LENGTH_SHORT).show();
                }
            }
        });
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detallerestaurante_delacalleactivity, menu);
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
