package com.delacalle.delacalle.delacalleapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;

import org.w3c.dom.Text;


public class PageFragmentInformacionDetalle extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";


    private TextView nombreR;
    private TextView descripcionR;
    private TextView direccionR;
    private TextView telefonoR;
    private TextView webR;
    private RatingBar ratingbarR;

    private TextView nombreT;
    private TextView descripcionT;
    private TextView direccionT;
    private TextView telefonoT;
    private TextView webT;

    String id;
    private float ratingR;
    double sumarate;
    int votos;

    private int mPage;

    public static PageFragmentInformacionDetalle newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragmentInformacionDetalle fragment = new PageFragmentInformacionDetalle();
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
            Log.d("delacalle","Error al pasar el id " + id);
        }
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_fragment_informacion_detalle, container, false);

        final Typeface primerfontcandara = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CandaraBold.ttf");
        final Typeface segundafontcaviar = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");

        nombreT = (TextView) view.findViewById(R.id.textViewtituloNombreRestauranteDetalle);
        descripcionT = (TextView) view.findViewById(R.id.textViewTituloDescripcionDetalle);
        direccionT = (TextView) view.findViewById(R.id.textViewTituloDireccionDetalle);
        telefonoT = (TextView) view.findViewById(R.id.textViewTituloTelefonoDetalle);
        webT = (TextView) view.findViewById(R.id.textViewTituloWebDetalle);

        nombreR = (TextView) view.findViewById(R.id.textViewNombreRestauranteDetalle);
        descripcionR = (TextView) view.findViewById(R.id.textViewDescripcionDetalle);
        direccionR = (TextView) view.findViewById(R.id.textViewDireccionDetalle);
        telefonoR = (TextView) view.findViewById(R.id.textViewTelefonoDetalle);
        webR = (TextView) view.findViewById(R.id.textViewWebDetalle);
        ratingbarR = (RatingBar) view.findViewById(R.id.ratingBarCalificacionDetalle);
        ratingbarR.setClickable(true);
        nombreT.setTypeface(primerfontcandara);
        descripcionT.setTypeface(primerfontcandara);
        direccionT.setTypeface(primerfontcandara);
        telefonoT.setTypeface(primerfontcandara);
        webT.setTypeface(primerfontcandara);
        nombreR.setTypeface(segundafontcaviar);
        descripcionR.setTypeface(segundafontcaviar);
        direccionR.setTypeface(segundafontcaviar);
        telefonoR.setTypeface(segundafontcaviar);
        webR.setTypeface(segundafontcaviar);





        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
    //    frameLayout.getBackground().setAlpha(0);
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) view.findViewById(R.id.fabmenu);
        final FloatingActionButton fabeditar = (FloatingActionButton) view.findViewById(R.id.fabeditar);
        final FloatingActionButton  fabrestaurante = (FloatingActionButton) view.findViewById(R.id.fabagregar);
        final FloatingActionButton  fabperfil = (FloatingActionButton) view.findViewById(R.id.fabperfil);

        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                //         frameLayout.getBackground().setAlpha(240);
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
                            else if (e.getCode() == ParseException.OBJECT_NOT_FOUND)
                            {
                                Log.d("delacalle","El usuario no tiene rol");
                                fabeditar.setVisibility(View.INVISIBLE);
                                fabrestaurante.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }

            }
        });

try
{
        ParseQuery<ParseObject> querymostrareditar = ParseQuery.getQuery("restaurante");
      //  querymostrareditar.whereEqualTo("objectId", id);
        querymostrareditar.getInBackground(id,new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                if (e == null) {

                    ratingR = object.getInt("sumavotos");
                    votos = object.getInt("votos");
                    nombreR.setText(object.getString("nombre"));
                    descripcionR.setText(object.getString("descripcion"));
                    direccionR.setText(object.getString("direccion"));
                    telefonoR.setText(object.getString("telefono"));
                    webR.setText(object.getString("web"));
              //      ratingbarR.setRating(object.getInt("rating"));  // es mejor quitarlo porque creo que si esta en 5 no se puede volver a calificar en 5


// se saca el num de estrellas anterior por ejem 5 y se le suma el rating del usuario actual por ejm 5 y luego se divide por la cantidad de votos actuales
                    ratingbarR.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            rating = ratingBar.getRating();
                            sumarate = rating + ratingR;
                            rating = (rating + ratingR) / (votos+1);
                            Log.d("delacalle","rating es " + rating);
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
                                                    object.put("sumavotos",sumarate);
                                                    object.saveEventually();
                                                    Toast.makeText(getActivity(), "Gracias por votar", Toast.LENGTH_SHORT).show();

                                                    ParseObject votosusuario = new ParseObject("calificacion");
                                                    votosusuario.put("idrestaurante", id);
                                                    votosusuario.put("idusuario", ParseUser.getCurrentUser().getObjectId());
                                                    votosusuario.put("voto", 1);
                                                    votosusuario.saveInBackground();

                                                    GoogleAnalytics analytics = GoogleAnalytics.getInstance(getActivity());
                                                    Tracker trackervotos = analytics.newTracker("UA-77841203-3");
                                                    trackervotos.send(new HitBuilders.EventBuilder()
                                                            .setCategory("Calificaciones")
                                                            .setAction("votado")
                                                            .setLabel(object.getString("nombre"))
                                                            .setValue(1)
                                                            .build());

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

}catch(Exception e)
{
    e.getStackTrace();
    Log.d("delacalle", "error en detalle informacion restaurante");
}

        return view;
    }

}
