package com.delacalle.delacalle.delacalleapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class agregarcarta_delacalleactivity extends AppCompatActivity {

    private ImageView fotocartaplato1;
    private TextView  nombreplato1;
    private TextView  descripcionplato1;
    private TextView  precioplato1;

    private ImageView fotocartaplato2;
    private TextView  nombreplato2;
    private TextView  descripcionplato2;
    private TextView  precioplato2;

    private ImageView fotocartaplato3;
    private TextView  nombreplato3;
    private TextView  descripcionplato3;
    private TextView  precioplato3;

    private ImageView fotocartaplato4;
    private TextView  nombreplato4;
    private TextView  descripcionplato4;
    private TextView  precioplato4;

    private ImageView fotocartaplato5;
    private TextView  nombreplato5;
    private TextView  descripcionplato5;
    private TextView  precioplato5;

    private ImageView fotocartaplato6;
    private TextView  nombreplato6;
    private TextView  descripcionplato6;
    private TextView  precioplato6;

    private Button btnguardarcarta;

    View focusView;

    private String nombreplato1C;
    private String descripcionplato1C;
    private String precioplato1C;

    Bitmap pic;
    Bitmap pic2;
    Bitmap pic3;
    Bitmap pic4;
    Bitmap pic5;
    Bitmap pic6;

    ParseFile fotocartafile1;
    ParseFile fotocartafile2;
    ParseFile fotocartafile3;
    ParseFile fotocartafile4;
    ParseFile fotocartafile5;
    ParseFile fotocartafile6;

    // Popup fotos
    RelativeLayout relativealbum;
    RelativeLayout relativefoto;


    // Seleccionar la foto
    public static final int IMAGEREQUESTCODE = 45535;
    public static final int IMAGEREQUESTCODE2 = 45536;
    public static final int IMAGEREQUESTCODE3 = 45537;
    Intent galleryIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregarcarta_delacalleactivity);


        fotocartaplato1 = (ImageView) findViewById(R.id.imageViewAfotoplato1);
        nombreplato1 = (TextView) findViewById(R.id.editTextAnombreplato1);
        descripcionplato1 = (TextView) findViewById(R.id.editTextAdescripcionplato1);
        precioplato1 = (TextView) findViewById(R.id.editTextAprecioplato1);

        fotocartaplato2 = (ImageView) findViewById(R.id.imageViewAfotoplato2);
        nombreplato2 = (TextView) findViewById(R.id.editTextAnombreplato2);
        descripcionplato2 = (TextView) findViewById(R.id.editTextAdescripcionplato2);
        precioplato2 = (TextView) findViewById(R.id.editTextAprecioplato2);

        fotocartaplato3 = (ImageView) findViewById(R.id.imageViewAfotoplato3);
        nombreplato3 = (TextView) findViewById(R.id.editTextAnombreplato3);
        descripcionplato3 = (TextView) findViewById(R.id.editTextAdescripcionplato3);
        precioplato3 = (TextView) findViewById(R.id.editTextAprecioplato3);

        fotocartaplato4 = (ImageView) findViewById(R.id.imageViewAfotoplato4);
        nombreplato4 = (TextView) findViewById(R.id.editTextAnombreplato4);
        descripcionplato4 = (TextView) findViewById(R.id.editTextAdescripcionplato4);
        precioplato4 = (TextView) findViewById(R.id.editTextAprecioplato4);

        fotocartaplato5 = (ImageView) findViewById(R.id.imageViewAfotoplato5);
        nombreplato5 = (TextView) findViewById(R.id.editTextAnombreplato5);
        descripcionplato5 = (TextView) findViewById(R.id.editTextAdescripcionplato5);
        precioplato5 = (TextView) findViewById(R.id.editTextAprecioplato5);

        fotocartaplato6 = (ImageView) findViewById(R.id.imageViewAfotoplato6);
        nombreplato6 = (TextView) findViewById(R.id.editTextAnombreplato6);
        descripcionplato6 = (TextView) findViewById(R.id.editTextAdescripcionplato6);
        precioplato6 = (TextView) findViewById(R.id.editTextAprecioplato6);

        btnguardarcarta = (Button) findViewById(R.id.btnGuardarCarta);

        fotocartaplato1.setClickable(true);
        fotocartaplato2.setClickable(true);
        fotocartaplato3.setClickable(true);
        fotocartaplato4.setClickable(true);
        fotocartaplato5.setClickable(true);
        fotocartaplato6.setClickable(true);

        galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);


        btnguardarcarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errors();
            }
        });

        fotocartaplato1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopupFotoPlato1(v);
            }
        });
    }

    public void errors()
    {
        cleanerrors();
        boolean cancel = false;


        nombreplato1C = nombreplato1.getText().toString();
        descripcionplato1C = descripcionplato1.getText().toString();
        precioplato1C = precioplato1.getText().toString();


        if(TextUtils.isEmpty(nombreplato1C))
        {
            nombreplato1.setError("Es necesario escribir el nombre del primer plato");
            focusView = nombreplato1;
            cancel = true;
        }

        if(TextUtils.isEmpty(descripcionplato1C))
        {
            descripcionplato1.setError("Es necesario escribir la descripción del primer plato");
            focusView = descripcionplato1;
            cancel = true;
        }

        if(TextUtils.isEmpty(precioplato1C))
        {
            precioplato1.setError("Es necesario escribir el precio del primer plato");
            focusView = precioplato1;
            cancel = true;
        }



        if(fotocartaplato1 == null)
        {
            Toast.makeText(getApplicationContext(), "Es necesario guardar una foto del primer plato", Toast.LENGTH_SHORT).show();
            focusView = nombreplato1;
            cancel = true;
        }






        if(cancel)
        {
            focusView.requestFocus();
        }
        else
        {
            agregarCarta();
        }

    }

    public void cleanerrors()
    {
        nombreplato1.setError(null);
        descripcionplato1.setError(null);
        precioplato1.setError(null);


    }

    public void agregarCarta()
    {



        ParseACL acl = new ParseACL();
        acl.setPublicWriteAccess(true);
        acl.setPublicReadAccess(true);


        ParseObject carta = new ParseObject("carta");
        carta.put("nombre",nombreplato1C);
        carta.put("descripcion",descripcionplato1C);
        carta.put("precio", precioplato1C);
        carta.put("restauranteId","1");
        carta.setACL(acl);
        carta.saveInBackground();


        Toast.makeText(getApplicationContext(), "Guardado", Toast.LENGTH_SHORT).show();
        Log.d("delacalle", "Carta creada");
        Intent intent = new Intent(agregarcarta_delacalleactivity.this,menu_pestanas_delacalleactivity.class);
        startActivity(intent);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agregarcarta_delacalleactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

       /* //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    private void displayPopupFotoPlato1(final View anchorView) {
        final PopupWindow popup = new PopupWindow(agregarcarta_delacalleactivity.this);
        LayoutInflater inflater = (LayoutInflater) agregarcarta_delacalleactivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popupfotos, null);
        popup.setContentView(layout);


        relativealbum = (RelativeLayout) layout.findViewById(R.id.relativelayoutAlbum);
        relativefoto = (RelativeLayout) layout.findViewById(R.id.relativelayoutTomarFoto);
        relativealbum.setClickable(true);
        relativefoto.setClickable(true);


        relativealbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectpic();
            }
        });

        relativefoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takephoto1();
            }
        });





        // Set content width and height
        popup.setHeight(400);
        popup.setWidth(400);
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

    private void displayPopupFotoPlato2(final View anchorView) {
        final PopupWindow popup = new PopupWindow(agregarcarta_delacalleactivity.this);
        LayoutInflater inflater = (LayoutInflater) agregarcarta_delacalleactivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popupfotos, null);
        popup.setContentView(layout);


        relativealbum = (RelativeLayout) layout.findViewById(R.id.relativelayoutAlbum);
        relativefoto = (RelativeLayout) layout.findViewById(R.id.relativelayoutTomarFoto);
        relativealbum.setClickable(true);
        relativefoto.setClickable(true);


        relativealbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectpic2();
            }
        });

        relativefoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takephoto2();
            }
        });





        // Set content width and height
        popup.setHeight(400);
        popup.setWidth(400);
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

    public void selectpic()
    {

        //select pic from gallery
        startActivityForResult(galleryIntent, IMAGEREQUESTCODE);
    }

    public void selectpic2()
    {

        //select pic from gallery
        startActivityForResult(galleryIntent, IMAGEREQUESTCODE2);
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
            fotocartafile1 = new ParseFile("fotocartaplato1.jpg", data1);
            fotocartafile1.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });


            fotocartaplato1.setImageBitmap(Bitmap.createScaledBitmap(pic, 400, 400, false));

        }

        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bundle extras = i.getExtras();
            pic2 = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic2.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data1 = stream.toByteArray();
            fotocartafile2 = new ParseFile("fotocartaplato2.jpg", data1);
            fotocartafile2.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });


            fotocartaplato2.setImageBitmap(Bitmap.createScaledBitmap(pic2, 400, 400, false));

        }

      /*  if (requestCode == 3 && resultCode == RESULT_OK) {
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


            //    imageviewfoto3.setImageBitmap(Bitmap.createScaledBitmap(pic3,400,400,false));

        }*/

        if(requestCode == IMAGEREQUESTCODE && resultCode == RESULT_OK)
        {

            manageImageFromUri1(i.getData());

        }
        else
        {
            Log.d("DeLaCalle", "Error en seleccionar la foto");
        }

        if(requestCode == IMAGEREQUESTCODE2 && resultCode == RESULT_OK)
        {

            manageImageFromUri2(i.getData());

        }
        else
        {
            Log.d("DeLaCalle", "Error en seleccionar la foto");
        }

        if(requestCode == IMAGEREQUESTCODE3 && resultCode == RESULT_OK)
        {

         //   manageImageFromUri3(i.getData());

        }
        else
        {
            Log.d("DeLaCalle", "Error en seleccionar la foto");
        }
    }



    private void manageImageFromUri1(Uri imageUri) {
        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(), imageUri);

        } catch (Exception e) {
            // Manage exception ...

        }

        if (bitmap != null) {
            // Here you can use bitmap in your application ...
            pic = bitmap;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data = stream.toByteArray();
            fotocartafile1 = new ParseFile("fotocartaplato1.jpg",data);
            fotocartafile1.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            fotocartaplato1.setImageBitmap(Bitmap.createScaledBitmap(pic, 400, 400, false));


        }
        else
        {
            Log.d("Delacalle", "Error bitmap  null");
        }
    }

    private void manageImageFromUri2(Uri imageUri) {
        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(), imageUri);

        } catch (Exception e) {
            // Manage exception ...

        }

        if (bitmap != null) {
            // Here you can use bitmap in your application ...
            pic2 = bitmap;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic2.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data = stream.toByteArray();
            fotocartafile2 = new ParseFile("fotocartaplato2.jpg",data);
            fotocartafile2.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            fotocartaplato2.setImageBitmap(Bitmap.createScaledBitmap(pic2, 400, 400,false));


        }
        else
        {
            Log.d("Delacalle", "Error bitmap  null");
        }
    }
}
