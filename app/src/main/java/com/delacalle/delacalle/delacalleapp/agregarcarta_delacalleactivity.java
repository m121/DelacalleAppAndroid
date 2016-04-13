package com.delacalle.delacalle.delacalleapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class agregarcarta_delacalleactivity extends AppCompatActivity {

    private Toolbar mToolbar;

    String id;

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

    private String nombreplato2C;
    private String descripcionplato2C;
    private String precioplato2C;

    private String nombreplato3C;
    private String descripcionplato3C;
    private String precioplato3C;

    private String nombreplato4C;
    private String descripcionplato4C;
    private String precioplato4C;

    private String nombreplato5C;
    private String descripcionplato5C;
    private String precioplato5C;

    private String nombreplato6C;
    private String descripcionplato6C;
    private String precioplato6C;

    Bitmap pic;
    Bitmap pic2;
    Bitmap pic3;
    Bitmap pic4;
    Bitmap pic5;
    Bitmap pic6;

    Bitmap bitmaperror2;
    Bitmap bitmaperror3;
    Bitmap bitmaperror4;
    Bitmap bitmaperror5;
    Bitmap bitmaperror6;

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
    public static final int IMAGEREQUESTCODE4 = 45538;
    public static final int IMAGEREQUESTCODE5 = 45539;
    public static final int IMAGEREQUESTCODE6 = 45540;

    Intent galleryIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregarcarta_delacalleactivity);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");

        }
        else
        {
            Log.d("delacalle","Error al pasar el id " + id);
        }


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

        bitmaperror2 = BitmapFactory.decodeResource(getResources(), R.drawable.agregar_foto);
        bitmaperror3 = BitmapFactory.decodeResource(getResources(), R.drawable.agregar_foto);
        bitmaperror4 = BitmapFactory.decodeResource(getResources(), R.drawable.agregar_foto);
        bitmaperror5 = BitmapFactory.decodeResource(getResources(), R.drawable.agregar_foto);
        bitmaperror6 = BitmapFactory.decodeResource(getResources(), R.drawable.agregar_foto);

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

        fotocartaplato2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopupFotoPlato2(v);
            }
        });

        fotocartaplato3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopupFotoPlato3(v);
            }
        });

        fotocartaplato4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopupFotoPlato4(v);
            }
        });

        fotocartaplato5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopupFotoPlato5(v);
            }
        });

        fotocartaplato6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopupFotoPlato6(v);
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

        nombreplato2C = nombreplato2.getText().toString();
        descripcionplato2C = descripcionplato2.getText().toString();
        precioplato2C = precioplato2.getText().toString();

        nombreplato3C = nombreplato3.getText().toString();
        descripcionplato3C = descripcionplato3.getText().toString();
        precioplato3C = precioplato3.getText().toString();

        nombreplato4C = nombreplato4.getText().toString();
        descripcionplato4C = descripcionplato4.getText().toString();
        precioplato4C = precioplato4.getText().toString();

        nombreplato5C = nombreplato5.getText().toString();
        descripcionplato5C = descripcionplato5.getText().toString();
        precioplato5C = precioplato5.getText().toString();

        nombreplato6C = nombreplato6.getText().toString();
        descripcionplato6C = descripcionplato6.getText().toString();
        precioplato6C = precioplato6.getText().toString();


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



        if(fotocartafile1 == null)
        {
            Toast.makeText(getApplicationContext(), "Es necesario guardar una foto del primer plato", Toast.LENGTH_SHORT).show();
            focusView = nombreplato1;
            cancel = true;
        }

        if(fotocartafile2 == null)
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmaperror2.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data = stream.toByteArray();
            fotocartafile2 = new ParseFile("fotocartaplato2.jpg",data);
            fotocartafile2.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            fotocartaplato2.setImageBitmap(bitmaperror2);
        }

        if(fotocartafile3 == null)
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmaperror3.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data = stream.toByteArray();
            fotocartafile3 = new ParseFile("fotocartaplato3.jpg",data);
            fotocartafile3.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            fotocartaplato3.setImageBitmap(bitmaperror3);
        }

        if(fotocartafile4 == null)
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmaperror4.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data = stream.toByteArray();
            fotocartafile4 = new ParseFile("fotocartaplato4.jpg",data);
            fotocartafile4.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            fotocartaplato4.setImageBitmap(bitmaperror4);
        }

        if(fotocartafile5 == null)
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmaperror5.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data = stream.toByteArray();
            fotocartafile5 = new ParseFile("fotocartaplato5.jpg",data);
            fotocartafile5.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            fotocartaplato5.setImageBitmap(bitmaperror5);
        }

        if(fotocartafile6 == null)
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmaperror6.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data = stream.toByteArray();
            fotocartafile6 = new ParseFile("fotocartaplato6.jpg",data);
            fotocartafile6.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            fotocartaplato6.setImageBitmap(bitmaperror6);
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

        nombreplato2.setError(null);
        descripcionplato2.setError(null);
        precioplato2.setError(null);

        nombreplato3.setError(null);
        descripcionplato3.setError(null);
        precioplato3.setError(null);

        nombreplato4.setError(null);
        descripcionplato4.setError(null);
        precioplato4.setError(null);

        nombreplato5.setError(null);
        descripcionplato5.setError(null);
        precioplato5.setError(null);

        nombreplato6.setError(null);
        descripcionplato6.setError(null);
        precioplato6.setError(null);


    }

    public void agregarCarta()
    {



        ParseACL acl = new ParseACL();
        acl.setPublicWriteAccess(true);
        acl.setPublicReadAccess(true);


        ParseObject carta1 = new ParseObject("carta");
        carta1.put("nombre", nombreplato1C);
        carta1.put("descripcion", descripcionplato1C);
        carta1.put("precio", precioplato1C);
        carta1.put("fotoplato", fotocartafile1);
        carta1.put("restauranteId", id);
        carta1.setACL(acl);
        carta1.saveInBackground();

        if(TextUtils.isEmpty(nombreplato2C))
        {
            Log.d("delacalle","nombre plato vacio");
        }
        else
        {
            ParseObject carta2 = new ParseObject("carta");
            carta2.put("nombre", nombreplato2C);
            carta2.put("descripcion", descripcionplato2C);
            carta2.put("precio", precioplato2C);
            carta2.put("fotoplato", fotocartafile2);
            carta2.put("restauranteId", id);
            carta2.setACL(acl);
            carta2.saveInBackground();
        }

        if(TextUtils.isEmpty(nombreplato3C)) {
            Log.d("delacalle","nombre plato vacio");
        }
        else
        {
            ParseObject carta3 = new ParseObject("carta");
            carta3.put("nombre", nombreplato3C);
            carta3.put("descripcion", descripcionplato3C);
            carta3.put("precio", precioplato3C);
            carta3.put("fotoplato", fotocartafile3);
            carta3.put("restauranteId", id);
            carta3.setACL(acl);
            carta3.saveInBackground();
        }
        if(TextUtils.isEmpty(nombreplato4C)) {
            Log.d("delacalle","nombre plato vacio");
        }
        else
        {
            ParseObject carta4 = new ParseObject("carta");
            carta4.put("nombre", nombreplato4C);
            carta4.put("descripcion", descripcionplato4C);
            carta4.put("precio", precioplato4C);
            carta4.put("fotoplato", fotocartafile4);
            carta4.put("restauranteId", id);
            carta4.setACL(acl);
            carta4.saveInBackground();
        }
        if(TextUtils.isEmpty(nombreplato5C)) {
            Log.d("delacalle","nombre plato vacio");
        }
        else
        {
            ParseObject carta5 = new ParseObject("carta");
            carta5.put("nombre", nombreplato5C);
            carta5.put("descripcion", descripcionplato5C);
            carta5.put("precio", precioplato5C);
            carta5.put("fotoplato", fotocartafile5);
            carta5.put("restauranteId", id);
            carta5.setACL(acl);
            carta5.saveInBackground();
        }
        if(TextUtils.isEmpty(nombreplato6C)) {
            Log.d("delacalle","nombre plato vacio");
        }
        else
        {
            ParseObject carta6 = new ParseObject("carta");
            carta6.put("nombre", nombreplato6C);
            carta6.put("descripcion", descripcionplato6C);
            carta6.put("precio", precioplato6C);
            carta6.put("fotoplato", fotocartafile6);
            carta6.put("restauranteId", id);
            carta6.setACL(acl);
            carta6.saveInBackground();
        }


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
                popup.dismiss();
            }
        });

        relativefoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takephoto1();
                popup.dismiss();
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
                popup.dismiss();
            }
        });

        relativefoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takephoto2();
                popup.dismiss();
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

    private void displayPopupFotoPlato3(final View anchorView) {
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
                selectpic3();
                popup.dismiss();
            }
        });

        relativefoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takephoto3();
                popup.dismiss();
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

    private void displayPopupFotoPlato4(final View anchorView) {
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
                selectpic4();
                popup.dismiss();
            }
        });

        relativefoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takephoto4();
                popup.dismiss();
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



    private void displayPopupFotoPlato5(final View anchorView) {
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
                selectpic5();
                popup.dismiss();
            }
        });

        relativefoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takephoto5();
                popup.dismiss();
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

    private void displayPopupFotoPlato6(final View anchorView) {
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
                selectpic6();
                popup.dismiss();
            }
        });

        relativefoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takephoto6();
                popup.dismiss();
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

    public void takephoto3()
    {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 3);
    }

    public void takephoto4()
    {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 4);
    }
    public void takephoto5()
    {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 5);
    }
    public void takephoto6()
    {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 6);
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

    public void selectpic4()
    {

        //select pic from gallery
        startActivityForResult(galleryIntent, IMAGEREQUESTCODE4);
    }

    public void selectpic5()
    {

        //select pic from gallery
        startActivityForResult(galleryIntent, IMAGEREQUESTCODE5);
    }

    public void selectpic6()
    {

        //select pic from gallery
        startActivityForResult(galleryIntent, IMAGEREQUESTCODE6);
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


            fotocartaplato1.setImageBitmap(pic);

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


            fotocartaplato2.setImageBitmap(pic2);

        }

        if (requestCode == 3 && resultCode == RESULT_OK) {
            Bundle extras = i.getExtras();
            pic3 = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic3.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data1 = stream.toByteArray();
            fotocartafile3 = new ParseFile("fotocartaplato3.jpg", data1);
            fotocartafile3.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });


            fotocartaplato3.setImageBitmap(pic3);

        }

        if (requestCode == 4 && resultCode == RESULT_OK) {
            Bundle extras = i.getExtras();
            pic4 = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic4.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data1 = stream.toByteArray();
            fotocartafile4 = new ParseFile("fotocartaplato4.jpg", data1);
            fotocartafile4.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });


            fotocartaplato4.setImageBitmap(pic4);

        }

        if (requestCode == 5 && resultCode == RESULT_OK) {
            Bundle extras = i.getExtras();
            pic5 = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic5.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data1 = stream.toByteArray();
            fotocartafile5 = new ParseFile("fotocartaplato5.jpg", data1);
            fotocartafile5.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });


            fotocartaplato5.setImageBitmap(pic5);

        }

        if (requestCode == 6 && resultCode == RESULT_OK) {
            Bundle extras = i.getExtras();
            pic6 = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic6.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data1 = stream.toByteArray();
            fotocartafile6 = new ParseFile("fotocartaplato6.jpg", data1);
            fotocartafile6.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });


            fotocartaplato6.setImageBitmap(pic6);

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

        if(requestCode == IMAGEREQUESTCODE4 && resultCode == RESULT_OK)
        {

            manageImageFromUri4(i.getData());

        }
        else
        {
            Log.d("DeLaCalle", "Error en seleccionar la foto");
        }

        if(requestCode == IMAGEREQUESTCODE5 && resultCode == RESULT_OK)
        {

            manageImageFromUri5(i.getData());

        }
        else
        {
            Log.d("DeLaCalle", "Error en seleccionar la foto");
        }

        if(requestCode == IMAGEREQUESTCODE6 && resultCode == RESULT_OK)
        {

            manageImageFromUri6(i.getData());

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
            fotocartaplato1.setImageBitmap(pic);


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
            fotocartaplato2.setImageBitmap(pic2);


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
            fotocartafile3 = new ParseFile("fotocartaplato3.jpg",data);
            fotocartafile3.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            fotocartaplato3.setImageBitmap(pic3);


        }
        else
        {
            Log.d("Delacalle", "Error bitmap  null");
        }
    }

    private void manageImageFromUri4(Uri imageUri) {
        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(), imageUri);

        } catch (Exception e) {
            // Manage exception ...

        }

        if (bitmap != null) {
            // Here you can use bitmap in your application ...
            pic4 = bitmap;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic4.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data = stream.toByteArray();
            fotocartafile4 = new ParseFile("fotocartaplato4.jpg",data);
            fotocartafile4.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            fotocartaplato4.setImageBitmap(pic4);


        }
        else
        {
            Log.d("Delacalle", "Error bitmap  null");
        }
    }

    private void manageImageFromUri5(Uri imageUri) {
        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(), imageUri);

        } catch (Exception e) {
            // Manage exception ...

        }

        if (bitmap != null) {
            // Here you can use bitmap in your application ...
            pic5 = bitmap;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic5.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data = stream.toByteArray();
            fotocartafile5 = new ParseFile("fotocartaplato5.jpg",data);
            fotocartafile5.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            fotocartaplato5.setImageBitmap(pic5);


        }
        else
        {
            Log.d("Delacalle", "Error bitmap  null");
        }
    }

    private void manageImageFromUri6(Uri imageUri) {
        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(), imageUri);

        } catch (Exception e) {
            // Manage exception ...

        }

        if (bitmap != null) {
            // Here you can use bitmap in your application ...
            pic6 = bitmap;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic6.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data = stream.toByteArray();
            fotocartafile6 = new ParseFile("fotocartaplato6.jpg",data);
            fotocartafile6.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            fotocartaplato6.setImageBitmap(pic6);


        }
        else
        {
            Log.d("Delacalle", "Error bitmap  null");
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        // or call onBackPressed()
        return true;
    }
}
