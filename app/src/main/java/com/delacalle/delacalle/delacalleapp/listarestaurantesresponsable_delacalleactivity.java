package com.delacalle.delacalle.delacalleapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
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

    String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarestaurantesresponsable_delacalleactivity);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);



        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);

       /* final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        //     frameLayout.getBackground().setAlpha(0);
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
                Intent intent = new Intent(listarestaurantesresponsable_delacalleactivity.this, listarestaurantesresponsable_delacalleactivity.class);
                startActivity(intent);
            }
        });

        fabrestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listarestaurantesresponsable_delacalleactivity.this, agregarrestaurante_delacalleactivity.class);
                startActivity(intent);
            }
        });

        fabperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listarestaurantesresponsable_delacalleactivity.this, perfilusuario_delacalleactivity.class);
                startActivity(intent);
            }
        });*/


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
          listaresponsable();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        listaresponsable();

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

public void listaresponsable()
{
    final Typeface primerfontcandara = Typeface.createFromAsset(getAssets(), "fonts/CandaraBold.ttf");
    final Typeface segundafontcaviar = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
    try
    {

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
                TextView domiciliotxt = (TextView) view.findViewById(R.id.editTextdomiciliomostrarrestaurante);
                TextView telefonotxt = (TextView) view.findViewById(R.id.textViewTelefonoM);
                TextView direcciontxt = (TextView) view.findViewById(R.id.textViewDireccionM);
                final ImageView picimageview = (ImageView) view.findViewById(R.id.imageViewfotounomostrarrestaurante);
                RatingBar ratingbarres = (RatingBar) view.findViewById(R.id.ratingBarmostrarrestaurante);
                ParseFile picfile;
                titletxt.setTypeface(primerfontcandara);
                domiciliotxt.setTypeface(segundafontcaviar);
                telefonotxt.setTypeface(segundafontcaviar);
                direcciontxt.setTypeface(segundafontcaviar);

                telefonotxt.setText(resta.getString("telefono"));
                direcciontxt.setText(resta.getString("direccion"));
                domiciliotxt.setText("Domicilio: " + resta.getString("domicilio"));
                cardview.setCardBackgroundColor(Color.parseColor(resta.getString("color")));
                titletxt.setText(resta.getString("nombre"));
                picfile = resta.getParseFile("fotologo");
                picfile.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] data, ParseException e) {
                        final BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        Bitmap  pic = BitmapFactory.decodeByteArray(data, 0, data.length,options);
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
                                Intent intent = new Intent(listarestaurantesresponsable_delacalleactivity.this,editarrestaurante_delacalleactivity.class);
                                intent.putExtra("id", id);
                                listarestaurantesresponsable_delacalleactivity.this.startActivity(intent);
                            }
                        });

                    }
                });



                return view;
            }
        };

        ListView restaListView = (ListView) this.findViewById(R.id.listViewrestaurantes);
        restaListView.setAdapter(misrestaurantesQueryAdapter);

    }catch(Exception e)
    {
        e.getStackTrace();
        Log.d("delacalle", "error en mostrar contenido lista responsable");
    }
}

}
