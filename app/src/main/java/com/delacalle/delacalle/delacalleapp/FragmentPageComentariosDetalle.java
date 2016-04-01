package com.delacalle.delacalle.delacalleapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;


public class FragmentPageComentariosDetalle extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private ParseQueryAdapter<ParseObject> comentariosQueryAdapter;

    private ImageView fotoUsuarioComentario;
    private TextView  usuarioComentario;
    private TextView  comentarioComentario;


    String id;



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


}
