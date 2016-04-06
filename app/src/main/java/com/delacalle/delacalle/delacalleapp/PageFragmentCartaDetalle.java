package com.delacalle.delacalle.delacalleapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class PageFragmentCartaDetalle extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private TextView nombrePlato1;
    private TextView descripcionPlato1;
    private TextView precioPlato1;

    private TextView nombrePlato2;
    private TextView descripcionPlato2;
    private TextView precioPlato2;

    private TextView nombrePlato3;
    private TextView descripcionPlato3;
    private TextView precioPlato3;

    private TextView nombrePlato4;
    private TextView descripcionPlato4;
    private TextView precioPlato4;

    private TextView nombrePlato5;
    private TextView descripcionPlato5;
    private TextView precioPlato5;

    private TextView nombrePlato6;
    private TextView descripcionPlato6;
    private TextView precioPlato6;


    private RelativeLayout relativelayoutPlato1;
    private RelativeLayout relativelayoutPlato2;
    private RelativeLayout relativelayoutPlato3;
    private RelativeLayout relativelayoutPlato4;
    private RelativeLayout relativelayoutPlato5;
    private RelativeLayout relativelayoutPlato6;

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


          nombrePlato1 = (TextView) view.findViewById(R.id.textViewPlatoCarta1);
          descripcionPlato1 = (TextView) view.findViewById(R.id.textViewDescripcionCarta1);
          precioPlato1 = (TextView) view.findViewById(R.id.textViewPrecioCarta1);

        nombrePlato2 = (TextView) view.findViewById(R.id.textViewPlatoCarta2);
        descripcionPlato2 = (TextView) view.findViewById(R.id.textViewDescripcionCarta2);
        precioPlato2 = (TextView) view.findViewById(R.id.textViewPrecioCarta2);

        nombrePlato3 = (TextView) view.findViewById(R.id.textViewPlatoCarta3);
        descripcionPlato3 = (TextView) view.findViewById(R.id.textViewDescripcionCarta3);
        precioPlato3 = (TextView) view.findViewById(R.id.textViewPrecioCarta3);

        nombrePlato4 = (TextView) view.findViewById(R.id.textViewPlatoCarta4);
        descripcionPlato4 = (TextView) view.findViewById(R.id.textViewDescripcionCarta4);
        precioPlato4 = (TextView) view.findViewById(R.id.textViewPrecioCarta4);

        nombrePlato5 = (TextView) view.findViewById(R.id.textViewPlatoCarta5);
        descripcionPlato5 = (TextView) view.findViewById(R.id.textViewDescripcionCarta5);
        precioPlato5 = (TextView) view.findViewById(R.id.textViewPrecioCarta5);

        nombrePlato6 = (TextView) view.findViewById(R.id.textViewPlatoCarta6);
        descripcionPlato6 = (TextView) view.findViewById(R.id.textViewDescripcionCarta6);
        precioPlato6 = (TextView) view.findViewById(R.id.textViewPrecioCarta6);


        relativelayoutPlato1 = (RelativeLayout) view.findViewById(R.id.relativeLayoutPlato1);
        relativelayoutPlato2 = (RelativeLayout) view.findViewById(R.id.relativeLayoutPlato2);
        relativelayoutPlato3 = (RelativeLayout) view.findViewById(R.id.relativeLayoutPlato3);
        relativelayoutPlato4 = (RelativeLayout) view.findViewById(R.id.relativeLayoutPlato4);
        relativelayoutPlato5 = (RelativeLayout) view.findViewById(R.id.relativeLayoutPlato5);
        relativelayoutPlato6 = (RelativeLayout) view.findViewById(R.id.relativeLayoutPlato6);

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
                        }
                    });
                }

            }
        });
/*


        ParseQuery<ParseObject>  cartaquery = ParseQuery.getQuery("carta");
        cartaquery.whereEqualTo("restauranteId", id);
        cartaquery.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject carta, ParseException e) {
                if (e == null) {
                    nombrePlato1.setText(carta.getString("nombre"));
                    descripcionPlato1.setText(carta.getString("descripcion"));
                    precioPlato1.setText("$" + carta.getString("precio"));
                    relativelayoutPlato1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            carta.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {

                                        Intent intent = new Intent(getActivity(), cartaDetalle_delacalleactivity.class);
                                        intent.putExtra("id", id);
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
                    nombrePlato2.setText(carta.getString("nombre"));
                    descripcionPlato2.setText(carta.getString("descripcion"));
                    precioPlato2.setText("$"+carta.getString("precio"));
                    relativelayoutPlato2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            carta.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {

                                        Intent intent = new Intent(getActivity(), cartaDetalle_delacalleactivity.class);
                                        intent.putExtra("id", id);
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
                    nombrePlato3.setText(carta.getString("nombre"));
                    descripcionPlato3.setText(carta.getString("descripcion"));
                    precioPlato3.setText("$"+carta.getString("precio"));
                    relativelayoutPlato3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            carta.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {

                                        Intent intent = new Intent(getActivity(), cartaDetalle_delacalleactivity.class);
                                        intent.putExtra("id", id);
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
                    nombrePlato4.setText(carta.getString("nombre"));
                    descripcionPlato4.setText(carta.getString("descripcion"));
                    precioPlato4.setText("$"+carta.getString("precio"));
                    relativelayoutPlato4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            carta.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {

                                        Intent intent = new Intent(getActivity(), cartaDetalle_delacalleactivity.class);
                                        intent.putExtra("id", id);
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
                    nombrePlato5.setText(carta.getString("nombre"));
                    descripcionPlato5.setText(carta.getString("descripcion"));
                    precioPlato5.setText("$"+carta.getString("precio"));
                    relativelayoutPlato5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            carta.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {

                                        Intent intent = new Intent(getActivity(), cartaDetalle_delacalleactivity.class);
                                        intent.putExtra("id", id);
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
                    nombrePlato6.setText(carta.getString("nombre"));
                    descripcionPlato6.setText(carta.getString("descripcion"));
                    precioPlato6.setText("$"+carta.getString("precio"));
                    relativelayoutPlato6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            carta.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {

                                        Intent intent = new Intent(getActivity(), cartaDetalle_delacalleactivity.class);
                                        intent.putExtra("id", id);
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
*/
        return view;
    }


}
