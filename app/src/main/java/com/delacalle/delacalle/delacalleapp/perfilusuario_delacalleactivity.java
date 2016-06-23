package com.delacalle.delacalle.delacalleapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class perfilusuario_delacalleactivity extends AppCompatActivity {

    private Toolbar mToolbar;


    TextView tcorreo;
    TextView tedad;
    TextView tciudad;
    TextView tusername;

    TextView nombre;
    TextView correo;
    TextView edad;
    TextView ciudad;
    TextView username;
    ImageView fotousuario;

    Bitmap pic;

    ParseFile filefoto;

    Intent galleryIntent;

    public static final int IMAGEREQUESTCODE = 45535;

    // Popup fotos
    RelativeLayout relativealbum;
    RelativeLayout relativefoto;


    boolean isInternetPresent = false;
    ConnectionDetector cd;

   /* Button btncerrarsesion;
    Button btncambiarcontrasena;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfilusuario_delacalleactivity);

        cd = new ConnectionDetector(getApplicationContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        Typeface primerfontcandara = Typeface.createFromAsset(getAssets(),"fonts/CandaraBold.ttf");
        Typeface segundafontcaviar = Typeface.createFromAsset(getAssets(),"fonts/CaviarDreams.ttf");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tedad = (TextView) findViewById(R.id.textViewTituloEdadUsuario);
        tciudad = (TextView) findViewById(R.id.textViewTituloCiudadUsuario);
        tcorreo = (TextView) findViewById(R.id.textViewTituloCorreoUsuario);

   //     nombre = (TextView) findViewById(R.id.textViewNombreUsuario);
        correo = (TextView) findViewById(R.id.textViewCorreoUsuario);
        edad = (TextView) findViewById(R.id.textViewEdadUsuario);
        ciudad = (TextView) findViewById(R.id.textViewCiudadUsuario);
        username = (TextView) findViewById(R.id.textViewUsernameUsuario);
        fotousuario = (ImageView) findViewById(R.id.imageViewFotoPerfil);
        tedad.setTypeface(primerfontcandara);
        tciudad.setTypeface(primerfontcandara);
        tcorreo.setTypeface(primerfontcandara);
        correo.setTypeface(segundafontcaviar);
        edad.setTypeface(segundafontcaviar);
        ciudad.setTypeface(segundafontcaviar);
        username.setTypeface(primerfontcandara);


        fotousuario.setClickable(true);

        galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);


        fotousuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopupFotoPlato1(v);
            }
        });



       /* btncerrarsesion = (Button) findViewById(R.id.btncerrarsesion);
        btncambiarcontrasena = (Button) findViewById(R.id.btncambiarcontrasena);*/

        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
      //  frameLayout.getBackground().setAlpha(0);
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.fabmenu);
        final FloatingActionButton fabeditar = (FloatingActionButton) findViewById(R.id.fabeditar);
        final FloatingActionButton  fabrestaurante = (FloatingActionButton) findViewById(R.id.fabagregar);
        final FloatingActionButton  fabperfil = (FloatingActionButton) findViewById(R.id.fabperfil);

        try {
            fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
                @Override
                public void onMenuExpanded() {
                    //          frameLayout.getBackground().setAlpha(240);
                    frameLayout.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            fabMenu.collapse();
                            return true;
                        }
                    });
                }

                @Override
                public void onMenuCollapsed() {
                    //         frameLayout.getBackground().setAlpha(0);
                    frameLayout.setOnTouchListener(null);
                }
            });

            fabeditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(perfilusuario_delacalleactivity.this, listarestaurantesresponsable_delacalleactivity.class);
                    startActivity(intent);
                }
            });

            fabrestaurante.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(perfilusuario_delacalleactivity.this, agregarrestaurante_delacalleactivity.class);
                    startActivity(intent);
                }
            });

            fabperfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(perfilusuario_delacalleactivity.this, perfilusuario_delacalleactivity.class);
                    startActivity(intent);
                }
            });

        }catch (Exception e)
        {
            Log.d("delacalle","error " + e);
        }
        try {

if(isInternetPresent) {
    ParseQuery<ParseRole> roleuserusuario = ParseRole.getQuery();
    roleuserusuario.whereEqualTo("name", "usuario");
    roleuserusuario.whereEqualTo("users", ParseUser.getCurrentUser().getObjectId());
    roleuserusuario.getFirstInBackground(new GetCallback<ParseRole>() {
        @Override
        public void done(ParseRole object, ParseException e) {
            if (e == null) {
                fabeditar.setVisibility(View.INVISIBLE);
                fabrestaurante.setVisibility(View.INVISIBLE);
            } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                ParseQuery<ParseRole> roleuserresponsable = ParseRole.getQuery();
                roleuserresponsable.whereEqualTo("name", "responsable");
                roleuserresponsable.whereEqualTo("users", ParseUser.getCurrentUser().getObjectId());
                roleuserresponsable.getFirstInBackground(new GetCallback<ParseRole>() {
                    @Override
                    public void done(ParseRole object, ParseException e) {
                        if (e == null) {
                            fabeditar.setVisibility(View.VISIBLE);
                            fabrestaurante.setVisibility(View.VISIBLE);
                        } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                            Log.d("delacalle", "El usuario no tiene rol");
                            fabeditar.setVisibility(View.INVISIBLE);
                            fabrestaurante.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }

        }
    });
}
            else
{
    fabeditar.setVisibility(View.INVISIBLE);
    fabrestaurante.setVisibility(View.INVISIBLE);
}

        }catch (java.lang.NullPointerException e)
        {
            e.getStackTrace();
            Log.d("delacalle","Error mostrando boton plus");
        }

try {
    username.setText(ParseUser.getCurrentUser().getString("nombre"));
    correo.setText(ParseUser.getCurrentUser().getEmail());
    edad.setText(ParseUser.getCurrentUser().getString("edad"));
    ciudad.setText(ParseUser.getCurrentUser().getString("ciudad"));
  //  nombre.setText(ParseUser.getCurrentUser().getString("nombre"));
    filefoto = ParseUser.getCurrentUser().getParseFile("fotousuario");
    if(filefoto != null) {
        filefoto.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap  pic = BitmapFactory.decodeByteArray(data, 0, data.length,options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                pic.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                fotousuario.setImageBitmap(pic);
            }
        });
        Log.d("delacalle","foto mostrada");
    }
    Log.d("delacalle","informacion mostrada");
}catch (Exception ae)
{
    Log.d("delacalle","No se puede mostrar la informacion del usuario");
    Toast.makeText(getApplicationContext(), "No se puede mostrar la informacion del usuario", Toast.LENGTH_SHORT).show();

}

        //Google analytics reportando la actividad
    //    GoogleAnalytics.getInstance(this).newTracker("Perfil usuario");
      //  AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.valueOf("Perfil usuario"));
       // AnalyticsTrackers.initialize(this);

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        Tracker tracker = analytics.newTracker("UA-77841203-3");
        tracker.setScreenName("perfilusuario");
        tracker.enableAutoActivityTracking(true);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfilusuario_delacalleactivity, menu);
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

        if(id == R.id.action_cambiar)
        {
            Intent intent = new Intent(perfilusuario_delacalleactivity.this,resetearcontrasena_delacalleactivity.class);
            startActivity(intent);
        }

        if(id == R.id.action_editar)
        {

try {
    ProgressDialog.show(perfilusuario_delacalleactivity.this, "Guardando", "Espera mientras se actualiza tu información",true,true);
    ParseQuery<ParseUser> userquery = ParseUser.getQuery();
    userquery.whereEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
    userquery.getFirstInBackground(new GetCallback<ParseUser>() {
        @Override
        public void done(ParseUser usuario, ParseException e) {
            if (e == null) {
                if (filefoto != null)
                {
                    usuario.put("fotousuario", filefoto);
                }


            //    usuario.put("nombre", nombre.getText().toString());
                usuario.put("edad", edad.getText().toString());
                usuario.put("ciudad", ciudad.getText().toString());
                usuario.saveInBackground();
                Log.d("delacalle", "Usuario actualizado");
                Toast.makeText(getApplicationContext(), "Perfil actualizado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(perfilusuario_delacalleactivity.this, menu_pestanas_delacalleactivity.class);
                startActivity(intent);
            } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                Log.d("delacalle", "Usuario no encontrado");
            }
        }
    });

    GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
    Tracker trackeredad = analytics.newTracker("UA-77841203-3");
    Tracker trackerciudad = analytics.newTracker("UA-77841203-3");
    trackeredad.send(new HitBuilders.EventBuilder()
            .setCategory("datosusuario")
            .setAction("guardar datos usuarios")
            .setLabel(edad.getText().toString())
            .setValue(1)
            .build());


    trackerciudad.send(new HitBuilders.EventBuilder()
            .setCategory("datosusuario")
            .setAction("guardar datos usuarios")
            .setLabel(ciudad.getText().toString())
            .setValue(1)
            .build());


            }catch (Exception e)
             {
                 Log.d("delacalle","error, no se puede guardar sin datos ");
                 e.printStackTrace();
                    Intent intent = new Intent(perfilusuario_delacalleactivity.this,menu_pestanas_delacalleactivity.class);
                        startActivity(intent);
            }
        }

        if(id == R.id.action_cerrar)
        {
            try {
                // detener google analytics
                GoogleAnalytics.getInstance(this).reportActivityStop(this);
                ParseUser.logOut();
                Log.d("delacalle", "usuario cierra sesion");
                Toast.makeText(getApplicationContext(), "Cerrando sesión", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(perfilusuario_delacalleactivity.this, iniciosesion_delacalleactivity.class);
                startActivity(intent);
            }catch (Exception e)
            {
                Log.d("delacalle","error " + e);
                Intent intent = new Intent(perfilusuario_delacalleactivity.this, iniciosesion_delacalleactivity.class);
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

    private void displayPopupFotoPlato1(final View anchorView) {
        final PopupWindow popup = new PopupWindow(perfilusuario_delacalleactivity.this);
        LayoutInflater inflater = (LayoutInflater) perfilusuario_delacalleactivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public void takephoto1()
    {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 1);
    }

    public void selectpic()
    {

        //select pic from gallery
        startActivityForResult(galleryIntent, IMAGEREQUESTCODE);
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
            filefoto = new ParseFile("fotousuario.jpg", data1);
            filefoto.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });


            fotousuario.setImageBitmap(pic);

        }



        if(requestCode == IMAGEREQUESTCODE && resultCode == RESULT_OK)
        {

            manageImageFromUri1(i.getData());

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
            filefoto = new ParseFile("fotousuario.jpg",data);
            filefoto.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            fotousuario.setImageBitmap(pic);


        }
        else
        {
            Log.d("Delacalle", "Error bitmap  null");
        }
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
