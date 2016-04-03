package com.delacalle.delacalle.delacalleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;

public class cartaDetalle_delacalleactivity extends AppCompatActivity {

    private ImageView fotologoCartaDetalle;
    private ImageView fotorestauranteCartaDetalle;
    private TextView  nombrerestauranteCartaDetalle;
    private TextView  nombreplatoCartaDetalle;
    private TextView  descripcionplatoCartaDetalle;
    private TextView  precioplatoCartaDetalle;


    ParseFile  filefotocarta;
    ParseFile filefotologo;

    Bitmap pic;
    Bitmap pic2;

    private Toolbar mToolbar;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta_detalle_delacalleactivity);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


          fotologoCartaDetalle = (ImageView) findViewById(R.id.imageViewLogoDetalleCarta);
          fotorestauranteCartaDetalle = (ImageView) findViewById(R.id.imageViewFotoRestauranteDetalleCarta);
          nombrerestauranteCartaDetalle = (TextView) findViewById(R.id.textViewNombreRestauranteDetalleCarta);
          nombreplatoCartaDetalle = (TextView) findViewById(R.id.textViewNombrePlatoRestauranteDetalleCarta);
          descripcionplatoCartaDetalle = (TextView) findViewById(R.id.textViewDescripcionPlatoResturanteDetalleCarta);
          precioplatoCartaDetalle = (TextView) findViewById(R.id.textViewPrecioPlatoRestauranteDetalleCarta);

        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
    //    frameLayout.getBackground().setAlpha(0);
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.fabmenu);
        final FloatingActionButton fabeditar = (FloatingActionButton) findViewById(R.id.fabeditar);
        final FloatingActionButton  fabrestaurante = (FloatingActionButton) findViewById(R.id.fabagregar);
        final FloatingActionButton  fabperfil = (FloatingActionButton) findViewById(R.id.fabperfil);

        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
     //           frameLayout.getBackground().setAlpha(240);
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
                Intent intent = new Intent(cartaDetalle_delacalleactivity.this, listarestaurantesresponsable_delacalleactivity.class);
                startActivity(intent);
            }
        });

        fabrestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cartaDetalle_delacalleactivity.this, agregarrestaurante_delacalleactivity.class);
                startActivity(intent);
            }
        });

        fabperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cartaDetalle_delacalleactivity.this, perfilusuario_delacalleactivity.class);
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
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");

        }
        else
        {
            Log.d("delacalle", "Error al pasar el id " + id);
        }


        ParseQuery<ParseObject> queryplato = ParseQuery.getQuery("carta");
        queryplato.whereEqualTo("restauranteId",id);
        queryplato.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject carta, ParseException e) {
                if(e== null)
                {
                    filefotocarta = carta.getParseFile("fotoplato");
                    filefotocarta.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            if(e== null)
                            {
                                pic = BitmapFactory.decodeByteArray(data, 0, data.length);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                pic.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                                fotorestauranteCartaDetalle.setImageBitmap(pic);
                            }
                            else if(e.getCode() == ParseException.OBJECT_NOT_FOUND)
                            {
                                Log.d("delacalle","No se puede cargar la foto");
                            }
                        }
                    });

                    nombreplatoCartaDetalle.setText(carta.getString("nombre"));
                    descripcionplatoCartaDetalle.setText(carta.getString("descripcion"));
                    precioplatoCartaDetalle.setText("$"+carta.getString("precio"));

                    ParseQuery<ParseObject> queryrestaurante = ParseQuery.getQuery("restaurante");
                    queryrestaurante.whereEqualTo("objectId", id);
                    queryrestaurante.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject restaurante, ParseException e) {
                            if (e == null)
                            {
                                filefotologo = restaurante.getParseFile("fotologo");
                                filefotologo.getDataInBackground(new GetDataCallback() {
                                    @Override
                                    public void done(byte[] data, ParseException e) {
                                        pic2 = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                        pic2.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                                        fotologoCartaDetalle.setImageBitmap(pic2);
                                    }
                                });
                             nombrerestauranteCartaDetalle.setText(restaurante.getString("nombre"));
                            }
                            else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                Log.d("delacalle", "No se encuentra el restaurante");
                            }
                        }
                    });
                }
                else if(e.getCode() == ParseException.OBJECT_NOT_FOUND)
                {
                    Log.d("delacalle","Plato no encontrado");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carta_detalle_delacalleactivity, menu);
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

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        // or call onBackPressed()
        return true;
    }
}
