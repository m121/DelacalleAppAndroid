package com.delacalle.delacalle.delacalleapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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


public class FragmentPageComentariosDetalle extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private ParseQueryAdapter<ParseObject> comentariosQueryAdapter;

    private ImageView fotoUsuarioComentario;
    private TextView  usuarioComentario;
    private TextView  comentarioComentario;


    String id;

    //Comentarios
    TextView comentario;
    Button btncomentario;


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
        View view = inflater.inflate(R.layout.fragment_fragment_page_comentarios_detalle ,container, false);

        btncomentario = (Button) view.findViewById(R.id.btnGuardarComentario);

        btncomentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialogComentarios();
            }
        });

        ParseQueryAdapter.QueryFactory<ParseObject> factory =
                new ParseQueryAdapter.QueryFactory<ParseObject>() {
                    public ParseQuery<ParseObject> create() {
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("comentarios");
                        query.whereEqualTo("restauranteid", id);
                        return query;
                    }
                };

        comentariosQueryAdapter = new ParseQueryAdapter<ParseObject>(getActivity(), factory) {

            @Override
            public View getItemView(final ParseObject comen, View view, ViewGroup parent) {
                if (view == null) {
                    view = View.inflate(getContext(), R.layout.plantilla_comentariosrestaurante, null);
                }

                usuarioComentario = (TextView) view.findViewById(R.id.textViewNombreUsuario);
                comentarioComentario = (TextView) view.findViewById(R.id.textViewComentario);

                usuarioComentario.setText(comen.getString("nombreusuario"));
                comentarioComentario.setText(comen.getString("comentario"));



                Log.d("delacalle","comentario mostrado");

                return view;
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
