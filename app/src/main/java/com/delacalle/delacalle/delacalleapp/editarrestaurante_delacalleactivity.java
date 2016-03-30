package com.delacalle.delacalle.delacalleapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class editarrestaurante_delacalleactivity extends AppCompatActivity {

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

    //popup paleta
    private Button buttonColor1;
    private Button buttonColor2;
    private Button buttonColor3;
    private Button buttonColor4;
    private Button buttonColor5;
    private Button buttonColor6;
    private RelativeLayout relativepaleta;
    String color;
    Button btnactualizarpaleta;


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

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);


        titletxt = (TextView) findViewById(R.id.editTextnombreeditarrestaurante);
        descriptiontxt = (TextView) findViewById(R.id.editTextdescripcioneditarrestaurante);
        plato1txt = (TextView) findViewById(R.id.editTextplato1editarrestaurante);
        plato2txt = (TextView) findViewById(R.id.editTextplato2editarrestaurante);
        plato3txt = (TextView) findViewById(R.id.editTextplato3editarrestaurante);
        picimageview1 = (ImageView) findViewById(R.id.imageViewfotounoeditarrestaurante);
        picimageview2 = (ImageView) findViewById(R.id.imageViewfotodoseditarrestaurante);
        picimageview3 = (ImageView) findViewById(R.id.imageViewfototreseditarrestaurante);
        btneditar  = (Button) findViewById(R.id.btnaeditarrestaurante);
        btnactualizarpaleta = (Button) findViewById(R.id.btnapaletaactualizarrestaurante);
        relativepaleta = (RelativeLayout) findViewById(R.id.relativelayoutpaletaactualizar);
        picimageview1.setClickable(true);
        picimageview2.setClickable(true);
        picimageview3.setClickable(true);

        galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
             id = bundle.getString("id");

        }


        btnactualizarpaleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopupPaleta(v);
            }
        });

        ParseQuery<ParseObject> querymostrareditar = ParseQuery.getQuery("restaurante");
        querymostrareditar.whereEqualTo("objectId", id);
        querymostrareditar.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                if (e == null) {
                    relativepaleta.setBackgroundColor(Color.parseColor(object.getString("color")));
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
                            displayPopupFotos1(v);
                        }
                    });

                    picimageview2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(animAlpha);
                            displayPopupFotos2(v);
                        }
                    });

                    picimageview3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(animAlpha);
                            displayPopupFotos3(v);
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
                            object.put("fototres", picfile3);
                            object.put("color",color);
                            object.saveInBackground();

                            Toast.makeText(getApplicationContext(), "Actualizado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(editarrestaurante_delacalleactivity.this,listarestaurantesresponsable_delacalleactivity.class);
                            startActivity(intent);

                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Error en mostrar", Toast.LENGTH_SHORT).show();
                }
            }
        });








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
            picfile1 = new ParseFile("fotorestaurantetres.jpg",data);
            picfile1.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            picimageview1.setImageBitmap(Bitmap.createScaledBitmap(pic, 400, 400, false));


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
            picfile2 = new ParseFile("fotorestaurantetres.jpg",data);
            picfile2.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {


                }
            });
            picimageview2.setImageBitmap(Bitmap.createScaledBitmap(pic2,400,400,false));


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


    private void displayPopupFotos1(final View anchorView) {
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


    }

    private void displayPopupFotos2(final View anchorView) {
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


    }


    private void displayPopupFotos3(final View anchorView) {
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
        // Show anchored to button
        //   popup.setBackgroundDrawable(new BitmapDrawable());
        new Handler().postDelayed(new Runnable() {

            public void run() {
                popup.showAtLocation(anchorView, Gravity.TOP | Gravity.START | Gravity.CENTER_VERTICAL, 120, 300);
                popup.showAsDropDown(anchorView);
            }
        }, 100L);


    }



}
