package com.delacalle.delacalle.delacalleapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.List;


public class FragmentPageComentariosDetalle extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private ParseQueryAdapter<ParseObject> comentariosQueryAdapter;

   /* private ImageView fotoUsuarioComentario;
    private TextView  usuarioComentario;
    private TextView  comentarioComentario;*/

    ParseFile filefoto;
    Bitmap pic;


    String id;
    String nombreusuario;

    //Comentarios
    TextView comentario;
    Button btncomentario;

    ImageView    fotoUsuarioComentario;
    TextView     usuarioComentario;
    TextView     comentarioComentario;


    private int mPage;

    public static FragmentPageComentariosDetalle newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentPageComentariosDetalle fragment = new FragmentPageComentariosDetalle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");

        }
        else
        {
            Log.d("delacalle", "Error al pasar el id " + id);
        }
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final    View view = inflater.inflate(R.layout.fragment_fragment_page_comentarios_detalle ,container, false);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);

        final Typeface primerfontcandara = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CandaraBold.ttf");
        final Typeface segundafontcaviar = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");


        fotoUsuarioComentario = (ImageView) view.findViewById(R.id.imageViewFotoUsuarioComentario);
        usuarioComentario = (TextView) view.findViewById(R.id.textViewNombreUsuario);
        comentarioComentario = (TextView) view.findViewById(R.id.textViewComentario);

        btncomentario = (Button) view.findViewById(R.id.btnGuardarComentario);
        btncomentario.setTypeface(primerfontcandara);

        btncomentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialogComentarios();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ParseQueryAdapter.QueryFactory<ParseObject> factory =
                        new ParseQueryAdapter.QueryFactory<ParseObject>() {
                            public ParseQuery<ParseObject> create() {
                                ParseQuery<ParseObject> query = ParseQuery.getQuery("comentarios");
                                query.whereEqualTo("restauranteid", id);
                                query.orderByAscending("createdAt");
                                return query;
                            }
                        };

                comentariosQueryAdapter = new ParseQueryAdapter<ParseObject>(getActivity(), factory) {

                    @Override
                    public View getItemView(final ParseObject comen, View view, ViewGroup parent) {
                        if (view == null) {
                            view = View.inflate(getContext(), R.layout.plantilla_comentariosrestaurante, null);
                        }
                 final ImageView fotoUsuarioComentario = (ImageView) view.findViewById(R.id.imageViewFotoUsuarioComentario);
                 TextView       usuarioComentario = (TextView) view.findViewById(R.id.textViewNombreUsuario);
                 TextView       comentarioComentario = (TextView) view.findViewById(R.id.textViewComentario);
                        usuarioComentario.setTypeface(primerfontcandara);
                        comentarioComentario.setTypeface(segundafontcaviar);

                        nombreusuario  = comen.getString("username");


                        usuarioComentario.setText(comen.getString("nombreusuario"));
                        comentarioComentario.setText(comen.getString("comentario"));
                        ParseQuery<ParseUser> userfotoq = ParseUser.getQuery();
                        userfotoq.whereEqualTo("username", nombreusuario);
                        userfotoq.getFirstInBackground(new GetCallback<ParseUser>() {
                            @Override
                            public void done(ParseUser usuario, ParseException e) {
                                if (e == null) {
                                    filefoto = usuario.getParseFile("fotousuario");
                                    if (filefoto != null) {
                                        filefoto.getDataInBackground(new GetDataCallback() {
                                            @Override
                                            public void done(byte[] data, ParseException e) {
                                     pic = BitmapFactory.decodeByteArray(data, 0, data.length);
                                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                                pic.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                                                fotoUsuarioComentario.setImageBitmap(pic);

                                            }
                                        });
                                        Log.d("delacalle", "foto usuario mostrada");
                                    }
                                } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                    Log.d("delacalle", "No se encuentra el usuario con el nombre");
                                }
                            }
                        });


                        Log.d("delacalle", "comentario mostrado");

                        return view;
                    }
                };

                ListView comentariosListView = (ListView) view.findViewById(R.id.listViewComentariosrestaurante);
                comentariosListView.setAdapter(comentariosQueryAdapter);

                swipeRefreshLayout.setRefreshing(false);

            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
  //      frameLayout.getBackground().setAlpha(0);
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) view.findViewById(R.id.fabmenu);
        final FloatingActionButton fabeditar = (FloatingActionButton) view.findViewById(R.id.fabeditar);
        final FloatingActionButton  fabrestaurante = (FloatingActionButton) view.findViewById(R.id.fabagregar);
        final FloatingActionButton  fabperfil = (FloatingActionButton) view.findViewById(R.id.fabperfil);

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
                //           frameLayout.getBackground().setAlpha(0);
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


        ParseQueryAdapter.QueryFactory<ParseObject> factory =
                new ParseQueryAdapter.QueryFactory<ParseObject>() {
                    public ParseQuery<ParseObject> create() {
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("comentarios");
                        query.whereEqualTo("restauranteid", id);
                        query.orderByAscending("createdAt");
                        return query;
                    }
                };

        comentariosQueryAdapter = new ParseQueryAdapter<ParseObject>(getActivity(), factory) {

            @Override
            public View getItemView(final ParseObject comen, View vista, ViewGroup parent) {
                if (vista == null) {
                    vista = View.inflate(getContext(), R.layout.plantilla_comentariosrestaurante, null);
                }
            final ImageView    fotoUsuarioComentario = (ImageView) vista.findViewById(R.id.imageViewFotoUsuarioComentario);
           TextView     usuarioComentario = (TextView) vista.findViewById(R.id.textViewNombreUsuario);
           TextView     comentarioComentario = (TextView) vista.findViewById(R.id.textViewComentario);
                usuarioComentario.setTypeface(primerfontcandara);
                comentarioComentario.setTypeface(segundafontcaviar);

                nombreusuario  = comen.getString("username");

                usuarioComentario.setText(comen.getString("nombreusuario"));
                comentarioComentario.setText(comen.getString("comentario"));
                ParseQuery<ParseUser> userfotoq = ParseUser.getQuery();
                userfotoq.whereEqualTo("username", nombreusuario);
                userfotoq.getFirstInBackground(new GetCallback<ParseUser>() {
                    @Override
                    public void done(ParseUser usuario, ParseException e) {
                        if (e == null) {
                            filefoto = usuario.getParseFile("fotousuario");
                            if (filefoto != null) {
                                filefoto.getDataInBackground(new GetDataCallback() {
                                    @Override
                                    public void done(byte[] data, ParseException e) {
                                  pic = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                        pic.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                                        fotoUsuarioComentario.setImageBitmap(pic);

                                    }
                                });
                                Log.d("delacalle", "foto usuario mostrada");
                            }
                        } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                            Log.d("delacalle", "No se encuentra el usuario con el nombre");
                        }
                    }
                });


                Log.d("delacalle", "comentario mostrado");

                return vista;

            }

        };

        ListView comentariosListView = (ListView) view.findViewById(R.id.listViewComentariosrestaurante);
        comentariosListView.setAdapter(comentariosQueryAdapter);







        return view;
    }

    protected void showInputDialogComentarios() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.popupcomentarios, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
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
                                    String nombreusuario = ParseUser.getCurrentUser().getString("nombre");
                                    // Si el usuario no tiene nombre aun, entonces se pondra el username que en este caso es el correo
                                    if(nombreusuario != null) {
                                        objcomentario.put("nombreusuario", nombreusuario);
                                    }
                                    else
                                    {
                                        objcomentario.put("nombreusuario",ParseUser.getCurrentUser().getUsername());
                                    }
                                    objcomentario.put("username",ParseUser.getCurrentUser().getUsername());
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
