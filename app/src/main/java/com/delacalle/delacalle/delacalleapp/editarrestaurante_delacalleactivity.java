package com.delacalle.delacalle.delacalleapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class editarrestaurante_delacalleactivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener  {

    private Toolbar mToolbar;
    Bitmap pic;
    Bitmap pic2;
    Bitmap pic3;


    private ImageView fotologoRestauranteA;
    private ImageView fotograndeRestauranteA;
    private TextView  nombreRestauranteA;
    private TextView  descripcionRestauranteA;
    private TextView  promocionRestauranteA;
    private TextView  direccionRestauranteA;
    private TextView  telefonoRestauranteA;
    private TextView  webRestauranteA;
    private TextView  horarioatencion;
    private Spinner spinnercategoria;
    private Spinner spinnercategoriados;
    private Spinner spinnerdomicilio;
    private Spinner spinnereventos;





    ParseFile picfile1;
    ParseFile picfile2;
    ParseFile picfile3;
    String id;
    private String categoriaR;
    private String domicilioR;
    private String categoria2R;
    private String eventosR;
    private String promocionR;
    private String abiertoR;

    Button btneditar;
    private Button btneliminarrestaurante;

    //popup paleta
    private Button buttonColor1;
    private Button buttonColor2;
    private Button buttonColor3;
    private Button buttonColor4;
    private Button buttonColor5;
    private Button buttonColor6;
    private LinearLayout relativepaleta;
    String color;
    ImageView btnactualizarpaleta;



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
        setContentView(R.layout.activity_editarrestaurante_delacalleactivity);

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        Tracker tracker = analytics.newTracker("UA-77841203-3");
        tracker.setScreenName("editarrestaurante");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        final Typeface primerfontcandara = Typeface.createFromAsset(getAssets(), "fonts/CandaraBold.ttf");
        final Typeface segundafontcaviar = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        fotologoRestauranteA = (ImageView) findViewById(R.id.imageViewfotoLogoA);
         fotograndeRestauranteA = (ImageView) findViewById(R.id.imageViewfotoRestauranteA);
        nombreRestauranteA = (TextView) findViewById(R.id.editTextNombreRestauranteA);
        descripcionRestauranteA = (TextView) findViewById(R.id.editTextDescripcionRestauranteA);
        promocionRestauranteA = (TextView) findViewById(R.id.editTextPromocionRestauranteA);
        direccionRestauranteA = (TextView) findViewById(R.id.editTextDireccionRestauranteA);
        telefonoRestauranteA = (TextView) findViewById(R.id.editTextTelefonoRestauranteA);
        webRestauranteA = (TextView) findViewById(R.id.editTextWebRestauranteA);
        horarioatencion = (TextView) findViewById(R.id.editTextAbiertoRestauranteA);
   //     btneliminarrestaurante = (Button) findViewById(R.id.btnEliminarRestaurante);
   //     btneditar  = (Button) findViewById(R.id.btnActualizarRestaurante);
   //     btnactualizarpaleta = (ImageView) findViewById(R.id.imageViewbtnPaletaRestaurante);
        relativepaleta = (LinearLayout) findViewById(R.id.relativelayoutPaletacambiar);
        spinnercategoria = (Spinner) findViewById(R.id.spinnercategoria);
        spinnercategoriados = (Spinner) findViewById(R.id.spinnercategoria2);
        spinnereventos = (Spinner) findViewById(R.id.spinnereventos);
        spinnerdomicilio = (Spinner) findViewById(R.id.spinnerdomicilio);
        fotologoRestauranteA.setClickable(true);
        fotograndeRestauranteA.setClickable(true);
        nombreRestauranteA.setTypeface(segundafontcaviar);
        descripcionRestauranteA.setTypeface(segundafontcaviar);
        promocionRestauranteA.setTypeface(segundafontcaviar);
        direccionRestauranteA.setTypeface(segundafontcaviar);
        telefonoRestauranteA.setTypeface(segundafontcaviar);
        webRestauranteA.setTypeface(segundafontcaviar);
        horarioatencion.setTypeface(segundafontcaviar);
  //      btneliminarrestaurante.setTypeface(primerfontcandara);
   //     btneditar.setTypeface(primerfontcandara);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categoriasCrear, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnercategoria.setAdapter(adapter);
        spinnercategoria.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterdomicilio = ArrayAdapter.createFromResource(this,
                R.array.domiciliosCrear, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerdomicilio.setAdapter(adapterdomicilio);
        spinnerdomicilio.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adaptercategoria2 = ArrayAdapter.createFromResource(this,
                R.array.categoriasCrear, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


       spinnercategoriados.setAdapter(adaptercategoria2);
        spinnercategoriados.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> adaptereventos = ArrayAdapter.createFromResource(this,
                R.array.eventosCrear, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnereventos.setAdapter(adaptereventos);
        spinnereventos.setOnItemSelectedListener(this);


        galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);


   /*     final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        //      frameLayout.getBackground().setAlpha(0);
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.fabmenu);
        final FloatingActionButton fabeditar = (FloatingActionButton) findViewById(R.id.fabeditar);
        final FloatingActionButton  fabrestaurante = (FloatingActionButton) findViewById(R.id.fabagregar);
        final FloatingActionButton  fabperfil = (FloatingActionButton) findViewById(R.id.fabperfil);

        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                //        frameLayout.getBackground().setAlpha(240);
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
                //          frameLayout.getBackground().setAlpha(0);
                frameLayout.setOnTouchListener(null);
            }
        });

        fabeditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editarrestaurante_delacalleactivity.this, listarestaurantesresponsable_delacalleactivity.class);
                startActivity(intent);
            }
        });

        fabrestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editarrestaurante_delacalleactivity.this, agregarrestaurante_delacalleactivity.class);
                startActivity(intent);
            }
        });

        fabperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editarrestaurante_delacalleactivity.this, perfilusuario_delacalleactivity.class);
                startActivity(intent);
            }
        });

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
                            }
                        }
                    });
                }

            }
        });*/

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
             id = bundle.getString("id");

        }
    /*    btneliminarrestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseRole> queryrol = ParseRole.getQuery();
                queryrol.whereEqualTo("name", "responsable");
                queryrol.whereEqualTo("users", ParseUser.getCurrentUser().getObjectId());
                queryrol.getFirstInBackground(new GetCallback<ParseRole>() {
                    @Override
                    public void done(ParseRole rol, ParseException e) {
                        if (e == null) {
                            try
                            {
                                ParseQuery<ParseObject> queryeliminar = ParseQuery.getQuery("restaurante");
                                queryeliminar.whereEqualTo("objectId", id);
                                queryeliminar.getFirstInBackground(new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject eliminar, ParseException e) {
                                        if (e == null) {
                                            ProgressDialog.show(editarrestaurante_delacalleactivity.this, "Eliminando", "Espera mientras elimina el restaurante",true,true);
                                            eliminar.deleteInBackground(new DeleteCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    if (e == null) {
                                                        Toast.makeText(getApplicationContext(), "Restaurante eliminado", Toast.LENGTH_SHORT).show();
                                                        Log.d("delacalle", "Restaurante eliminado");
                                                    } else {
                                                        Log.d("delacalle", "No se puede eliminar el restaurante");
                                                    }
                                                }
                                            });

                                            ParseQuery<ParseObject> cartaeliminarquery = ParseQuery.getQuery("carta");
                                            cartaeliminarquery.whereEqualTo("restauranteId", id);
                                            final List<ParseObject> cartalist;

                                            try {
                                                cartalist = cartaeliminarquery.find();

                                                for (final ParseObject cartas : cartalist) {
                                                    cartas.deleteInBackground(new DeleteCallback() {
                                                        @Override
                                                        public void done(ParseException e) {
                                                            if (e == null) {
                                                                Log.d("delacalle", "Carta eliminada");
                                                            } else {
                                                                Log.d("delacalle", "Carta no eliminada");
                                                            }
                                                        }
                                                    });
                                                }
                                            } catch (ParseException ae) {
                                                Log.d("delacalle", "Error" + ae);
                                            }
                                            Intent intent = new Intent(editarrestaurante_delacalleactivity.this, menu_pestanas_delacalleactivity.class);
                                            startActivity(intent);
                                        } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                            Log.d("delacalle", "Restaurante no encontrado, no se puede eliminar");
                                        }


                                    }
                                });

                            }catch(Exception oe)
                            {
                                oe.getStackTrace();
                                Log.d("delacalle", "error en eliminar restaurante");
                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "No puedes  eliminar un restaurante si no eres un responsable", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        });*/


       /* btnactualizarpaleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopupPaleta(v);
            }
        });*/

        try
        {
        ParseQuery<ParseObject> querymostrareditar = ParseQuery.getQuery("restaurante");
        querymostrareditar.whereEqualTo("objectId", id);
        querymostrareditar.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                if (e == null) {
                    relativepaleta.setBackgroundColor(Color.parseColor(object.getString("color")));
                    color = object.getString("color");
                    nombreRestauranteA.setText(object.getString("nombre"));
                    descripcionRestauranteA.setText(object.getString("descripcion"));
                    promocionRestauranteA.setText(object.getString("promo"));
                    direccionRestauranteA.setText(object.getString("direccion"));
                    telefonoRestauranteA.setText(object.getString("telefono"));
                    webRestauranteA.setText(object.getString("web"));
                    horarioatencion.setText(object.getString("horario"));

                    picfile1 = object.getParseFile("fotologo");
                    picfile1.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            final BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 2;
                            Bitmap  pic = BitmapFactory.decodeByteArray(data, 0, data.length,options);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            pic.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            fotologoRestauranteA.setImageBitmap(pic);
                        }
                    });

                    picfile2 = object.getParseFile("fotogrande");
                    picfile2.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            final BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 4;
                            Bitmap  pic2 = BitmapFactory.decodeByteArray(data, 0, data.length,options);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            pic2.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            fotograndeRestauranteA.setImageBitmap(pic2);
                        }
                    });


                    fotologoRestauranteA.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(animAlpha);
                            displayPopupFotos1(v);
                        }
                    });

                    fotograndeRestauranteA.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(animAlpha);
                            displayPopupFotos2(v);
                        }
                    });




