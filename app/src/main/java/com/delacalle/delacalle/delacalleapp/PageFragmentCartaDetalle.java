package com.delacalle.delacalle.delacalleapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


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
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_fragment_carta_detalle ,container, false);


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

        return view;
    }


}