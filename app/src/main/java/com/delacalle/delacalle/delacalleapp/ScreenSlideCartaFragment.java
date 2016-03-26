package com.delacalle.delacalle.delacalleapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.util.List;


public class ScreenSlideCartaFragment extends android.support.v4.app.Fragment {


    public static final String ARG_PAGE = "ARG_PAGE";
    public String nuevocarta;
    ImageView cartamenu;
    ParseFile filecarta;
    Bitmap pic;


    public static ScreenSlideCartaFragment create(int page,String postpardon)
    {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString("titulo", postpardon);
        ScreenSlideCartaFragment fragmentslide = new ScreenSlideCartaFragment();
        fragmentslide.setArguments(args);



        return fragmentslide;
    }

   // Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        nuevocarta = getArguments().getString("titulo");
        this.setRetainInstance(true);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_carta, container, false);


        cartamenu = (ImageView) rootView.findViewById(R.id.ImageViewFotoCartaMenu);


        final ParseQuery<ParseObject> query = ParseQuery.getQuery("restaurante");
        query.whereEqualTo("titulo", nuevocarta);

        final List<ParseObject> cartatlista;
        try {

            cartatlista = query.find();



            for (ParseObject cartas : cartatlista) {


            filecarta = cartas.getParseFile("fotouno");
            filecarta.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    pic = BitmapFactory.decodeByteArray(data, 0, data.length);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    //compres: format,quality,stream
                    pic.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                    cartamenu.setImageBitmap(Bitmap.createScaledBitmap(pic, 600,600,false));
                }
            });


            }
        }
    catch(ParseException e)

    {

    }

        return rootView;
    }

}
