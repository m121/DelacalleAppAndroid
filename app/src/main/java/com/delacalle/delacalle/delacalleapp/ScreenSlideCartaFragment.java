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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.util.List;


public class ScreenSlideCartaFragment extends android.support.v4.app.Fragment {

    private ImageView fotologoCartaDetalle;
    private ImageView fotorestauranteCartaDetalle;
    private TextView  nombrerestauranteCartaDetalle;
    private TextView  nombreplatoCartaDetalle;
    private TextView  descripcionplatoCartaDetalle;
    private TextView  precioplatoCartaDetalle;

    private RelativeLayout milayoutrestaurantelink;


    ParseFile  filefotocarta;
    ParseFile filefotologo;

    Bitmap pic;
    Bitmap pic2;

    String id;

    public static final String ARG_PAGE = "ARG_PAGE";
    public String nuevocarta;
    ImageView cartamenu;
    ParseFile filecarta;

    boolean isInternetPresent = false;
    ConnectionDetector cd;

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

        cd = new ConnectionDetector(getActivity().getApplicationContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_carta, container, false);


        final Typeface primerfontcandara = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CandaraBold.ttf");
        final Typeface segundafontcaviar = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");

        final Animation animAlpha = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_alpha);

        fotologoCartaDetalle = (ImageView) rootView.findViewById(R.id.imageViewLogoDetalleCarta);
        fotorestauranteCartaDetalle = (ImageView) rootView.findViewById(R.id.imageViewFotoRestauranteDetalleCarta);
        nombrerestauranteCartaDetalle = (TextView) rootView.findViewById(R.id.textViewNombreRestauranteDetalleCarta);
        nombreplatoCartaDetalle = (TextView) rootView.findViewById(R.id.textViewNombrePlatoRestauranteDetalleCarta);
        descripcionplatoCartaDetalle = (TextView) rootView.findViewById(R.id.textViewDescripcionPlatoResturanteDetalleCarta);
        precioplatoCartaDetalle = (TextView) rootView.findViewById(R.id.textViewPrecioPlatoRestauranteDetalleCarta);
        milayoutrestaurantelink = (RelativeLayout) rootView.findViewById(R.id.relativerestaurante);
        nombrerestauranteCartaDetalle.setTypeface(primerfontcandara);
        nombreplatoCartaDetalle.setTypeface(primerfontcandara);
        descripcionplatoCartaDetalle.setTypeface(segundafontcaviar);
        precioplatoCartaDetalle.setTypeface(primerfontcandara);
        milayoutrestaurantelink.setClickable(true);

        final FrameLayout frameLayout = (FrameLayout) rootView.findViewById(R.id.frame_layout);
        //  frameLayout.getBackground().setAlpha(0);
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) rootView.findViewById(R.id.fabmenu);
        final FloatingActionButton fabeditar = (FloatingActionButton) rootView.findViewById(R.id.fabeditar);
        final FloatingActionButton  fabrestaurante = (FloatingActionButton) rootView.findViewById(R.id.fabagregar);
        final FloatingActionButton  fabperfil = (FloatingActionButton) rootView.findViewById(R.id.fabperfil);



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
        if (isInternetPresent) {
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
                                    Log.d("delacalle", "El usuario no tiene rol");
                                    fabeditar.setVisibility(View.INVISIBLE);
                                    fabrestaurante.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }

                }
            });
        }
        else
        {
            fabeditar.setVisibility(View.INVISIBLE);
            fabrestaurante.setVisibility(View.INVISIBLE);
        }

        final ParseQuery<ParseObject> query = ParseQuery.getQuery("carta");
        query.whereEqualTo("nombre", nuevocarta);

        final List<ParseObject> cartatlista;
        try {

            cartatlista = query.find();



            for (final ParseObject cartas : cartatlista) {

                try
                {

                id = cartas.getString("restauranteId");
                milayoutrestaurantelink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setAnimation(animAlpha);
                        Intent intent = new Intent(getActivity(),detallerestaurante_delacalleactivity.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                        Log.d("delacalle","Se envia el id " + id + " y envia al usuario al restaurante");
                    }
                });


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

                nombreplatoCartaDetalle.setText(cartas.getString("nombre").toUpperCase());
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

                }catch(Exception e)
                {
                    e.getStackTrace();
                    Log.d("delacalle", "error en mostrar carta en screenslide");
                }
            }
        }
    catch(ParseException e)

    {

    }

        return rootView;
    }

}
