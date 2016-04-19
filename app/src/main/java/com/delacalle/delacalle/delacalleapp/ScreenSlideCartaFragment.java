package com.delacalle.delacalle.delacalleapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.util.List;


public class ScreenSlideCartaFragment extends android.support.v4.app.Fragment {

    private ImageView fotologoCartaDetalle;
    private ImageView fotorestauranteCartaDetalle;
    private TextView  nombrerestauranteCartaDetalle;
    private TextView  nombreplatoCartaDetalle;
    private TextView  descripcionplatoCartaDetalle;
    private TextView  precioplatoCartaDetalle;


    ParseFile  filefotocarta;
    ParseFile filefotologo;

    Bitmap pic;
    Bitmap pic2;


    public static final String ARG_PAGE = "ARG_PAGE";
    public String nuevocarta;
    ImageView cartamenu;
    ParseFile filecarta;



    public static ScreenSlideCartaFragment create(int page,String cartadela)
    {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString("nombre", cartadela);
        ScreenSlideCartaFragment fragmentslide = new ScreenSlideCartaFragment();
        fragmentslide.setArguments(args);



        return fragmentslide;
    }

   // Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        nuevocarta = getArguments().getString("nombre");
        this.setRetainInstance(true);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_carta, container, false);


        final Typeface primerfontcandara = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CandaraBold.ttf");
        final Typeface segundafontcaviar = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");



        fotologoCartaDetalle = (ImageView) rootView.findViewById(R.id.imageViewLogoDetalleCarta);
        fotorestauranteCartaDetalle = (ImageView) rootView.findViewById(R.id.imageViewFotoRestauranteDetalleCarta);
        nombrerestauranteCartaDetalle = (TextView) rootView.findViewById(R.id.textViewNombreRestauranteDetalleCarta);
        nombreplatoCartaDetalle = (TextView) rootView.findViewById(R.id.textViewNombrePlatoRestauranteDetalleCarta);
        descripcionplatoCartaDetalle = (TextView) rootView.findViewById(R.id.textViewDescripcionPlatoResturanteDetalleCarta);
        precioplatoCartaDetalle = (TextView) rootView.findViewById(R.id.textViewPrecioPlatoRestauranteDetalleCarta);
        nombrerestauranteCartaDetalle.setTypeface(primerfontcandara);
        nombreplatoCartaDetalle.setTypeface(primerfontcandara);
        descripcionplatoCartaDetalle.setTypeface(segundafontcaviar);
        precioplatoCartaDetalle.setTypeface(primerfontcandara);
        fotologoCartaDetalle.setClickable(true);


        final ParseQuery<ParseObject> query = ParseQuery.getQuery("carta");
        query.whereEqualTo("nombre", nuevocarta);

        final List<ParseObject> cartatlista;
        try {

            cartatlista = query.find();



            for (final ParseObject cartas : cartatlista) {


                filefotocarta = cartas.getParseFile("fotoplato");
                filefotocarta.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] data, ParseException e) {
                        if(e== null)
                        {
                            pic = BitmapFactory.decodeByteArray(data, 0, data.length);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            pic.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            fotorestauranteCartaDetalle.setImageBitmap(pic);
                        }
                        else if(e.getCode() == ParseException.OBJECT_NOT_FOUND)
                        {
                            Log.d("delacalle", "No se puede cargar la foto");
                        }
                    }
                });

                nombreplatoCartaDetalle.setText(cartas.getString("nombre"));
                descripcionplatoCartaDetalle.setText(cartas.getString("descripcion"));
                precioplatoCartaDetalle.setText("$"+cartas.getString("precio"));

                ParseQuery<ParseObject> queryrestaurante = ParseQuery.getQuery("restaurante");
                queryrestaurante.whereEqualTo("objectId", cartas.getString("restauranteId"));
                queryrestaurante.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject restaurante, ParseException e) {
                        if (e == null) {
                            filefotologo = restaurante.getParseFile("fotologo");
                            filefotologo.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    pic2 = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    pic2.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                                    fotologoCartaDetalle.setImageBitmap(pic2);
                                }
                            });
                            nombrerestauranteCartaDetalle.setText(restaurante.getString("nombre"));
                        } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                            Log.d("delacalle", "No se encuentra el restaurante");
                        }
                    }
                });
 /*       fotologoCartaDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String id = cartas.getString("restauranteId");
                Intent intent = new Intent(getActivity(),detallerestaurante_delacalleactivity.class);
                }
});*/

            }
        }
    catch(ParseException e)

    {

    }

        return rootView;
    }

}
