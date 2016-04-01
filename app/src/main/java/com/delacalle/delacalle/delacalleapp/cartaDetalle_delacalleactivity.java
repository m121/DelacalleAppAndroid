package com.delacalle.delacalle.delacalleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class cartaDetalle_delacalleactivity extends AppCompatActivity {

    private ImageView fotologoCartaDetalle;
    private ImageView fotorestauranteCartaDetalle;
    private TextView  nombrerestauranteCartaDetalle;
    private TextView  nombreplatoCartaDetalle;
    private TextView  descripcionplatoCartaDetalle;
    private TextView  precioplatoCartaDetalle;

    private Toolbar mToolbar;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta_detalle_delacalleactivity);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


          fotologoCartaDetalle = (ImageView) findViewById(R.id.imageViewLogoDetalleCarta);
          fotorestauranteCartaDetalle = (ImageView) findViewById(R.id.imageViewFotoRestauranteDetalleCarta);
          nombrerestauranteCartaDetalle = (TextView) findViewById(R.id.textViewNombreRestauranteDetalleCarta);
          nombreplatoCartaDetalle = (TextView) findViewById(R.id.textViewNombrePlatoRestauranteDetalleCarta);
          descripcionplatoCartaDetalle = (TextView) findViewById(R.id.textViewDescripcionPlatoResturanteDetalleCarta);
          precioplatoCartaDetalle = (TextView) findViewById(R.id.textViewPrecioPlatoRestauranteDetalleCarta);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");

        }
        else
        {
            Log.d("delacalle", "Error al pasar el id " + id);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carta_detalle_delacalleactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

       /* //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        // or call onBackPressed()
        return true;
    }
}
