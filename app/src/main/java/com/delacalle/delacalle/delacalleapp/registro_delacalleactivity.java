package com.delacalle.delacalle.delacalleapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.Locale;

public class registro_delacalleactivity extends AppCompatActivity {

    private TextView txtUserName;
    private TextView txtUserEmail;
    private TextView txtUserPass;
    private TextView txtUserRePass;
    private Button buttonSignUpEmail;
    private Button buttonLinkTologinEmail;

    private String userName;
    private String userEmail;
    private String userPass;
    private String userRePass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_delacalleactivity);

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
   //     final Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);


        txtUserName = (TextView) findViewById(R.id.editTextnombreregistro);
        txtUserEmail = (TextView) findViewById(R.id.editTextemailregistro);
        txtUserPass = (TextView) findViewById(R.id.editTextpassregistro);
        txtUserRePass = (TextView) findViewById(R.id.editTextrepassregistro);
        buttonSignUpEmail = (Button) findViewById(R.id.btnregistrarse);
        buttonLinkTologinEmail = (Button) findViewById(R.id.buttonenlacealogin);

       // ParseUser currentUser = ParseUser.getCurrentUser();
     //   currentUser.logOut();

        // Sign Up Button
        buttonSignUpEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                signUpErrors(v);
            }
        });

        buttonLinkTologinEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                btnLinkToLoginEmail(v);
            }
        });

    }

    public void btnLinkToLoginEmail(View view)
    {
        Intent intent = new Intent(this,iniciosesion_delacalleactivity.class);
        startActivity(intent);
    }


    public void signUpErrors(View view)
    {
        clearErrors();
        boolean cancel = false;
        View focusView = null;


        userName = txtUserName.getText().toString();
        userEmail = txtUserEmail.getText().toString();
        userPass = txtUserPass.getText().toString();
        userRePass = txtUserRePass.getText().toString();

        if(TextUtils.isEmpty(userName))
        {
            txtUserName.setError("Es necesario escribir el nombre");
            focusView = txtUserName;
            cancel = true;
        }

        // verificar la confirmación del password
        if(TextUtils.isEmpty(userRePass))
        {
            txtUserRePass.setError("Es necesario escribir la contraseña otra vez ");
            focusView = txtUserRePass;
            cancel = true;
        }
        else if (userPass != null && !userRePass.equals(userPass))
        {
            txtUserPass.setError("¡Las contraseñas no coinciden!");
            focusView = txtUserPass;
            cancel = true;
        }

        // verificar si la contraseña es correcta
        if(TextUtils.isEmpty(userPass))
        {
            txtUserPass.setError("Es necesario escribir la contraseña ");
            focusView = txtUserPass;
            cancel = true;
        }
        else if(userPass.length()<6)
        {
            txtUserPass.setError("Es necesario escribir almenos 6 letras");
            focusView = txtUserPass;
            cancel = true;
        }

        // verificar si el correo es valido
        if(TextUtils.isEmpty(userEmail))
        {
            txtUserEmail.setError("Es necesario escribir el correo");
            focusView = txtUserEmail;
            cancel = true;
        }
        else if(!userEmail.contains("@"))
        {
            txtUserEmail.setError("correo invalido");
            focusView = txtUserEmail;
            cancel = true;
        }

        if(cancel)
        {
            focusView.requestFocus();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Guardando ", Toast.LENGTH_SHORT).show();
            signUp(userName.toLowerCase(Locale.getDefault()), userEmail, userPass);
        }


    }

    public void signUp(final String userName,final String userEmail, String userPass)
    {





        final   ParseUser user = new ParseUser();
        user.setUsername(userName);
        user.setPassword(userPass);
        user.setEmail(userEmail);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    ParseACL roleACL = new ParseACL();
                    roleACL.setPublicReadAccess(true);
                    ParseRole role = new ParseRole("usuario", roleACL);
                    role.getUsers().add(ParseUser.getCurrentUser());
                    role.saveInBackground();

                    signUprMsg("registro exitoso, ¡bienvenido!");
                    Toast.makeText(getApplicationContext(), userName + " - " + userEmail, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), menu_pestanas_delacalleactivity
                            .class);
                    startActivity(intent);

                }
                else  {
                    Log.e("Error", "" + e.getMessage());
                    signUprMsg("Error de registro,intenta otra vez");
                }
            }
        });
    }

    private void clearErrors() {
        txtUserEmail.setError(null);
        txtUserName.setError(null);
        txtUserPass.setError(null);
        txtUserRePass.setError(null);
    }

    protected void signUprMsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @SuppressWarnings("deprecation")
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




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro_delacalleactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }}