//                    btneditar.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
                 //           v.startAnimation(animAlpha);
                //    if(editar == true) {
              /*          ParseQuery<ParseRole> queryrol = ParseRole.getQuery();
                        queryrol.whereEqualTo("name", "responsable");
                        queryrol.whereEqualTo("users", ParseUser.getCurrentUser().getObjectId());
                        queryrol.getFirstInBackground(new GetCallback<ParseRole>() {
                            @Override
                            public void done(ParseRole rol, ParseException e) {
                                if (e == null) {
                                    final String nombre = nombreRestauranteA.getText().toString();
                                    final String descripcion = descripcionRestauranteA.getText().toString();
                                    final String direccion = direccionRestauranteA.getText().toString();
                                    final String telefono = telefonoRestauranteA.getText().toString();
                                    final String web = webRestauranteA.getText().toString();


                                    object.put("nombre", nombre);
                                    object.put("descripcion", descripcion);
                                    object.put("direccion", direccion);
                                    object.put("telefono", telefono);
                                    object.put("web", web);
                                    object.put("fotologo", picfile1);
                                    object.put("fotogrande", picfile2);
                                    object.put("color", color);
                                    object.put("categoria", categoriaR);
                                    ProgressDialog.show(editarrestaurante_delacalleactivity.this, "Guardando", "Espera mientras actualiza el restaurante", true, true);
                                    object.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                id = object.getObjectId().toString();
                                                Intent intent = new Intent(editarrestaurante_delacalleactivity.this, editarcartarestaurante_delacalleactivity.class);
                                                intent.putExtra("id", id);
                                                editarrestaurante_delacalleactivity.this.startActivity(intent);
                                                Toast.makeText(getApplicationContext(), "Actualizado", Toast.LENGTH_SHORT).show();
                                                Log.d("delacalle", "Restaurante Actualizado");
                                            } else {
                                                Log.d("delacalle", "Error al actualizar restaurante");
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(getApplicationContext(), "No puedes editar el restaurante si no eres un responsable", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });*/


                        //        }
                        //      });
         //           }
                } else {
                    Toast.makeText(getApplicationContext(), "Error en mostrar", Toast.LENGTH_SHORT).show();
                }
            }


        });

        }catch(Exception e)
        {
            e.getStackTrace();
            Log.d("delacalle", "error en mostrar restaurante");
        }








    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editarrestaurante_delacalleactivity, menu);
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

        if (id == R.id.action_actualizar)
        {
            editarrestaurante();
        }

        if(id == R.id.action_color)
        {
            displayPopupPaleta();
        }

        if(id == R.id.action_borrar)
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Eliminar restaurante")
                    .setMessage("¿Estas seguro que deseas eliminar el restaurante?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            eliminarrestaurante();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

        }

        return super.onOptionsItemSelected(item);
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
            picfile1 = new ParseFile("fotorestaurantelogo.jpg", data1);
            picfile1.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });


            fotologoRestauranteA.setImageBitmap(pic);

        }

        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bundle extras = i.getExtras();
            pic2 = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic2.compress(Bitmap.CompressFormat.JPEG, 70, stream);

            byte[] data1 = stream.toByteArray();
            picfile2 = new ParseFile("fotorestaurantegrande.jpg", data1);
            picfile2.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });


            fotograndeRestauranteA.setImageBitmap(pic2);

        }

