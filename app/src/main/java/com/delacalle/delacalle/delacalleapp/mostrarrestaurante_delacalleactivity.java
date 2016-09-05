package com.delacalle.delacalle.delacalleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

public class mostrarrestaurante_delacalleactivity extends AppCompatActivity {

    private ParseQueryAdapter<ParseObject> restaurantesQueryAdapter;


    private Toolbar mToolbar;
    Bitmap pic;


    TextView titletxt;
    TextView descriptiontxt;
    TextView menutxt;
    ImageView picimageview;
    RatingBar ratingbarres;
    ParseFile picfile;

    String id;
    private float ratingR;
    int votos;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrarrestaurante_delacalleactivity);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        try
        {
        // Show results  in listview with my own adapter ParseQueryAdapter
        ParseQueryAdapter.QueryFactory<ParseObject> factory =
                new  ParseQueryAdapter.QueryFactory<ParseObject>(){
                    public ParseQuery<ParseObject> create () {
                        ParseQuery<ParseObject>  query = ParseQuery.getQuery("restaurante");
                        query.orderByAscending("createAt");
                        return query;
                    }
                };

        restaurantesQueryAdapter = new ParseQueryAdapter<ParseObject>(this,factory)
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
         //       TextView menutxt = (TextView) view.findViewById(R.id.editTextmenumostrarrestaurante);
        final   ImageView picimageview = (ImageView) view.findViewById(R.id.imageViewfotounomostrarrestaurante);
                RatingBar ratingbarres = (RatingBar) view.findViewById(R.id.ratingBarmostrarrestaurante);
                ParseFile picfile;

                cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        resta.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                id = resta.getObjectId().toString();
                         //       displayPopupdetalleResta(v);
                            }
                        });

                    }
                });

                titletxt.setText(resta.getString("titulo"));
           //     descriptiontxt.setText(resta.getString("descripcion"));
        //        menutxt.setText(resta.getString("menu"));
                picfile = resta.getParseFile("fotouno");
                picfile.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] data, ParseException e) {
                        pic = BitmapFactory.decodeByteArray(data, 0, data.length);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        pic.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                        picimageview.setImageBitmap(Bitmap.createScaledBitmap(pic,400,400,false));
                    }
                });
                ratingbarres.setRating(resta.getInt("rating"));


                return view;
            }
        };

        ListView restaListView = (ListView) this.findViewById(R.id.listViewrestaurantes);
        restaListView.setAdapter(restaurantesQueryAdapter);

        }catch(Exception e)
        {
            e.getStackTrace();
            Log.d("delacalle", "error en mostrar restaurante");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mostrarrestaurante_delacalleactivity, menu);
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

       /* if(id == R.id.action_agregar)
        {
            Intent intent = new Intent(mostrarrestaurante_delacalleactivity.this,agregarrestaurante_delacalleactivity.class);
            startActivity(intent);
        }*/

           if(id == R.id.action_logout)
        {
            if(ParseUser.getCurrentUser() != null)
            {
                ParseUser.getCurrentUser().logOut();
                Intent intent = new Intent(mostrarrestaurante_delacalleactivity.this,iniciosesion_delacalleactivity.class);
                startActivity(intent);
            }
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        // or call onBackPressed()
        return true;
    }


  /*  private void displayPopupdetalleResta(final View anchorView) {
        final PopupWindow popup = new PopupWindow(mostrarrestaurante_delacalleactivity.this);
        View layout = getLayoutInflater().inflate(R.layout.popupdetallerestaurante, null);
        popup.setContentView(layout);


        titletxt = (TextView) layout.findViewById(R.id.editTextnombremostrarrestaurante);
        descriptiontxt = (TextView) layout.findViewById(R.id.editTextdescripcionmostrarrestaurante);
        menutxt = (TextView) layout.findViewById(R.id.editTextplato1detallerestaurante);
        picimageview = (ImageView) layout.findViewById(R.id.imageViewfotounomostrarrestaurante);
        ratingbarres = (RatingBar) layout.findViewById(R.id.ratingBarmostrarrestaurante);
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
                    menutxt.setText(object.getString("menu"));
                    picfile = object.getParseFile("fotouno");
                    picfile.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            pic = BitmapFactory.decodeByteArray(data, 0, data.length);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            pic.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            picimageview.setImageBitmap(Bitmap.createScaledBitmap(pic, 200, 120, false));
                        }
                    });

ratingbarres.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
    @Override
    public void onRatingChanged(RatingBar ratingBar,  float rating, boolean fromUser) {
        rating = (rating + ratingR) / votos;
        final float rate = rating;

        final ParseQuery<ParseObject> usuarioRvotarq = ParseQuery.getQuery("restaurante");
        usuarioRvotarq.whereEqualTo("objectId", id);
        usuarioRvotarq.whereEqualTo("usuarioid", ParseUser.getCurrentUser());
        usuarioRvotarq.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject objs, ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "¡No puedes votar en tus restaurantes!", Toast.LENGTH_SHORT).show();
                } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                    ParseQuery<ParseObject> usuariovotoq = ParseQuery.getQuery("calificacion");
                    usuariovotoq.whereEqualTo("idrestaurante", id);
                    usuariovotoq.whereEqualTo("idusuario", ParseUser.getCurrentUser().getObjectId());
                    usuariovotoq.whereEqualTo("voto",1);
                    usuariovotoq.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject obj, ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(), "¡Solamente puedes calificar una vez este restaurante!", Toast.LENGTH_SHORT).show();
                            } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {

                                object.increment("votos", 1);
                                object.put("rating", rate);
                                object.saveEventually();
                                Toast.makeText(getApplicationContext(), "Gracias por votar", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getApplicationContext(), "Error en mostrar", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Set content width and height
        popup.setHeight(600);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
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










    }*/
}
