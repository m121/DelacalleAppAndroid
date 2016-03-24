package com.delacalle.delacalle.delacalleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class agregarrestaurante_delacalleactivity extends AppCompatActivity {

    private Toolbar mToolbar;


    private TextView textviewnombreR;
    private TextView textviewdescripcionR;
    private TextView textviewplato1;
    private TextView textviewplato2;
    private TextView textviewplato3;

    private RatingBar ratingbarR;
    private ImageView imageviewfoto1;
    private ImageView imageviewfoto2;
    private ImageView imageviewfoto3;
    private Button btnagregarR;
    private ParseFile filefoto1;
    private ParseFile filefoto2;
    private ParseFile filefoto3;


    private String nombreR;
    private String descripcionR;
    private String plato1;
    private String plato2;
    private String plato3;
    private float ratingR;


    Bitmap pic;
    Bitmap pic2;
    Bitmap pic3;
    View focusView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregarrestaurante_delacalleactivity);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);


        textviewnombreR = (TextView) findViewById(R.id.editTextnombreagregarrestaurante);
        textviewdescripcionR = (TextView) findViewById(R.id.editTextdescripcionagregarrestaurante);
        textviewplato1 = (TextView) findViewById(R.id.editTextplato1agregarrestaurante);
        textviewplato2 = (TextView) findViewById(R.id.editTextplato2agregarrestaurante);
        textviewplato3 = (TextView) findViewById(R.id.editTextplato3agregarrestaurante);
     //   ratingbarR = (RatingBar) findViewById(R.id.ratingBaragregarrestaurante);
        imageviewfoto1 = (ImageView) findViewById(R.id.imageViewfotounoagregarrestaurante);
        imageviewfoto2 = (ImageView) findViewById(R.id.imageViewfotodosagregarrestaurante);
        imageviewfoto3 = (ImageView) findViewById(R.id.imageViewfototresagregarrestaurante);
        btnagregarR = (Button) findViewById(R.id.btnagregarrestaurante);
        imageviewfoto1.setClickable(true);
        imageviewfoto2.setClickable(true);
        imageviewfoto3.setClickable(true);



        btnagregarR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                errors();
            }
        });


        imageviewfoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                takephoto1();

            }
        });

        imageviewfoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                takephoto2();

            }
        });

        imageviewfoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                takephoto3();

            }
        });




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
            filefoto1 = new ParseFile("fotorestauranteuno.jpg", data1);
            filefoto1.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });


            imageviewfoto1.setImageBitmap(Bitmap.createScaledBitmap(pic,200,200,false));

        }

        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bundle extras = i.getExtras();
            pic2 = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic2.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data1 = stream.toByteArray();
            filefoto2 = new ParseFile("fotorestaurantedos.jpg", data1);
            filefoto2.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });


            imageviewfoto2.setImageBitmap(Bitmap.createScaledBitmap(pic2,400,400,false));

        }

        if (requestCode == 3 && resultCode == RESULT_OK) {
            Bundle extras = i.getExtras();
            pic3 = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic3.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data1 = stream.toByteArray();
            filefoto3 = new ParseFile("fotorestaurantetres.jpg", data1);
            filefoto3.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });


            imageviewfoto3.setImageBitmap(Bitmap.createScaledBitmap(pic3,400,400,false));

        }
    }




    public void errors()
    {
        cleanerrors();
        boolean cancel = false;


        nombreR = textviewnombreR.getText().toString();
        descripcionR = textviewdescripcionR.getText().toString();
        plato1 = textviewplato1.getText().toString();
        plato2 = textviewplato2.getText().toString();
        plato3 = textviewplato3.getText().toString();

        if(TextUtils.isEmpty(nombreR))
        {
            textviewnombreR.setError("Es necesario escribir el nombre del restaurante");
            focusView = textviewnombreR;
            cancel = true;
        }

        if(TextUtils.isEmpty(descripcionR))
        {
            textviewdescripcionR.setError("Es necesario escribir la descripción del restaurante");
            focusView = textviewdescripcionR;
            cancel = true;
        }

        if(TextUtils.isEmpty(plato1))
        {
            textviewplato1.setError("Es necesario escribir al menos un plato");
            focusView = textviewplato1;
            cancel = true;
        }

        if(filefoto1 == null)
        {
            Toast.makeText(getApplicationContext(), "Es necesario tomar una foto del restaurante", Toast.LENGTH_SHORT).show();
            focusView = textviewnombreR;
            cancel = true;
        }

        if(filefoto2 == null)
        {
            Toast.makeText(getApplicationContext(), "Es necesario tomar la segunda foto del restaurante", Toast.LENGTH_SHORT).show();
            focusView = textviewnombreR;
            cancel = true;
        }

        if(filefoto3 == null)
        {
            Toast.makeText(getApplicationContext(), "Es necesario tomar la tercera foto del restaurante", Toast.LENGTH_SHORT).show();
            focusView = textviewnombreR;
            cancel = true;
        }


        if(cancel)
        {
            focusView.requestFocus();
        }
        else
        {
            agregarRestaurante();
        }

    }

    public void cleanerrors()
    {
        textviewnombreR.setError(null);
        textviewdescripcionR.setError(null);
        textviewplato1.setError(null);

    }

    public void agregarRestaurante()
    {

//        ratingR = ratingbarR.getRating();

        ParseACL acl = new ParseACL();
        acl.setPublicWriteAccess(true);
        acl.setPublicReadAccess(true);


        ParseObject  restauranteA = new ParseObject("restaurante");
        restauranteA.put("titulo",nombreR);
        restauranteA.put("descripcion",descripcionR);
        restauranteA.put("plato1", plato1);
        restauranteA.put("plato2", plato2);
        restauranteA.put("plato3", plato3);
        restauranteA.increment("rating", 0);
        restauranteA.put("fotouno", filefoto1);
        restauranteA.put("fotodos", filefoto2);
        restauranteA.put("fototres", filefoto3);
        restauranteA.put("usuarioid", ParseUser.getCurrentUser());
        restauranteA.increment("votos", 1);
        restauranteA.setACL(acl);
        restauranteA.saveInBackground();


        Toast.makeText(getApplicationContext(), "Guardado", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(agregarrestaurante_delacalleactivity.this,menu_pestanas_delacalleactivity.class);
        startActivity(intent);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agregarrestaurante_delacalleactivity, menu);
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


        if(id == R.id.action_mostrar)
        {
            Intent intent = new Intent(agregarrestaurante_delacalleactivity.this,mostrarrestaurante_delacalleactivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        // or call onBackPressed()
        return true;
    }
}
