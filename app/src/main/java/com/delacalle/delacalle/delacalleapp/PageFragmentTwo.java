package com.delacalle.delacalle.delacalleapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
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


// MENU DONDE MUESTRA TODOS LOS RESTAURANTES
public class PageFragmentTwo extends android.support.v4.app.Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private ParseQueryAdapter<ParseObject> restaurantesQueryAdapter;

    Bitmap pic;
    Bitmap pic2;
    Bitmap pic3;

String titulo;

    boolean isInternetPresent = false;
    ConnectionDetector cd;


    String id;
    private float ratingR;
    int votos;


    private int mPage;

    public static PageFragmentTwo newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragmentTwo fragmenttwo = new PageFragmentTwo();
        fragmenttwo.setArguments(args);
        return fragmenttwo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

        cd = new ConnectionDetector(getActivity().getApplicationContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     final   View view = inflater.inflate(R.layout.categoriasplantilla, container, false);


        LinearLayout relativeInternacional = (LinearLayout) view.findViewById(R.id.internacionalrelative);
        LinearLayout relativetipica = (LinearLayout) view.findViewById(R.id.tipicorelative);
        LinearLayout relativegourmet = (LinearLayout) view.findViewById(R.id.gourmetlrelative);
        LinearLayout relativemariscos = (LinearLayout) view.findViewById(R.id.mariscosrelative);
        LinearLayout relativerapida = (LinearLayout) view.findViewById(R.id.rapidarelative);
        LinearLayout relativealmuerzo = (LinearLayout) view.findViewById(R.id.almuerzorelative);
        LinearLayout relativejugos = (LinearLayout) view.findViewById(R.id.jugosrelative);
        LinearLayout relativevegetariano = (LinearLayout) view.findViewById(R.id.vegetarianorelative);
        LinearLayout relativedomicilio = (LinearLayout) view.findViewById(R.id.domiciliorelative);
        LinearLayout relativecarnes = (LinearLayout) view.findViewById(R.id.carnesrelative);
        LinearLayout relativehelado = (LinearLayout) view.findViewById(R.id.heladorelative);
        LinearLayout relativepromo = (LinearLayout) view.findViewById(R.id.promosrelative);
        LinearLayout relativeeventos = (LinearLayout) view.findViewById(R.id.eventosrelative);
        relativeInternacional.setClickable(true);
        relativetipica.setClickable(true);
        relativegourmet.setClickable(true);
        relativemariscos.setClickable(true);
        relativerapida.setClickable(true);
        relativealmuerzo.setClickable(true);
        relativejugos.setClickable(true);
        relativevegetariano.setClickable(true);
        relativedomicilio.setClickable(true);
        relativecarnes.setClickable(true);
        relativehelado.setClickable(true);
        relativepromo.setClickable(true);
        relativeeventos.setClickable(true);

   //     INTERNACIONAL
        relativeInternacional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = "INTERNACIONAL";
                Intent intent = new Intent(getActivity(), mostrarcategorias_delacalleactivity.class);
                intent.putExtra("titulo", titulo);
                startActivity(intent);
            }
        });
    //    TIPICA
        relativetipica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = "TIPICA";
                Intent intent = new Intent(getActivity(), mostrarcategorias_delacalleactivity.class);
                intent.putExtra("titulo", titulo);
                startActivity(intent);
            }
        });
     //   GOURMET
        relativegourmet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = "GOURMET";
                Intent intent = new Intent(getActivity(), mostrarcategorias_delacalleactivity.class);
                intent.putExtra("titulo", titulo);
                startActivity(intent);
            }
        });
  //      MARISCOS
        relativemariscos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = "MARISCOS";
                Intent intent = new Intent(getActivity(), mostrarcategorias_delacalleactivity.class);
                intent.putExtra("titulo", titulo);
                startActivity(intent);
            }
        });
   //     RAPIDA
        relativerapida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = "RAPIDA";
                Intent intent = new Intent(getActivity(), mostrarcategorias_delacalleactivity.class);
                intent.putExtra("titulo", titulo);
                startActivity(intent);
            }
        });
    //    ALMUERZO
        relativealmuerzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = "ALMUERZO";
                Intent intent = new Intent(getActivity(), mostrarcategorias_delacalleactivity.class);
                intent.putExtra("titulo", titulo);
                startActivity(intent);
            }
        });
  //      JUGOS
        relativejugos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = "JUGOS";
                Intent intent = new Intent(getActivity(), mostrarcategorias_delacalleactivity.class);
                intent.putExtra("titulo", titulo);
                startActivity(intent);
            }
        });
  //      VEGETARIANO
        relativevegetariano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = "VEGETARIANO";
                Intent intent = new Intent(getActivity(), mostrarcategorias_delacalleactivity.class);
                intent.putExtra("titulo", titulo);
                startActivity(intent);
            }
        });
 //       DOMICILIO
        relativedomicilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), domicilio_delacalleactivity.class);
                startActivity(intent);
            }
        });
//        CARNES
        relativecarnes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = "CARNES";
                Intent intent = new Intent(getActivity(), mostrarcategorias_delacalleactivity.class);
                intent.putExtra("titulo", titulo);
                startActivity(intent);
            }
        });
     //   HELADO
        relativehelado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = "HELADO";
                Intent intent = new Intent(getActivity(), mostrarcategorias_delacalleactivity.class);
                intent.putExtra("titulo", titulo);
                startActivity(intent);
            }
        });
//        PROMOCIONES
        relativepromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = "PROMOCIONES";
                Intent intent = new Intent(getActivity(), mostrarcategorias_delacalleactivity.class);
                intent.putExtra("titulo", titulo);
                startActivity(intent);
            }
        });

        //  EVENTOS
        relativeeventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), eventos_delacalleactivity.class);
                startActivity(intent);
            }
        });




        return view;
    }


}
