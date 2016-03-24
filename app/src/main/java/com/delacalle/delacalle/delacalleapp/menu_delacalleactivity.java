package com.delacalle.delacalle.delacalleapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.PagerAdapter;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class menu_delacalleactivity extends AppCompatActivity {

    private Toolbar mToolbar;
    Bitmap pic;

    Button btnagregar;

    TextView titletxt;
    TextView descriptiontxt;
    TextView menutxt;
    ImageView picimageview;
    RatingBar ratingbarres;
    ParseFile picfile;

    String id;
    private float ratingR;
    int votos;

    private ParseQueryAdapter<ParseObject> restaurantesQueryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_delacalleactivity);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
      //  btnagregar = (Button) findViewById(R.id.action_agregar);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setLogo(R.mipmap.ic_logo);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        frameLayout.getBackground().setAlpha(0);
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.fabmenu);
        final FloatingActionButton  fabeditar = (FloatingActionButton) findViewById(R.id.fabeditar);
        final FloatingActionButton  fabrestaurante = (FloatingActionButton) findViewById(R.id.fabagregar);
        final FloatingActionButton  fabperfil = (FloatingActionButton) findViewById(R.id.fabperfil);
        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                frameLayout.getBackground().setAlpha(240);
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
                frameLayout.getBackground().setAlpha(0);
                frameLayout.setOnTouchListener(null);
            }
        });

       fabeditar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(menu_delacalleactivity.this, listarestaurantesresponsable_delacalleactivity.class);
               startActivity(intent);
           }
       });

        fabrestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu_delacalleactivity.this, agregarrestaurante_delacalleactivity.class);
                startActivity(intent);
            }
        });

        fabperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu_delacalleactivity.this, menu_pestanas_delacalleactivity.class);
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





       /* SearchView searchView = (SearchView) findViewById(R.id.search);
// Sets searchable configuration defined in searchable.xml for this SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
          //  doMySearch(query);
        }
*/




        // Show results  in listview with my own adapter ParseQueryAdapter
        ParseQueryAdapter.QueryFactory<ParseObject> factory =
                new  ParseQueryAdapter.QueryFactory<ParseObject>(){
                    public ParseQuery<ParseObject> create () {
                        ParseQuery<ParseObject>  query = ParseQuery.getQuery("restaurante");
                        query.whereGreaterThan("rating",3);
                        return query;
                    }
                };

        restaurantesQueryAdapter = new ParseQueryAdapter<ParseObject>(this,factory)
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
                TextView descriptiontxt = (TextView) view.findViewById(R.id.editTextdescripcionmostrarrestaurante);
           //     TextView menutxt = (TextView) view.findViewById(R.id.editTextmenumostrarrestaurante);
                final   ImageView picimageview = (ImageView) view.findViewById(R.id.imageViewfotounomostrarrestaurante);
                RatingBar ratingbarres = (RatingBar) view.findViewById(R.id.ratingBarmostrarrestaurante);
                ParseFile picfile;

                cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        resta.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                id = resta.getObjectId().toString();
                                displayPopupdetalleResta(v);
                            }
                        });

                    }
                });

                titletxt.setText(resta.getString("titulo"));
                descriptiontxt.setText(resta.getString("descripcion"));
         //       menutxt.setText(resta.getString("menu"));
                picfile = resta.getParseFile("fotouno");
                picfile.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] data, ParseException e) {
                        pic = BitmapFactory.decodeByteArray(data, 0, data.length);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        pic.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                        picimageview.setImageBitmap(Bitmap.createScaledBitmap(pic,200,120,false));
                    }
                });
                ratingbarres.setRating(resta.getInt("rating"));


                return view;
            }
        };

        ListView restaListView = (ListView) this.findViewById(R.id.listViewrestaurantes);
        restaListView.setAdapter(restaurantesQueryAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_delacalleactivity, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String menu) {
                // perform query here
                ParseQuery<ParseObject> querybuscar = ParseQuery.getQuery("restaurante");
                querybuscar.whereEqualTo("menu", menu);
                querybuscar.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            // Show results  in listview with my own adapter ParseQueryAdapter
                            ParseQueryAdapter.QueryFactory<ParseObject> factory =
                                    new ParseQueryAdapter.QueryFactory<ParseObject>() {
                                        public ParseQuery<ParseObject> create() {
                                            ParseQuery<ParseObject> query = ParseQuery.getQuery("restaurante");
                                            query.whereEqualTo("menu", menu);
                                            return query;
                                        }
                                    };

                            restaurantesQueryAdapter = new ParseQueryAdapter<ParseObject>(getApplication(), factory) {

                                @Override
                                public View getItemView(final ParseObject resta, View view, ViewGroup parent) {
                                    if (view == null) {
                                        view = View.inflate(getContext(), R.layout.plantilla_mostrarrestaurante_delacalle, null);
                                    }

                                    TextView titletxt = (TextView) view.findViewById(R.id.editTextnombremostrarrestaurante);
                                    //      TextView descriptiontxt = (TextView) view.findViewById(R.id.editTextdescripcionmostrarrestaurante);
                                    //         TextView menutxt = (TextView) view.findViewById(R.id.editTextmenumostrarrestaurante);
                                    final ImageView picimageview = (ImageView) view.findViewById(R.id.imageViewfotounomostrarrestaurante);
                                    RatingBar ratingbarres = (RatingBar) view.findViewById(R.id.ratingBarmostrarrestaurante);
                                    ParseFile picfile;

                                    titletxt.setText(resta.getString("titulo"));
                                    //       descriptiontxt.setText(resta.getString("descripcion"));
                                    //        menutxt.setText(resta.getString("menu"));
                                    picfile = resta.getParseFile("fotouno");
                                    picfile.getDataInBackground(new GetDataCallback() {
                                        @Override
                                        public void done(byte[] data, ParseException e) {
                                            pic = BitmapFactory.decodeByteArray(data, 0, data.length);
                                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                            pic.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                                            picimageview.setImageBitmap(pic);
                                        }
                                    });
                                    ratingbarres.setRating(resta.getInt("rating"));


                                    return view;
                                }
                            };

                            ListView restaListView = (ListView) findViewById(R.id.listViewrestaurantes);
                            restaListView.setAdapter(restaurantesQueryAdapter);
                            searchView.requestFocus();
                            Toast.makeText(menu_delacalleactivity.this, "Lo has encontrado!", Toast.LENGTH_SHORT).show();
                        } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                            Toast.makeText(menu_delacalleactivity.this, "Lo siento, no lo has encontrado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

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

       /* if(id == R.id.action_agregar)
        {



        }*/

        if(id == R.id.action_mostrar)
        {
            Intent intent = new Intent(menu_delacalleactivity.this,mostrarrestaurante_delacalleactivity.class);
            startActivity(intent);
        }

        /*if(id == R.id.action_agregarrestaurante)
        {
            Intent intent = new Intent(menu_delacalleactivity.this, agregarrestaurante_delacalleactivity.class);
            startActivity(intent);
        }


        if(id == R.id.action_editar)
        {
            Intent intent = new Intent(menu_delacalleactivity.this, listarestaurantesresponsable_delacalleactivity.class);
            startActivity(intent);
        }*/



        return super.onOptionsItemSelected(item);
    }

   /* @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {

       *//* MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu_delacalleactivity, menu);*//*

        ParseQuery<ParseRole> roleuserusuario = ParseRole.getQuery();
        roleuserusuario.whereEqualTo("name", "usuario");
        roleuserusuario.whereEqualTo("users", ParseUser.getCurrentUser().getObjectId());
        roleuserusuario.getFirstInBackground(new GetCallback<ParseRole>() {
            @Override
            public void done(ParseRole object, ParseException e) {
                if (e == null) {
                    menu.findItem(R.id.action_agregar).setVisible(false);
                } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                    ParseQuery<ParseRole> roleuserresponsable = ParseRole.getQuery();
                    roleuserresponsable.whereEqualTo("name", "responsable");
                    roleuserresponsable.whereEqualTo("users", ParseUser.getCurrentUser().getObjectId());
                    roleuserresponsable.getFirstInBackground(new GetCallback<ParseRole>() {
                        @Override
                        public void done(ParseRole object, ParseException e) {
                            if (e == null) {
                                menu.findItem(R.id.action_agregar).setVisible(true);
                            }
                        }
                    });
                }

            }
        });


        return super.onPrepareOptionsMenu(menu);

    }*/



    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void displayPopupdetalleResta(final View anchorView) {
        final PopupWindow popup = new PopupWindow(menu_delacalleactivity.this);
        View layout = getLayoutInflater().inflate(R.layout.popupdetallerestaurante, null);
        popup.setContentView(layout);


        titletxt = (TextView) layout.findViewById(R.id.editTextnombremostrarrestaurante);
        descriptiontxt = (TextView) layout.findViewById(R.id.editTextdescripcionmostrarrestaurante);
        menutxt = (TextView) layout.findViewById(R.id.editTextplato1detallerestaurante);
        picimageview = (ImageView) layout.findViewById(R.id.imageViewfotounomostrarrestaurante);
        ratingbarres = (RatingBar) layout.findViewById(R.id.ratingBarmostrarrestaurante);
        ratingbarres.setClickable(true);


        ParseQuery<ParseObject> querymostrareditar = ParseQuery.getQuery("restaurante");
        querymostrareditar.whereEqualTo("objectId", id);
        querymostrareditar.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                if (e == null) {

                    ratingR = object.getInt("rating");
                    votos = object.getInt("votos");
                    titletxt.setText(object.getString("titulo"));
                    descriptiontxt.setText(object.getString("descripcion"));
                    menutxt.setText(object.getString("menu"));
                    picfile = object.getParseFile("fotouno");
                    picfile.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            pic = BitmapFactory.decodeByteArray(data, 0, data.length);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            pic.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            picimageview.setImageBitmap(Bitmap.createScaledBitmap(pic, 200, 120, false));
                        }
                    });

                    ratingbarres.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar,  float rating, boolean fromUser) {
                            rating = (rating + ratingR) / votos;
                            final float rate = rating;

                            final ParseQuery<ParseObject> usuarioRvotarq = ParseQuery.getQuery("restaurante");
                            usuarioRvotarq.whereEqualTo("objectId", id);
                            usuarioRvotarq.whereEqualTo("usuarioid", ParseUser.getCurrentUser());
                            usuarioRvotarq.getFirstInBackground(new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject objs, ParseException e) {
                                    if (e == null) {
                                        Toast.makeText(getApplicationContext(), "¡No puedes votar en tus restaurantes!", Toast.LENGTH_SHORT).show();
                                    } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                        ParseQuery<ParseObject> usuariovotoq = ParseQuery.getQuery("calificacion");
                                        usuariovotoq.whereEqualTo("idrestaurante", id);
                                        usuariovotoq.whereEqualTo("idusuario", ParseUser.getCurrentUser().getObjectId());
                                        usuariovotoq.whereEqualTo("voto",1);
                                        usuariovotoq.getFirstInBackground(new GetCallback<ParseObject>() {
                                            @Override
                                            public void done(ParseObject obj, ParseException e) {
                                                if (e == null) {
                                                    Toast.makeText(getApplicationContext(), "¡Solamente puedes calificar una vez este restaurante!", Toast.LENGTH_SHORT).show();
                                                } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {

                                                    object.increment("votos", 1);
                                                    object.put("rating", rate);
                                                    object.saveEventually();
                                                    Toast.makeText(getApplicationContext(), "Gracias por votar", Toast.LENGTH_SHORT).show();

                                                    ParseObject votosusuario = new ParseObject("calificacion");
                                                    votosusuario.put("idrestaurante", id);
                                                    votosusuario.put("idusuario", ParseUser.getCurrentUser().getObjectId());
                                                    votosusuario.put("voto", 1);
                                                    votosusuario.saveInBackground();




                                                }
                                            }
                                        });
                                    }

                                }
                            });



                        }
                    });



                } else {
                    Toast.makeText(getApplicationContext(), "Error en mostrar", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Set content width and height
        popup.setHeight(600);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
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


