package com.delacalle.delacalle.delacalleapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
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

    private Button btnpaleta;
    Bitmap bitmaperror2;
    Bitmap bitmaperror3;


    Bitmap pic;
    Bitmap pic2;
    Bitmap pic3;
    View focusView;

    //popup paleta
    private Button buttonColor1;
    private Button buttonColor2;
    private Button buttonColor3;
    private Button buttonColor4;
    private Button buttonColor5;
    private Button buttonColor6;
    private RelativeLayout relativepaleta;
    String color;



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
        setContentView(R.layout.activity_agregarrestaurante_delacalleactivity);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        bitmaperror2 = BitmapFactory.decodeResource(getResources(), R.drawable.agregar_foto);
        bitmaperror3 = BitmapFactory.decodeResource(getResources(), R.drawable.agregar_foto);


        textviewnombreR = (TextView) findViewById(R.id.editTextnombreagregarrestaurante);
        textviewdescripcionR = (TextView) findViewById(R.id.editTextdescripcionagregarrestaurante);
        textviewplato1 = (TextView) findViewById(R.id.editTextplato1agregarrestaurante);
        textviewplato2 = (TextView) findViewById(R.id.editTextplato2agregarrestaurante);
        textviewplato3 = (TextView) findViewById(R.id.editTextplato3agregarrestaurante);
        relativepaleta = (RelativeLayout) findViewById(R.id.relativelayoutPaletacambiar);
     //   ratingbarR = (RatingBar) findViewById(R.id.ratingBaragregarrestaurante);
        imageviewfoto1 = (ImageView) findViewById(R.id.imageViewfotounoagregarrestaurante);
        imageviewfoto2 = (ImageView) findViewById(R.id.imageViewfotodosagregarrestaurante);
        imageviewfoto3 = (ImageView) findViewById(R.id.imageViewfototresagregarrestaurante);
        btnagregarR = (Button) findViewById(R.id.btnagregarrestaurante);
        btnpaleta = (Button) findViewById(R.id.btnpaletacolores);
        imageviewfoto1.setClickable(true);
        imageviewfoto2.setClickable(true);
        imageviewfoto3.setClickable(true);


        btnpaleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopupPaleta(v);
            }
        });



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
              //  takephoto1();
                displayPopupFotos1(v);

            }
        });

        imageviewfoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
        //        takephoto2();
                displayPopupFotos2(v);

            }
        });

        imageviewfoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
          //      takephoto3();
                displayPopupFotos3(v);


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

    public void selectpic3()
    {

        //select pic from gallery
        startActivityForResult(galleryIntent, IMAGEREQUESTCODE3);
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


            imageviewfoto1.setImageBitmap(Bitmap.createScaledBitmap(pic,400,400,false));

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

            manageImageFromUri3(i.getData());

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
            filefoto1 = new ParseFile("fotorestaurantetres.jpg",data);
            filefoto1.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            imageviewfoto1.setImageBitmap(Bitmap.createScaledBitmap(pic, 400, 400, false));


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
            filefoto2 = new ParseFile("fotorestaurantetres.jpg",data);
            filefoto2.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            imageviewfoto2.setImageBitmap(Bitmap.createScaledBitmap(pic2,400,400,false));


        }
        else
        {
            Log.d("Delacalle", "Error bitmap  null");
        }
    }

    private void manageImageFromUri3(Uri imageUri) {
        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(), imageUri);

        } catch (Exception e) {
            // Manage exception ...

        }

        if (bitmap != null) {
            // Here you can use bitmap in your application ...
            pic3 = bitmap;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic3.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data = stream.toByteArray();
            filefoto3 = new ParseFile("fotorestaurantetres.jpg",data);
            filefoto3.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            imageviewfoto3.setImageBitmap(Bitmap.createScaledBitmap(pic3,400,400,false));


        }
        else
        {
            Log.d("Delacalle", "Error bitmap  null");
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
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmaperror2.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data = stream.toByteArray();
            filefoto2 = new ParseFile("fotorestaurantedos.jpg",data);
            filefoto2.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            imageviewfoto2.setImageBitmap(bitmaperror2);
        }

        if(filefoto3 == null)
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmaperror3.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data = stream.toByteArray();
            filefoto3 = new ParseFile("fotorestaurantetres.jpg",data);
            filefoto3.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            imageviewfoto3.setImageBitmap(bitmaperror3);
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
        restauranteA.put("color",color);
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




    private void displayPopupFotos1(final View anchorView) {
        final PopupWindow popup = new PopupWindow(agregarrestaurante_delacalleactivity.this);
        LayoutInflater inflater = (LayoutInflater) agregarrestaurante_delacalleactivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    private void displayPopupFotos2(final View anchorView) {
        final PopupWindow popup = new PopupWindow(agregarrestaurante_delacalleactivity.this);
        LayoutInflater inflater = (LayoutInflater) agregarrestaurante_delacalleactivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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


    private void displayPopupFotos3(final View anchorView) {
        final PopupWindow popup = new PopupWindow(agregarrestaurante_delacalleactivity.this);
        LayoutInflater inflater = (LayoutInflater) agregarrestaurante_delacalleactivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popupfotos, null);
        popup.setContentView(layout);


        relativealbum = (RelativeLayout) layout.findViewById(R.id.relativelayoutAlbum);
        relativefoto = (RelativeLayout) layout.findViewById(R.id.relativelayoutTomarFoto);
        relativealbum.setClickable(true);
        relativefoto.setClickable(true);


        relativealbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectpic3();


            }
        });

        relativefoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takephoto3();
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



    private void displayPopupPaleta(final View anchorView) {
        final PopupWindow popup = new PopupWindow(agregarrestaurante_delacalleactivity.this);
        LayoutInflater inflater = (LayoutInflater) agregarrestaurante_delacalleactivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popuppaleta, null);
        popup.setContentView(layout);


        buttonColor1 = (Button) layout.findViewById(R.id.btnColor1);
        buttonColor2 = (Button) layout.findViewById(R.id.btnColor2);
        buttonColor3 = (Button) layout.findViewById(R.id.btnColor3);
        buttonColor4 = (Button) layout.findViewById(R.id.btnColor4);
        buttonColor5 = (Button) layout.findViewById(R.id.btnColor5);
        buttonColor6 = (Button) layout.findViewById(R.id.btnColor6);





        buttonColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativepaleta.setBackgroundColor(Color.parseColor("#b56497"));
                color = "#b56497";
            }
        });

        buttonColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativepaleta.setBackgroundColor(Color.parseColor("#169c79"));
                color = "#169c79";
            }
        });

        buttonColor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativepaleta.setBackgroundColor(Color.parseColor("#f05543"));
                color = "#f05543";
            }
        });

        buttonColor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativepaleta.setBackgroundColor(Color.parseColor("#da4f70"));
                color = "#da4f70";
            }
        });

        buttonColor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativepaleta.setBackgroundColor(Color.parseColor("#41b7ab"));
                color = "#41b7ab";
            }
        });

        buttonColor6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativepaleta.setBackgroundColor(Color.parseColor("#f0bf59"));
                color = "#f0bf59";
            }
        });








        // Set content width and height
        popup.setHeight(600);
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

    // Ocultar el teclado
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }


}