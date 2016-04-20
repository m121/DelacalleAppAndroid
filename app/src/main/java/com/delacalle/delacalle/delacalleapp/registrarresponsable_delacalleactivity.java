package com.delacalle.delacalle.delacalleapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class registrarresponsable_delacalleactivity extends AppCompatActivity {

    private Toolbar mToolbar;

    TextView txtnombre;
    TextView txtemail;
    TextView txtclave;
    TextView txtRclave;


    Button btnregistrarresponsable;


    String   nombre;
    String   email;
    String   clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarresponsable_delacalleactivity);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        final Typeface primerfontcandara = Typeface.createFromAsset(getAssets(), "fonts/CandaraBold.ttf");
        final Typeface segundafontcaviar = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtnombre = (TextView) findViewById(R.id.editTextnombreregistroResponsable);
        txtemail = (TextView) findViewById(R.id.editTextemailregistroResponsable);
        txtclave = (TextView) findViewById(R.id.editTextpassregistroResponsable);
        txtRclave = (TextView) findViewById(R.id.editTextrepassregistroResponsable);
        btnregistrarresponsable = (Button) findViewById(R.id.btnregistrarResponsable);


        btnregistrarresponsable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombre = txtnombre.getText().toString();
                email = txtemail.getText().toString();
                clave = txtclave.getText().toString();

                ParseUser user = new ParseUser();
                user.setUsername(email);
                user.setEmail(email);
                user.setPassword(clave);
                user.put("nombre", nombre);

                user.signUpInBackground(new SignUpCallback() {

                    @Override

                    public void done(ParseException e) {
                        if (e == null) {

                            ParseQuery<ParseRole> roleq = ParseRole.getQuery();
                            roleq.whereEqualTo("name", "responsable");
                            roleq.getFirstInBackground(new GetCallback<ParseRole>() {
                                @Override
                                public void done(ParseRole rol, ParseException e) {
                                    if (e == null) {
                                        rol.getUsers().add(ParseUser.getCurrentUser());
                                        rol.saveInBackground();

                                        Log.d("delacalle", "usuario agregado al rol responsable");
                                        Toast.makeText(getApplicationContext(), "Registrado", Toast.LENGTH_SHORT).show();
                                    } else if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                                        ParseACL roleACL = new ParseACL();
                                        roleACL.setPublicReadAccess(true);
                                        roleACL.setPublicWriteAccess(true);
                                        ParseRole role = new ParseRole("responsable", roleACL);
                                        role.getUsers().add(ParseUser.getCurrentUser());
                                        role.saveInBackground();

                                        Toast.makeText(getApplicationContext(), "Registrado", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                            Log.d("delacalle", "responsable registrado");


                        } else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                ParseUser.getCurrentUser().logOut();
                Intent intent = new Intent(registrarresponsable_delacalleactivity.this,iniciosesion_delacalleactivity.class);
                startActivity(intent);
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registrarresponsable_delacalleactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cerrar) {
            ParseUser.getCurrentUser().logOut();
            Intent intent = new Intent(registrarresponsable_delacalleactivity.this,iniciosesion_delacalleactivity.class);
            startActivity(intent);

        }

        if(id == R.id.action_menu)
        {
            Intent intent = new Intent(registrarresponsable_delacalleactivity.this,menu_pestanas_delacalleactivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
