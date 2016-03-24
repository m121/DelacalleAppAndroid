package com.delacalle.delacalle.delacalleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class listarestaurantesresponsable_delacalleactivity extends AppCompatActivity {

    private ParseQueryAdapter<ParseObject> misrestaurantesQueryAdapter;


    private Toolbar mToolbar;
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

    Button btneditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarestaurantesresponsable_delacalleactivity);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);



        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
// Show results  in listview with my own adapter ParseQueryAdapter
                ParseQueryAdapter.QueryFactory<ParseObject> factory =
                        new  ParseQueryAdapter.QueryFactory<ParseObject>(){
                            public ParseQuery<ParseObject> create () {
                                ParseQuery<ParseObject>  query = ParseQuery.getQuery("restaurante");
                                query.whereEqualTo("usuarioid", ParseUser.getCurrentUser());
                                return query;
                            }
                        };

                misrestaurantesQueryAdapter = new ParseQueryAdapter<ParseObject>(getApplication(),factory)
                {

                    @Override
                    public View getItemView(final ParseObject resta,View view, ViewGroup parent)
                    {
                        if(view == null)
                        {
                            view = View.inflate(getContext(),R.layout.plantilla_mostrarrestaurante_delacalle,null);
                        }
                        CardView cardview = (CardView) view.findViewById(R.id.cardView);
                        cardview.setClickable(true);
                        TextView titletxt = (TextView) view.findViewById(R.id.editTextnombremostrarrestaurante);
                        TextView descriptiontxt = (TextView) view.findViewById(R.id.editTextdescripcionmostrarrestaurante);
                        //       TextView menutxt = (TextView) view.findViewById(R.id.editTextmenumostrarrestaurante);
                        final ImageView picimageview = (ImageView) view.findViewById(R.id.imageViewfotounomostrarrestaurante);
                        RatingBar ratingbarres = (RatingBar) view.findViewById(R.id.ratingBarmostrarrestaurante);
                        ParseFile picfile;

                        //   id = resta.getObjectId().toString();

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

                        cardview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                resta.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        id = resta.getObjectId().toString();
                                        displayPopupdetalleMiResta(v);
                                    }
                                });

                            }
                        });



                        return view;
                    }
                };

                ListView restaListView = (ListView) findViewById(R.id.listViewrestaurantes);
                restaListView.setAdapter(misrestaurantesQueryAdapter);

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // Show results  in listview with my own adapter ParseQueryAdapter
        ParseQueryAdapter.QueryFactory<ParseObject> factory =
                new  ParseQueryAdapter.QueryFactory<ParseObject>(){
                    public ParseQuery<ParseObject> create () {
                        ParseQuery<ParseObject>  query = ParseQuery.getQuery("restaurante");
                        query.whereEqualTo("usuarioid", ParseUser.getCurrentUser());
                        return query;
                    }
                };

        misrestaurantesQueryAdapter = new ParseQueryAdapter<ParseObject>(this,factory)
        {

            @Override
            public View getItemView(final ParseObject resta,View view, ViewGroup parent)
            {
                if(view == null)
                {
                    view = View.inflate(getContext(),R.layout.plantilla_mostrarrestaurante_delacalle,null);
                }
                CardView cardview = (CardView) view.findViewById(R.id.cardView);
                cardview.setClickable(true);
                TextView titletxt = (TextView) view.findViewById(R.id.editTextnombremostrarrestaurante);
                //        TextView descriptiontxt = (TextView) view.findViewById(R.id.editTextdescripcionmostrarrestaurante);
                //       TextView menutxt = (TextView) view.findViewById(R.id.editTextmenumostrarrestaurante);
                final ImageView picimageview = (ImageView) view.findViewById(R.id.imageViewfotounomostrarrestaurante);
                RatingBar ratingbarres = (RatingBar) view.findViewById(R.id.ratingBarmostrarrestaurante);
                ParseFile picfile;

             //   id = resta.getObjectId().toString();

                titletxt.setText(resta.getString("titulo"));
                //        descriptiontxt.setText(resta.getString("descripcion"));
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

                cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                resta.saveInBackground(new SaveCallback() {
                        @Override
                         public void done(ParseException e) {
                            id = resta.getObjectId().toString();
                            displayPopupdetalleMiResta(v);
                                 }
                        });

                    }
                });



                return view;
            }
        };

        ListView restaListView = (ListView) this.findViewById(R.id.listViewrestaurantes);
        restaListView.setAdapter(misrestaurantesQueryAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listarestaurantesresponsable_delacalleactivity, menu);
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

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        // or call onBackPressed()
        return true;
    }

    private void displayPopupdetalleMiResta(final View anchorView) {
        final PopupWindow popup = new PopupWindow(listarestaurantesresponsable_delacalleactivity.this);
        View layout = getLayoutInflater().inflate(R.layout.popupeditar, null);
        popup.setContentView(layout);

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);


        titletxt = (TextView) layout.findViewById(R.id.editTextnombreeditar);
        descriptiontxt = (TextView) layout.findViewById(R.id.editTextdescripcioneditar);
        plato1txt = (TextView) layout.findViewById(R.id.editTextplato1responsable);
        plato2txt = (TextView) layout.findViewById(R.id.editTextplato2responsable);
        plato3txt = (TextView) layout.findViewById(R.id.editTextplato3responsable);
        picimageview1 = (ImageView) layout.findViewById(R.id.imageViewfotounoresponsable);
        picimageview2 = (ImageView) layout.findViewById(R.id.imageViewfotodosresponsable);
        picimageview3 = (ImageView) layout.findViewById(R.id.imageViewfototresresponsable);
        btneditar  = (Button) layout.findViewById(R.id.btneditarrestaurante);
        picimageview1.setClickable(true);
        picimageview2.setClickable(true);
        picimageview3.setClickable(true);



        ParseQuery<ParseObject> querymostrareditar = ParseQuery.getQuery("restaurante");
        querymostrareditar.whereEqualTo("objectId", id);
        querymostrareditar.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                if (e == null) {
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
                            picimageview1.setImageBitmap(Bitmap.createScaledBitmap(pic, 200, 120, false));
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

                    picimageview1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(animAlpha);
                            takephoto1();
                        }
                    });

                    picimageview2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(animAlpha);
                            takephoto2();
                        }
                    });

                    picimageview3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(animAlpha);
                            takephoto3();
                        }
                    });

                    btneditar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(animAlpha);
                            final String nombre = titletxt.getText().toString();
                            final String descripcion = descriptiontxt.getText().toString();
                            final String plato1 = plato1txt.getText().toString();
                            final String plato2 = plato2txt.getText().toString();
                            final String plato3 = plato3txt.getText().toString();


                            object.put("titulo", nombre);
                            object.put("descripcion", descripcion);
                            object.put("plato1", plato1);
                            object.put("plato2", plato2);
                            object.put("plato3", plato3);
                            object.put("fotouno",picfile1);
                            object.put("fotodos",picfile2);
                            object.put("fototres",picfile3);
                            object.saveInBackground();

                            Toast.makeText(getApplicationContext(), "Actualizado", Toast.LENGTH_SHORT).show();

                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Error en mostrar", Toast.LENGTH_SHORT).show();
                }
            }
        });










        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        popup.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        // Show anchored to button
        //   popup.setBackgroundDrawable(new BitmapDrawable());
        new Handler().postDelayed(new Runnable() {

            public void run() {
                popup.showAtLocation(anchorView, Gravity.TOP | Gravity.START | Gravity.CENTER_VERTICAL, 120, 300);
                popup.showAsDropDown(anchorView);
            }
        }, 100L);




    }

    public void takephoto1()
    {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 1);
    }
    public void takephoto2()
    {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 2);
    }
    public void takephoto3()
    {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 3);
    }

    protected final void onActivityResult(final int requestCode,
                                          final int resultCode, final Intent i) {
        super.onActivityResult(requestCode, resultCode, i);


        // take pic

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = i.getExtras();
            pic = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data1 = stream.toByteArray();
            picfile1 = new ParseFile("fotorestauranteuno.jpg", data1);
            picfile1.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });


            picimageview1.setImageBitmap(Bitmap.createScaledBitmap(pic,200,120,false));

        }

        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bundle extras = i.getExtras();
            pic2 = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic2.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data1 = stream.toByteArray();
            picfile2 = new ParseFile("fotorestaurantedos.jpg", data1);
            picfile2.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });


            picimageview2.setImageBitmap(Bitmap.createScaledBitmap(pic2,200,120,false));

        }

        if (requestCode == 3 && resultCode == RESULT_OK) {
            Bundle extras = i.getExtras();
            pic3 = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic3.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data1 = stream.toByteArray();
            picfile3 = new ParseFile("fotorestaurantetres.jpg", data1);
            picfile3.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });


            picimageview3.setImageBitmap(Bitmap.createScaledBitmap(pic3,200,120,false));

        }
    }


}