/*
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
*/

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

      /*  if(requestCode == IMAGEREQUESTCODE3 && resultCode == RESULT_OK)
        {

            manageImageFromUri3(i.getData());

        }
        else
        {
            Log.d("DeLaCalle", "Error en seleccionar la foto");
        }*/
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
            picfile1 = new ParseFile("fotologorestaurante.jpg",data);
            picfile1.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            fotologoRestauranteA.setImageBitmap(pic);


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
            picfile2 = new ParseFile("fotogranderestaurante.jpg",data);
            picfile2.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            fotograndeRestauranteA.setImageBitmap(pic2);


        }
        else
        {
            Log.d("Delacalle", "Error bitmap  null");
        }
    }

 /*   private void manageImageFromUri3(Uri imageUri) {
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
            picfile3 = new ParseFile("fotorestaurantetres.jpg",data);
            picfile3.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            picimageview3.setImageBitmap(Bitmap.createScaledBitmap(pic3, 400, 400, false));


        }
        else
        {
            Log.d("Delacalle", "Error bitmap  null");
        }
    }
*/

    private void displayPopupFotos1(final View anchorView) {

        try
        {
        final PopupWindow popup = new PopupWindow(editarrestaurante_delacalleactivity.this);
        LayoutInflater inflater = (LayoutInflater) editarrestaurante_delacalleactivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        }catch(Exception e)
        {
            e.getStackTrace();
            Log.d("delacalle", "error en mostrar popup fotos");
        }

    }

    private void displayPopupFotos2(final View anchorView) {
        try
        {
        final PopupWindow popup = new PopupWindow(editarrestaurante_delacalleactivity.this);
        LayoutInflater inflater = (LayoutInflater) editarrestaurante_delacalleactivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        }catch(Exception e)
        {
            e.getStackTrace();
            Log.d("delacalle", "error en mostrar popup fotos");
        }
    }




    private void displayPopupPaleta() {
        try {
            final PopupWindow popup = new PopupWindow(editarrestaurante_delacalleactivity.this);
            LayoutInflater inflater = (LayoutInflater) editarrestaurante_delacalleactivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            popup.showAtLocation(layout, Gravity.TOP | Gravity.START | Gravity.CENTER_VERTICAL, 120, 300);
            popup.showAsDropDown(layout);
            // Show anchored to button
            //   popup.setBackgroundDrawable(new BitmapDrawable());
            /*new Handler().postDelayed(new Runnable() {

                public void run() {

                }
            }, 100L);*/

        }catch(Exception e)
        {
            e.getStackTrace();
            Log.d("delacalle", "error en mostrar popup paleta colores");
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

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        // or call onBackPressed()
        return true;
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Spinner spiner = (Spinner) parent;
        if(spiner.getId() == R.id.spinnercategoria)
        {
            categoriaR = spinnercategoria.getItemAtPosition(pos).toString();
        }

        if(spiner.getId() == R.id.spinnerdomicilio)
        {
            domicilioR = spinnerdomicilio.getItemAtPosition(pos).toString();
        }

        if(spiner.getId() == R.id.spinnercategoria2)
        {
            categoria2R = spinnercategoriados.getItemAtPosition(pos).toString();
        }

        if (spiner.getId() == R.id.spinnereventos)
        {
            eventosR = spinnereventos.getItemAtPosition(pos).toString();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        View focusView = spinnercategoria;
        focusView.requestFocus();
        Toast.makeText(getApplicationContext(), "Selecciona una categoria por favor ", Toast.LENGTH_SHORT).show();
    }

    public void eliminarrestaurante()
    {
        ParseQuery<ParseRole> queryrol = ParseRole.getQuery();
        queryrol.whereEqualTo("name", "responsable");
        queryrol.whereEqualTo("users", ParseUser.getCurrentUser().getObjectId());
        queryrol.getFirstInBackground(new GetCallback<ParseRole>() {
            @Override
            public void done(ParseRole rol, ParseException e) {
                if (e == null) {
                    try
                    {
                        ParseQuery<ParseObject> queryeliminar = ParseQuery.getQuery("restaurante");
                        queryeliminar.whereEqualTo("objectId", id);
                        queryeliminar.getFirstInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject eliminar, ParseException e) {
                                if (e == null) {
                                    ProgressDialog.show(editarrestaurante_delacalleactivity.this, "Eliminando", "Espera mientras elimina el restaurante",true,true);
                                    eliminar.deleteInBackground(new DeleteCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                Toast.makeText(getApplicationContext(), "Restaurante eliminado", Toast.LENGTH_SHORT).show();
                                                Log.d("delacalle", "Restaurante eliminado");
                                            } else {
                                                Log.d("delacalle", "No se puede eliminar el restaurante");
                                            }
                                        }
                                    });

                                    ParseQuery<ParseObject> cartaeliminarquery = ParseQuery.getQuery("carta");
                                    cartaeliminarquery.whereEqualTo("restauranteId", id);
                                    final List<ParseObject> cartalist;

                                    try {
                                        cartalist = cartaeliminarquery.find();

                                        for (final ParseObject cartas : cartalist) {
                                            cartas.deleteInBackground(new DeleteCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    if (e == null) {
                                                        Log.d("delacalle", "Carta eliminada");
                                                    } else {
                                                        Log.d("delacalle", "Carta no eliminada");
                                                    }
                                                }
                                            });
                                        }
                                    } catch (ParseException ae) {
                                        Log.d("delacalle", "Error" + ae);
                                    }
                                    Intent intent = new Intent(editarrestaurante_delacalleactivity.this, menu_pestanas_delacalleactivity.class);
                                    startActivity(intent);
                                } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                    Log.d("delacalle", "Restaurante no encontrado, no se puede eliminar");
                                }


                            }
                        });

                    }catch(Exception oe)
                    {
                        oe.getStackTrace();
                        Log.d("delacalle", "error en eliminar restaurante");
                    }
                } else {

                    Toast.makeText(getApplicationContext(), "No puedes  eliminar un restaurante si no eres un responsable", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void editarrestaurante()
    {
        ParseQuery<ParseObject> editarrestaurante = ParseQuery.getQuery("restaurante");
        editarrestaurante.whereEqualTo("objectId", id);
        editarrestaurante.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                if(e == null)
                {
                    ParseQuery<ParseRole> queryrol = ParseRole.getQuery();
                    queryrol.whereEqualTo("name", "responsable");
                    queryrol.whereEqualTo("users", ParseUser.getCurrentUser().getObjectId());
                    queryrol.getFirstInBackground(new GetCallback<ParseRole>() {
                        @Override
                        public void done(ParseRole rol, ParseException e) {
                            if (e == null) {
                                final String nombre = nombreRestauranteA.getText().toString();
                                final String descripcion = descripcionRestauranteA.getText().toString();
                                final String direccion = direccionRestauranteA.getText().toString();
                                final String telefono = telefonoRestauranteA.getText().toString();
                                final String web = webRestauranteA.getText().toString();
                                final String promocion = promocionRestauranteA.getText().toString();
                                final String horario = horarioatencion.getText().toString();


                                object.put("nombre", nombre);
                                object.put("descripcion", descripcion);
                                object.put("promo",promocion);
                                object.put("direccion", direccion);
                                object.put("telefono", telefono);
                                object.put("web", web);
                                object.put("horario",horario);
                                object.put("fotologo", picfile1);
                                object.put("fotogrande", picfile2);
                                object.put("color", color);
                                object.put("categoria", categoriaR + "," + categoria2R);
                                object.put("domicilio",domicilioR);
                                object.put("eventos",eventosR);
                                ProgressDialog.show(editarrestaurante_delacalleactivity.this, "Guardando", "Espera mientras actualiza el restaurante", true, true);
                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            id = object.getObjectId().toString();
                                            Intent intent = new Intent(editarrestaurante_delacalleactivity.this, editarcartarestaurante_delacalleactivity.class);
                                            intent.putExtra("id", id);
                                            editarrestaurante_delacalleactivity.this.startActivity(intent);
                                            Toast.makeText(getApplicationContext(), "Actualizado", Toast.LENGTH_SHORT).show();
                                            Log.d("delacalle", "Restaurante Actualizado");
                                        } else {
                                            Log.d("delacalle", "Error al actualizar restaurante");
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "No puedes editar el restaurante si no eres un responsable", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No puedes editar el restaurante si no eres un responsable", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
