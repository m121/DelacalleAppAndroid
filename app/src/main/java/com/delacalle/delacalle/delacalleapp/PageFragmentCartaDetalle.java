package com.delacalle.delacalle.delacalleapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;


public class PageFragmentCartaDetalle extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private TextView nombrePlato1;
    private TextView descripcionPlato1;
    private TextView precioPlato1;
    private ImageView fotoPlato1;

    private TextView nombrePlato2;
    private TextView descripcionPlato2;
    private TextView precioPlato2;
    private ImageView fotoPlato2;

    private TextView nombrePlato3;
    private TextView descripcionPlato3;
    private TextView precioPlato3;
    private ImageView fotoPlato3;

    private TextView nombrePlato4;
    private TextView descripcionPlato4;
    private TextView precioPlato4;
    private ImageView fotoPlato4;

    private TextView nombrePlato5;
    private TextView descripcionPlato5;
    private TextView precioPlato5;
    private ImageView fotoPlato5;

    private TextView nombrePlato6;
    private TextView descripcionPlato6;
    private TextView precioPlato6;
    private ImageView fotoPlato6;


    private RelativeLayout relativelayoutPlato1;
    private RelativeLayout relativelayoutPlato2;
    private RelativeLayout relativelayoutPlato3;
    private RelativeLayout relativelayoutPlato4;
    private RelativeLayout relativelayoutPlato5;
    private RelativeLayout relativelayoutPlato6;


    ParseFile fotofileplato1;
    ParseFile fotofileplato2;
    ParseFile fotofileplato3;
    ParseFile fotofileplato4;
    ParseFile fotofileplato5;
    ParseFile fotofileplato6;


    private int mPage;


    String id;

    public static PageFragmentCartaDetalle newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragmentCartaDetalle fragment = new PageFragmentCartaDetalle();
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
        View view = inflater.inflate(R.layout.fragment_page_fragment_carta_detalle ,container, false);


        Bitmap pic;
        ParseFile filefotocarta;

        final Typeface primerfontcandara = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CandaraBold.ttf");
        final Typeface segundafontcaviar = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");



          nombrePlato1 = (TextView) view.findViewById(R.id.textViewPlatoCarta1);
          descripcionPlato1 = (TextView) view.findViewById(R.id.textViewDescripcionCarta1);
          precioPlato1 = (TextView) view.findViewById(R.id.textViewPrecioCarta1);
         fotoPlato1 = (ImageView) view.findViewById(R.id.imageViewplato1);
        nombrePlato1.setTypeface(primerfontcandara);
        descripcionPlato1.setTypeface(segundafontcaviar);
        precioPlato1.setTypeface(primerfontcandara);

        nombrePlato2 = (TextView) view.findViewById(R.id.textViewPlatoCarta2);
        descripcionPlato2 = (TextView) view.findViewById(R.id.textViewDescripcionCarta2);
        precioPlato2 = (TextView) view.findViewById(R.id.textViewPrecioCarta2);
        fotoPlato2 = (ImageView) view.findViewById(R.id.imageViewplato2);
        nombrePlato2.setTypeface(primerfontcandara);
        descripcionPlato2.setTypeface(segundafontcaviar);
        precioPlato2.setTypeface(primerfontcandara);



        nombrePlato3 = (TextView) view.findViewById(R.id.textViewPlatoCarta3);
        descripcionPlato3 = (TextView) view.findViewById(R.id.textViewDescripcionCarta3);
        precioPlato3 = (TextView) view.findViewById(R.id.textViewPrecioCarta3);
        fotoPlato3 = (ImageView) view.findViewById(R.id.imageViewplato3);
        nombrePlato3.setTypeface(primerfontcandara);
        descripcionPlato3.setTypeface(segundafontcaviar);
        precioPlato3.setTypeface(primerfontcandara);



        nombrePlato4 = (TextView) view.findViewById(R.id.textViewPlatoCarta4);
        descripcionPlato4 = (TextView) view.findViewById(R.id.textViewDescripcionCarta4);
        precioPlato4 = (TextView) view.findViewById(R.id.textViewPrecioCarta4);
        fotoPlato4 = (ImageView) view.findViewById(R.id.imageViewplato4);
        nombrePlato4.setTypeface(primerfontcandara);
        descripcionPlato4.setTypeface(segundafontcaviar);
        precioPlato4.setTypeface(primerfontcandara);



        nombrePlato5 = (TextView) view.findViewById(R.id.textViewPlatoCarta5);
        descripcionPlato5 = (TextView) view.findViewById(R.id.textViewDescripcionCarta5);
        precioPlato5 = (TextView) view.findViewById(R.id.textViewPrecioCarta5);
        fotoPlato5 = (ImageView) view.findViewById(R.id.imageViewplato5);
        nombrePlato5.setTypeface(primerfontcandara);
        descripcionPlato5.setTypeface(segundafontcaviar);
        precioPlato5.setTypeface(primerfontcandara);



        nombrePlato6 = (TextView) view.findViewById(R.id.textViewPlatoCarta6);
        descripcionPlato6 = (TextView) view.findViewById(R.id.textViewDescripcionCarta6);
        precioPlato6 = (TextView) view.findViewById(R.id.textViewPrecioCarta6);
        fotoPlato6 = (ImageView) view.findViewById(R.id.imageViewplato6);
        nombrePlato6.setTypeface(primerfontcandara);
        descripcionPlato6.setTypeface(segundafontcaviar);
        precioPlato6.setTypeface(primerfontcandara);




        relativelayoutPlato1 = (RelativeLayout) view.findViewById(R.id.relative1);
        relativelayoutPlato2 = (RelativeLayout) view.findViewById(R.id.relative2);
        relativelayoutPlato3 = (RelativeLayout) view.findViewById(R.id.relative3);
        relativelayoutPlato4 = (RelativeLayout) view.findViewById(R.id.relative4);
        relativelayoutPlato5 = (RelativeLayout) view.findViewById(R.id.relative5);
        relativelayoutPlato6 = (RelativeLayout) view.findViewById(R.id.relative6);

        relativelayoutPlato1.setClickable(true);
        relativelayoutPlato2.setClickable(true);
        relativelayoutPlato3.setClickable(true);
        relativelayoutPlato4.setClickable(true);
        relativelayoutPlato5.setClickable(true);
        relativelayoutPlato6.setClickable(true);



        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
 //       frameLayout.getBackground().setAlpha(0);
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
      //          frameLayout.getBackground().setAlpha(0);
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
        ParseQuery<ParseObject>  cartaquery = ParseQuery.getQuery("carta");
        cartaquery.whereEqualTo("restauranteId", id);
        cartaquery.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject carta, ParseException e) {
                if (e == null) {
                    nombrePlato1.setText(carta.getString("nombre").toUpperCase());
                    descripcionPlato1.setText(carta.getString("descripcion"));
                    precioPlato1.setText("$" + carta.getString("precio"));
                    fotofileplato1 = carta.getParseFile("fotoplato");
                    fotofileplato1.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            final BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 4;
                    Bitmap  pic = BitmapFactory.decodeByteArray(data, 0, data.length,options);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            pic.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            fotoPlato1.setImageBitmap(pic);

                        }
                    });
                    relativelayoutPlato1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            carta.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {

                                        Intent intent = new Intent(getActivity(), cartaDetalle_delacalleactivity.class);
                                        intent.putExtra("id", carta.getObjectId());
                                        getActivity().startActivity(intent);
                                    } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                        Log.d("delacalle", "no se puede guardar la carta");
                                    }
                                }
                            });
                        }
                    });

                } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                    Log.d("delacalle", "Carta no encontrada");
                }
            }
        });

        ParseQuery<ParseObject>  cartaquery2 = ParseQuery.getQuery("carta");
        cartaquery2.whereEqualTo("restauranteId",id);
        cartaquery2.setSkip(1);
        cartaquery2.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject carta, ParseException e) {
                if (e == null) {
                    nombrePlato2.setText(carta.getString("nombre").toUpperCase());
                    descripcionPlato2.setText(carta.getString("descripcion"));
                    precioPlato2.setText("$"+carta.getString("precio"));
                    fotofileplato2 = carta.getParseFile("fotoplato");
                    fotofileplato2.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            final BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 4;
                            Bitmap  pic2 = BitmapFactory.decodeByteArray(data, 0, data.length,options);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            pic2.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            fotoPlato2.setImageBitmap(pic2);
                        }
                    });
                    relativelayoutPlato2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            carta.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {

                                        Intent intent = new Intent(getActivity(), cartaDetalle_delacalleactivity.class);
                                        intent.putExtra("id", carta.getObjectId());
                                        getActivity().startActivity(intent);
                                    } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                        Log.d("delacalle", "no se puede guardar la carta");
                                    }
                                }
                            });
                        }
                    });

                } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                    Log.d("delacalle", "Carta no encontrada");
                }
            }
        });

        ParseQuery<ParseObject>  cartaquery3 = ParseQuery.getQuery("carta");
        cartaquery3.whereEqualTo("restauranteId",id);
        cartaquery3.setSkip(2);
        cartaquery3.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject carta, ParseException e) {
                if (e == null) {
                    nombrePlato3.setText(carta.getString("nombre").toUpperCase());
                    descripcionPlato3.setText(carta.getString("descripcion"));
                    precioPlato3.setText("$"+carta.getString("precio"));
                    fotofileplato3 = carta.getParseFile("fotoplato");
                    fotofileplato3.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            final BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 4;
                            Bitmap  pic3 = BitmapFactory.decodeByteArray(data, 0, data.length,options);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            pic3.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            fotoPlato3.setImageBitmap(pic3);
                        }
                    });
                    relativelayoutPlato3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            carta.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {

                                        Intent intent = new Intent(getActivity(), cartaDetalle_delacalleactivity.class);
                                        intent.putExtra("id", carta.getObjectId());
                                        getActivity().startActivity(intent);
                                    } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                        Log.d("delacalle", "no se puede guardar la carta");
                                    }
                                }
                            });
                        }
                    });

                } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                    Log.d("delacalle", "Carta no encontrada");
                }
            }
        });

        ParseQuery<ParseObject>  cartaquery4 = ParseQuery.getQuery("carta");
        cartaquery4.whereEqualTo("restauranteId",id);
        cartaquery4.setSkip(3);
        cartaquery4.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject carta, ParseException e) {
                if (e == null) {
                    nombrePlato4.setText(carta.getString("nombre").toUpperCase());
                    descripcionPlato4.setText(carta.getString("descripcion"));
                    precioPlato4.setText("$"+carta.getString("precio"));
                    fotofileplato4 = carta.getParseFile("fotoplato");
                    fotofileplato4.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            final BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 4;
                            Bitmap  pic4 = BitmapFactory.decodeByteArray(data, 0, data.length,options);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            pic4.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            fotoPlato4.setImageBitmap(pic4);
                        }
                    });
                    relativelayoutPlato4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            carta.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {

                                        Intent intent = new Intent(getActivity(), cartaDetalle_delacalleactivity.class);
                                        intent.putExtra("id", carta.getObjectId());
                                        getActivity().startActivity(intent);
                                    } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                        Log.d("delacalle", "no se puede guardar la carta");
                                    }
                                }
                            });
                        }
                    });

                } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                    Log.d("delacalle", "Carta no encontrada");
                }
            }
        });

        ParseQuery<ParseObject>  cartaquery5 = ParseQuery.getQuery("carta");
        cartaquery5.whereEqualTo("restauranteId",id);
        cartaquery5.setSkip(4);
        cartaquery5.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject carta, ParseException e) {
                if (e == null) {
                    nombrePlato5.setText(carta.getString("nombre").toUpperCase());
                    descripcionPlato5.setText(carta.getString("descripcion"));
                    precioPlato5.setText("$"+carta.getString("precio"));
                    fotofileplato5 = carta.getParseFile("fotoplato");
                    fotofileplato5.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            final BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 4;
                            Bitmap  pic5 = BitmapFactory.decodeByteArray(data, 0, data.length,options);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            pic5.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            fotoPlato5.setImageBitmap(pic5);
                        }
                    });
                    relativelayoutPlato5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            carta.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {

                                        Intent intent = new Intent(getActivity(), cartaDetalle_delacalleactivity.class);
                                        intent.putExtra("id", carta.getObjectId());
                                        getActivity().startActivity(intent);
                                    } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                        Log.d("delacalle", "no se puede guardar la carta");
                                    }
                                }
                            });
                        }
                    });

                } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                    Log.d("delacalle", "Carta no encontrada");
                }
            }
        });

        ParseQuery<ParseObject>  cartaquery6 = ParseQuery.getQuery("carta");
        cartaquery6.whereEqualTo("restauranteId",id);
        cartaquery6.setSkip(5);
        cartaquery6.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject carta, ParseException e) {
                if (e == null) {
                    nombrePlato6.setText(carta.getString("nombre").toUpperCase());
                    descripcionPlato6.setText(carta.getString("descripcion"));
                    precioPlato6.setText("$"+carta.getString("precio"));

                    fotofileplato6 = carta.getParseFile("fotoplato");
                    fotofileplato6.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            final BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 4;
                            Bitmap  pic6 = BitmapFactory.decodeByteArray(data, 0, data.length,options);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            pic6.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                            fotoPlato6.setImageBitmap(pic6);
                        }
                    });
                    relativelayoutPlato6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            carta.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {

                                        Intent intent = new Intent(getActivity(), cartaDetalle_delacalleactivity.class);
                                        intent.putExtra("id", carta.getObjectId());
                                        getActivity().startActivity(intent);
                                    } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                        Log.d("delacalle", "no se puede guardar la carta");
                                    }
                                }
                            });
                        }
                    });

                } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                    Log.d("delacalle", "Carta no encontrada");
                }
            }
        });

}catch(Exception e)
{
    e.getStackTrace();
    Log.d("delacalle", "error en mostrar carta detalle");
}
        return view;
    }


}
