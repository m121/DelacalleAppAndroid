package com.delacalle.delacalle.delacalleapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class resetearcontrasenadesdelogin_delacalleactivity extends AppCompatActivity {

    private EditText edittextemail;
    private Button buttonresetpass;
    private ImageButton buttonlinktomenulogin;



    private String textemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetearcontrasenadesdelogin_delacalleactivity);



        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        //      final Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);

        Typeface primerfontcandara = Typeface.createFromAsset(getAssets(),"fonts/CandaraBold.ttf");
        Typeface segundafontcaviar = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");

        edittextemail = (EditText) findViewById(R.id.edittextcorreoresetearcontrasena);
        buttonresetpass = (Button) findViewById(R.id.btnresetearcontrasena);
        buttonlinktomenulogin = (ImageButton) findViewById(R.id.btniramenuinicio);

        edittextemail.setTypeface(segundafontcaviar);
        buttonresetpass.setTypeface(primerfontcandara);
      //  buttonlinktomenulogin.setTypeface(primerfontcandara);


        buttonresetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                resetpasserrors(v);
                resetpass();


            }
        });


        buttonlinktomenulogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Intent intent = new Intent(getApplication(), iniciosesion_delacalleactivity.class);
                startActivity(intent);

            }
        });



    }

    // Reset Pass
    public void resetpass()
    {
        try {
            ParseUser.requestPasswordResetInBackground(textemail, new RequestPasswordResetCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        // An email was successfully sent with reset instructions.
                        showAlertDialog(resetearcontrasenadesdelogin_delacalleactivity.this, "Resetear contraseña", "Un email será enviado con instrucciones", false);
                        Intent intent = new Intent(resetearcontrasenadesdelogin_delacalleactivity.this, iniciosesion_delacalleactivity.class);
                        startActivity(intent);
                    } else {
                        // Something went wrong. Look at the ParseException to see what's up.
                        Toast.makeText(getApplicationContext(), "No existe el correo  " + textemail, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e)
        {
            e.getStackTrace();
            Log.d("delacalle","error en resetear contrasena");
        }
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);



        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public void resetpasserrors(View view)
    {
        clearErrors();
        textemail = edittextemail.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(textemail))
        {
            edittextemail.setError("Es necesario escribir el correo");
            focusView = edittextemail;
            cancel = true;
        }

        if(cancel)
        {
            focusView.requestFocus();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Reseteando contraseña a  " + textemail, Toast.LENGTH_SHORT).show();
        }


    }

    public void clearErrors()
    {
        edittextemail.setError(null);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resetearcontrasena_delacalleactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    // Ocultar el teclado
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
