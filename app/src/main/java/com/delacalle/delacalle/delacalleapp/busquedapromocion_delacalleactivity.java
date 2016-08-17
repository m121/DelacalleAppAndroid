package com.delacalle.delacalle.delacalleapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class busquedapromocion_delacalleactivity extends AppCompatActivity {

    private ParseQueryAdapter<ParseObject> promocionesQueryAdapter;

    private Toolbar mToolbar;
    Bitmap pic;

    String id;

    String titulo;
    boolean isInternetPresent = false;
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busquedapromocion_delacalleactivity);

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        Tracker tracker = analytics.newTracker("UA-77841203-3");
        tracker.setScreenName("busquedapromocion");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        cd = new ConnectionDetector(getApplicationContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();


        //    titulo = (ArrayList<String>) getIntent().getSerializableExtra("titulo");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            titulo = bundle.getString("titulo");


        }
        else
        {
            Log.d("delacalle", "Error al pasar el titulo " + titulo);
        }



        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                promocionesbusqueda();

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        promocionesbusqueda();


    }


    public void promocionesbusqueda()
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
                            query.whereContains("promo", titulo);
                            return query;
                        }
                    };

            promocionesQueryAdapter = new ParseQueryAdapter<ParseObject>(this,factory)
            {

                @Override
                public View getItemView(final ParseObject resta, View view, ViewGroup parent)
                {
                    if(view == null)
                    {
                        view = View.inflate(getContext(),R.layout.plantilla_mostrarpromociones_delacalle,null);
                    }
                    CardView cardview = (CardView) view.findViewById(R.id.cardView);
                    cardview.setClickable(true);
                    TextView titletxt = (TextView) view.findViewById(R.id.editTextnombremostrarpromocionesnombrerestaurante);
                    TextView promotxt = (TextView) view.findViewById(R.id.editTextnombremostrarpromocionespromo);
                    final ImageView picimageview = (ImageView) view.findViewById(R.id.imageViewfotounomostrarpromocioneslogo);
                    ParseFile picfile;
                    titletxt.setTypeface(primerfontcandara);
                    promotxt.setTypeface(segundafontcaviar);



                    promotxt.setText(resta.getString("promo"));
                    titletxt.setText(resta.getString("nombre"));
                    picfile = resta.getParseFile("fotologo");
                    picfile.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            final BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 2;
                            Bitmap pic = BitmapFactory.decodeByteArray(data, 0, data.length,options);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            pic.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            picimageview.setImageBitmap(pic);
                        }
                    });

                    cardview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            resta.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    id = resta.getObjectId().toString();
                                    Intent intent = new Intent(busquedapromocion_delacalleactivity.this,detallerestaurante_delacalleactivity.class);
                                    intent.putExtra("id", id);
                                    busquedapromocion_delacalleactivity.this.startActivity(intent);
                                }
                            });

                        }
                    });



                    return view;
                }
            };



            ListView restaListView = (ListView) findViewById(R.id.listViewpromociones);
            restaListView.setAdapter(promocionesQueryAdapter);

        }catch(Exception e)
        {
            e.getStackTrace();
            Log.d("delacalle", "error en mostrar busqueda promociones "+e);
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        // or call onBackPressed()
        return true;
    }
}
