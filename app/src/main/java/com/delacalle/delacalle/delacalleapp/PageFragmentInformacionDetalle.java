package com.delacalle.delacalle.delacalleapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
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

    String id;
    private float ratingR;
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

        nombreR = (TextView) view.findViewById(R.id.textViewNombreRestauranteDetalle);
        descripcionR = (TextView) view.findViewById(R.id.textViewDescripcionDetalle);
        direccionR = (TextView) view.findViewById(R.id.textViewDireccionDetalle);
        telefonoR = (TextView) view.findViewById(R.id.textViewTelefonoDetalle);
        webR = (TextView) view.findViewById(R.id.textViewWebDetalle);
        ratingbarR = (RatingBar) view.findViewById(R.id.ratingBarCalificacionDetalle);
        ratingbarR.setClickable(true);







        ParseQuery<ParseObject> querymostrareditar = ParseQuery.getQuery("restaurante");
        querymostrareditar.whereEqualTo("objectId", id);
        querymostrareditar.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject object, ParseException e) {
                if (e == null) {

                    ratingR = object.getInt("rating");
                    votos = object.getInt("votos");
                    nombreR.setText(object.getString("nombre"));
                    descripcionR.setText(object.getString("descripcion"));
                    direccionR.setText(object.getString("direccion"));
                    telefonoR.setText(object.getString("telefono"));
                    webR.setText(object.getString("web"));



                    ratingbarR.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
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

        return view;
    }

}
