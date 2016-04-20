package com.delacalle.delacalle.delacalleapp;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
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
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;



/**
 * Created by pc on 17/11/2015.
 */
// MENU DONDE MUESTRA LOS RESTAURANTES POR ARRIBA DE 3 ESTRELLAS
public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private ParseQueryAdapter<ParseObject> restaurantesQueryAdapter;
    Bitmap pic;
    Bitmap pic2;
    Bitmap pic3;


    TextView nombrecarta;
    TextView titletxt;
    TextView descriptiontxt;

    TextView plato1txt;
    TextView plato2txt;
    TextView plato3txt;
    ImageView picimageview1;
    ImageView picimageview2;
    ImageView picimageview3;
    ImageView imageviewcomentario;
    RatingBar ratingbarres;
    ParseFile picfile1;
    ParseFile picfile2;
    ParseFile picfile3;


    String id;
    private float ratingR;
    int votos;


    //comentarios
    TextView usuario;
    TextView comentario;



    private int mPage;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);


    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      final  View view = inflater.inflate(R.layout.fragment_page, container, false);
      //  TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
    //    tvTitle.setText("Fragment #" + mPage);

        final Typeface primerfontcandara = Typeface.createFromAsset(getActivity().getAssets(),"fonts/CandaraBold.ttf");
        final Typeface segundafontcaviar = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");

   final     SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);




        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
// Show results  in listview with my own adapter ParseQueryAdapter
                ParseQueryAdapter.QueryFactory<ParseObject> factory =
                        new ParseQueryAdapter.QueryFactory<ParseObject>() {
                            public ParseQuery<ParseObject> create() {
                                ParseQuery<ParseObject> query = ParseQuery.getQuery("restaurante");
                                query.whereGreaterThan("rating", 4);
                                return query;
                            }
                        };

                restaurantesQueryAdapter = new ParseQueryAdapter<ParseObject>(getActivity(), factory) {

                    @Override
                    public View getItemView(final ParseObject resta, View view, ViewGroup parent) {
                        if (view == null) {
                            view = View.inflate(getContext(), R.layout.plantilla_mostrarrestaurante_delacalle, null);
                        }
                        CardView cardview = (CardView) view.findViewById(R.id.cardView);
                        cardview.setClickable(true);
                        TextView titletxt = (TextView) view.findViewById(R.id.editTextnombremostrarrestaurante);
                        TextView descriptiontxt = (TextView) view.findViewById(R.id.editTextdescripcionmostrarrestaurante);
                        TextView direcciontxt = (TextView) view.findViewById(R.id.textViewDireccionM);
                        TextView telefonotxt = (TextView)   view.findViewById(R.id.textViewTelefonoM);
                        final ImageView picimageview = (ImageView) view.findViewById(R.id.imageViewfotounomostrarrestaurante);
                        RatingBar ratingbarres = (RatingBar) view.findViewById(R.id.ratingBarmostrarrestaurante);
                        ParseFile picfile;
                        titletxt.setTypeface(primerfontcandara);
                        descriptiontxt.setTypeface(segundafontcaviar);
                        direcciontxt.setTypeface(segundafontcaviar);
                        telefonotxt.setTypeface(segundafontcaviar);

                        cardview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                resta.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        id = resta.getObjectId().toString();
                                        Intent intent = new Intent(getActivity(),detallerestaurante_delacalleactivity.class);
                                        intent.putExtra("id", id);
                                        getActivity().startActivity(intent);
                                    }
                                });

                            }
                        });
                        cardview.setCardBackgroundColor(Color.parseColor(resta.getString("color")));
                        titletxt.setText(resta.getString("nombre"));
                        descriptiontxt.setText(resta.getString("descripcion"));
                        direcciontxt.setText(resta.getString("direccion"));
                        telefonotxt.setText(resta.getString("telefono"));
                        picfile = resta.getParseFile("fotologo");
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

                ListView restaListView = (ListView) view.findViewById(R.id.listViewrestaurantes);
                restaListView.setAdapter(restaurantesQueryAdapter);

                swipeRefreshLayout.setRefreshing(false);

            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
      //  frameLayout.getBackground().setAlpha(0);
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) view.findViewById(R.id.fabmenu);
        final FloatingActionButton fabeditar = (FloatingActionButton) view.findViewById(R.id.fabeditar);
        final FloatingActionButton  fabrestaurante = (FloatingActionButton) view.findViewById(R.id.fabagregar);
        final FloatingActionButton  fabperfil = (FloatingActionButton) view.findViewById(R.id.fabperfil);



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
        //        frameLayout.getBackground().setAlpha(0);
                frameLayout.setOnTouchListener(null);
            }
        });

        fabeditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), listarestaurantesresponsable_delacalleactivity.class);
                startActivity(intent);
            }
        });

        fabrestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), agregarrestaurante_delacalleactivity.class);
                startActivity(intent);
            }
        });

        fabperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), perfilusuario_delacalleactivity.class);
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
                            } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                Log.d("delacalle","eres administrador");

                            }
                        }
                    });
                }

            }
        });

        // Show results  in listview with my own adapter ParseQueryAdapter
        ParseQueryAdapter.QueryFactory<ParseObject> factory =
                new  ParseQueryAdapter.QueryFactory<ParseObject>(){
                    public ParseQuery<ParseObject> create () {
                        ParseQuery<ParseObject>  query = ParseQuery.getQuery("restaurante");
                        query.whereGreaterThan("rating",4);
                        return query;
                    }
                };

        restaurantesQueryAdapter = new ParseQueryAdapter<ParseObject>(getActivity(),factory)
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
                TextView direcciontxt = (TextView) view.findViewById(R.id.textViewDireccionM);
                TextView telefonotxt = (TextView)   view.findViewById(R.id.textViewTelefonoM);
                final ImageView picimageview = (ImageView) view.findViewById(R.id.imageViewfotounomostrarrestaurante);
                RatingBar ratingbarres = (RatingBar) view.findViewById(R.id.ratingBarmostrarrestaurante);
                ParseFile picfile;
                titletxt.setTypeface(primerfontcandara);
                descriptiontxt.setTypeface(segundafontcaviar);
                direcciontxt.setTypeface(segundafontcaviar);
                telefonotxt.setTypeface(segundafontcaviar);

                cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        resta.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                id = resta.getObjectId().toString();
                                Intent intent = new Intent(getActivity(),detallerestaurante_delacalleactivity.class);
                                intent.putExtra("id", id);
                                getActivity().startActivity(intent);
                            }
                        });

                    }
                });
                cardview.setCardBackgroundColor(Color.parseColor(resta.getString("color")));
                titletxt.setText(resta.getString("nombre"));
                descriptiontxt.setText(resta.getString("descripcion"));
                direcciontxt.setText(resta.getString("direccion"));
                telefonotxt.setText(resta.getString("telefono"));
                picfile = resta.getParseFile("fotologo");
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

        ListView restaListView = (ListView) view.findViewById(R.id.listViewrestaurantes);
        restaListView.setAdapter(restaurantesQueryAdapter);





        return view;
    }






}