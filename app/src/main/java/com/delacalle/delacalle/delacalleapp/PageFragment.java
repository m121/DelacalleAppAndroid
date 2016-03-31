package com.delacalle.delacalle.delacalleapp;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
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
// In this case, the fragment displays simple text based on the page
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


   final     SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);




        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
// Show results  in listview with my own adapter ParseQueryAdapter
                ParseQueryAdapter.QueryFactory<ParseObject> factory =
                        new ParseQueryAdapter.QueryFactory<ParseObject>() {
                            public ParseQuery<ParseObject> create() {
                                ParseQuery<ParseObject> query = ParseQuery.getQuery("restaurante");
                                query.whereGreaterThan("rating", 3);
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
                        //     TextView menutxt = (TextView) view.findViewById(R.id.editTextmenumostrarrestaurante);
                        final ImageView picimageview = (ImageView) view.findViewById(R.id.imageViewfotounomostrarrestaurante);
                        RatingBar ratingbarres = (RatingBar) view.findViewById(R.id.ratingBarmostrarrestaurante);
                        ParseFile picfile;

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
                                picimageview.setImageBitmap(Bitmap.createScaledBitmap(pic, 200, 120, false));
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
        frameLayout.getBackground().setAlpha(0);
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) view.findViewById(R.id.fabmenu);
        final FloatingActionButton fabeditar = (FloatingActionButton) view.findViewById(R.id.fabeditar);
        final FloatingActionButton  fabrestaurante = (FloatingActionButton) view.findViewById(R.id.fabagregar);
        final FloatingActionButton  fabperfil = (FloatingActionButton) view.findViewById(R.id.fabperfil);



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
                        query.whereGreaterThan("rating",3);
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
                //     TextView menutxt = (TextView) view.findViewById(R.id.editTextmenumostrarrestaurante);
                final ImageView picimageview = (ImageView) view.findViewById(R.id.imageViewfotounomostrarrestaurante);
                RatingBar ratingbarres = (RatingBar) view.findViewById(R.id.ratingBarmostrarrestaurante);
                ParseFile picfile;

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

        ListView restaListView = (ListView) view.findViewById(R.id.listViewrestaurantes);
        restaListView.setAdapter(restaurantesQueryAdapter);





        return view;
    }

    private void displayPopupdetalleResta(final View anchorView) {
        final PopupWindow popup = new PopupWindow(getActivity().getBaseContext());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popupdetallerestaurante, null);
        popup.setContentView(layout);



        nombrecarta = (TextView) layout.findViewById(R.id.textViewtitulocartadetalle);
        titletxt = (TextView) layout.findViewById(R.id.editTextnombremostrarrestaurante);
        descriptiontxt = (TextView) layout.findViewById(R.id.editTextdescripcionmostrarrestaurante);
        plato1txt = (TextView) layout.findViewById(R.id.editTextplato1detallerestaurante);
        plato2txt = (TextView) layout.findViewById(R.id.editTextplato2detallerestaurante);
        plato3txt = (TextView) layout.findViewById(R.id.editTextplato3detallerestaurante);
        picimageview1 = (ImageView) layout.findViewById(R.id.imageViewfotounodetalle);
        picimageview2 = (ImageView) layout.findViewById(R.id.imageViewfotodosdetalle);
        picimageview3 = (ImageView) layout.findViewById(R.id.imageViewfototresdetalle);
        imageviewcomentario = (ImageView) layout.findViewById(R.id.imageViewComentarioDetalle);

        usuario = (TextView) layout.findViewById(R.id.textViewusuariocomentario);
        comentario = (TextView) layout.findViewById(R.id.editTextcomentario);

        ratingbarres = (RatingBar) layout.findViewById(R.id.ratingBarmostrarrestaurante);
        ratingbarres.setClickable(true);
        imageviewcomentario.setClickable(true);
        nombrecarta.setClickable(true);

        nombrecarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),carta_delacalleactivity.class);
                startActivity(intent);
            }
        });

        imageviewcomentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });


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

                    ratingbarres.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            rating = (rating + ratingR) / votos;
                            final float rate = rating;

                            final ParseQuery<ParseObject> usuarioRvotarq = ParseQuery.getQuery("restaurante");
                            usuarioRvotarq.whereEqualTo("objectId", id);
                            usuarioRvotarq.whereEqualTo("usuarioid", ParseUser.getCurrentUser());
                            usuarioRvotarq.getFirstInBackground(new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject objs, ParseException e) {
                                    if (e == null) {
                                        Toast.makeText(getActivity(), "¡No puedes votar en tus restaurantes!", Toast.LENGTH_SHORT).show();
                                    } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                        ParseQuery<ParseObject> usuariovotoq = ParseQuery.getQuery("calificacion");
                                        usuariovotoq.whereEqualTo("idrestaurante", id);
                                        usuariovotoq.whereEqualTo("idusuario", ParseUser.getCurrentUser().getObjectId());
                                        usuariovotoq.whereEqualTo("voto", 1);
                                        usuariovotoq.getFirstInBackground(new GetCallback<ParseObject>() {
                                            @Override
                                            public void done(ParseObject obj, ParseException e) {
                                                if (e == null) {
                                                    Toast.makeText(getActivity(), "¡Solamente puedes calificar una vez este restaurante!", Toast.LENGTH_SHORT).show();
                                                } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {

                                                    object.increment("votos", 1);
                                                    object.put("rating", rate);
                                                    object.saveEventually();
                                                    Toast.makeText(getActivity(), "Gracias por votar", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getActivity(), "Error en mostrar", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ParseQuery<ParseObject> querycomen = ParseQuery.getQuery("comentarios");
        querycomen.whereEqualTo("restauranteid",id);
        querycomen.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(e== null)
                {
                    usuario.setText(object.getString("nombreusuario"));
                    comentario.setText(object.getString("comentario"));

                }
                else if(e.getCode() == ParseException.OBJECT_NOT_FOUND)
                {
                    Toast.makeText(getActivity(), "No hay comentarios", Toast.LENGTH_SHORT).show();
                }
            }
        });




        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        popup.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
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




    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.popupcomentarios, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        comentario = (TextView) promptView.findViewById(R.id.editTextcomentario);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Enviar comentario", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int ids) {
                  final      String   comen = comentario.getText().toString();

                        ParseQuery<ParseObject> querycomentariopropio = ParseQuery.getQuery("comentarios");
                        querycomentariopropio.whereEqualTo("userid", ParseUser.getCurrentUser());
                        querycomentariopropio.whereEqualTo("restauranteid", id);
                        querycomentariopropio.whereEqualTo("numcom", 1);
                        querycomentariopropio.getFirstInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                if (e == null) {
                                    Toast.makeText(getActivity(), "¡No puedes comentar más de una vez!", Toast.LENGTH_SHORT).show();
                                } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                    ParseObject objcomentario = new ParseObject("comentarios");
                                    objcomentario.put("userid", ParseUser.getCurrentUser());
                                    objcomentario.put("nombreusuario", ParseUser.getCurrentUser().getUsername());
                                    objcomentario.put("restauranteid", id);
                                    objcomentario.put("comentario", comen);
                                    objcomentario.put("numcom", 1);
                                    objcomentario.saveInBackground();

                                    Toast.makeText(getActivity(), "Enviado", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



                    }
                })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


}