package com.delacalle.delacalle.delacalleapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFacebookUtils;
import com.facebook.AccessToken;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class iniciosesion_delacalleactivity extends AppCompatActivity {


    private Button logInwithEmail;
    private Button loginwithFace;
    private Button loginwithGmail;
    private Button loginwithTwitter;


    private TextView txtUserName;
    private TextView txtUserPass;
    private Button  btnLogInEmail;
    private Button btnLinkTosignupEmail;
    private Button btnLinkToResetPass;
    private Button btnlinkregistrar;


    private String userName;
    private String userPass;



    Button buttoniniciarsesion;

    public static final List<String> mPermissions = new ArrayList<String>() {{
        add("public_profile");
        add("email");
    }};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesion_delacalleactivity);

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        try {
            FacebookSdk.sdkInitialize(this);
            ParseFacebookUtils.initialize(this);
            ParseTwitterUtils.initialize("xKhBquBjUbGas2xxna19spCYS", "qWtUR7dq9U0deJ8BiRQx2UZ79VNgG6agI1l7YD4uCX4QpEQ0cC");

        }catch(Exception e)
        {
e.printStackTrace();
        }
/*
        // Obtener el KeyHash que piden cuando agregas un dispositivo a tu app en Facebook, el KeyHash se puede ver en los logs
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.delacalle.delacalle.delacalleapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }*/

        buttoniniciarsesion = (Button) findViewById(R.id.btniniciarsesion);
      //  loginwithFace = (Button) findViewById(R.id.btnSignInFacebook);
    //    loginwithTwitter = (Button) findViewById(R.id.btnSignInTwitter);
     //   btnLinkToResetPass = (Button) findViewById(R.id.btniraresetearcontrasena);

        txtUserName = (TextView) findViewById(R.id.editTextnombreiniciarsesion);
        txtUserPass = (TextView) findViewById(R.id.editTextcontrasenainiciarsesion);

        btnLogInEmail = (Button) findViewById(R.id.btniniciarsesion);
        btnlinkregistrar = (Button) findViewById(R.id.btnlinkregistrar);
        //    btnLinkTosignupEmail = (Button) layout.findViewById(R.id.btnLinkToSignUp);
        //    btnLinkToResetPass = (Button) layout.findViewById(R.id.btnLinkToresetpass);

        //     final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        //     final Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);

        btnLogInEmail.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                logInEmailErrors(v);

            }
        });

        btnlinkregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Intent intent = new Intent(iniciosesion_delacalleactivity.this,registro_delacalleactivity.class);
                startActivity(intent);
            }
        });


        buttoniniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
              //  displayPopupWindowPhotos(v);
                logInEmailErrors(v);
            }
        });


       /* loginwithFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                ParseFacebookUtils.logInWithReadPermissionsInBackground(iniciosesion_delacalleactivity.this, mPermissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            //   Log.d("myapp",err.getLocalizedMessage());
                            Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                        } else if (user.isNew()) {
                            ParseACL roleACL = new ParseACL();
                            roleACL.setPublicReadAccess(true);
                            ParseRole role = new ParseRole("usuario", roleACL);
                            role.getUsers().add(ParseUser.getCurrentUser());
                            role.saveInBackground();
                            Intent intent = new Intent(iniciosesion_delacalleactivity.this, menu_pestanas_delacalleactivity.class);
                            startActivity(intent);
                            Log.d("MyApp", "User signed up and logged in through Facebook!");
                        } else {
                            Intent intent = new Intent(iniciosesion_delacalleactivity.this, menu_pestanas_delacalleactivity.class);
                            startActivity(intent);
                            Log.d("MyApp", "User logged in through Facebook!");
                        }
                    }
                });
            }
        });*/



       /* loginwithTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                ParseTwitterUtils.logIn(iniciosesion_delacalleactivity.this, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Log.d("MyApp", "Uh oh. The user cancelled the Twitter login.");
                        } else if (user.isNew()) {
                            ParseACL roleACL = new ParseACL();
                            roleACL.setPublicReadAccess(true);
                            ParseRole role = new ParseRole("usuario", roleACL);
                            role.getUsers().add(ParseUser.getCurrentUser());
                            role.saveInBackground();
                            Intent intent = new Intent(iniciosesion_delacalleactivity.this, menu_pestanas_delacalleactivity.class);
                            startActivity(intent);
                            Log.d("MyApp", "User signed up and logged in through Twitter!");
                        } else {
                            Log.d("MyApp", "User logged in through Twitter!");
                            Intent intent = new Intent(iniciosesion_delacalleactivity.this, menu_pestanas_delacalleactivity.class);
                            startActivity(intent);
                        }
                    }
                });

            }
        });*/

/*
        btnLinkToResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Intent intent = new Intent(iniciosesion_delacalleactivity.this,resetearcontrasena_delacalleactivity.class);
                startActivity(intent);
            }
        });
*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //   ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
        //  if (requestCode == 32665) {
   //     ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);

        //}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_iniciosesion_delacalleactivity, menu);
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
    }

    private void displayPopupWindowPhotos(final View anchorView) {
final        PopupWindow popup = new PopupWindow(iniciosesion_delacalleactivity.this);
        View layout = getLayoutInflater().inflate(R.layout.popupiniciarsesioncorreo, null);
        popup.setContentView(layout);


        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        txtUserName = (TextView) layout.findViewById(R.id.editTextnombreiniciarsesion);
        txtUserPass = (TextView) layout.findViewById(R.id.editTextcontrasenainiciarsesion);

        btnLogInEmail = (Button) layout.findViewById(R.id.btniniciarsesion);
        btnlinkregistrar = (Button) layout.findViewById(R.id.btnlinkregistrar);
    //    btnLinkTosignupEmail = (Button) layout.findViewById(R.id.btnLinkToSignUp);
    //    btnLinkToResetPass = (Button) layout.findViewById(R.id.btnLinkToresetpass);

   //     final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
   //     final Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);

        btnLogInEmail.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                logInEmailErrors(v);

            }
        });

        btnlinkregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Intent intent = new Intent(iniciosesion_delacalleactivity.this,registro_delacalleactivity.class);
                startActivity(intent);
            }
        });


      /*  btnLinkTosignupEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animTranslate);
                btnLinkToSignUpEmail(v);
            }
        });

        btnLinkToResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animTranslate);
                Intent intent = new Intent(getApplication(), resetpassword_PardonActivity.class);
                startActivity(intent);
            }
        });
*/

        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        // Show anchored to button
        //   popup.setBackgroundDrawable(new BitmapDrawable());
        new Handler().postDelayed(new Runnable() {

            public void run() {
                popup.showAtLocation(anchorView, Gravity.TOP | Gravity.START | Gravity.CENTER_VERTICAL,120, 300);
                popup.showAsDropDown(anchorView);
            }
        },100L);




        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        // Show anchored to button
        //   popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAtLocation(anchorView, Gravity.TOP | Gravity.LEFT, 150, 400);
        popup.showAsDropDown(anchorView);





    }

    public void logInEmailErrors(View view)
    {
        clearErrors();
        userName = txtUserName.getText().toString();
        userPass = txtUserPass.getText().toString();

        boolean cancel = false;
        View focusView = null;

// check if the password is valid
        if(TextUtils.isEmpty(userPass))
        {
            //  txtUserPass.setError("It is necessary to write the password");
            Toast.makeText(getApplicationContext(), "Tienes que ingresar la contraseña ", Toast.LENGTH_SHORT).show();
            focusView = txtUserPass;
            cancel = true;
        }
        else if(userPass.length()<6)
        {
            //  txtUserPass.setError("It is necessary minimum 6 characters of length");
            Toast.makeText(getApplicationContext(), "Tiene que ser un minímo de 6 caracteres", Toast.LENGTH_SHORT).show();
            focusView = txtUserPass;
            cancel = true;

        }

        // check if the nickname is valid
        if(TextUtils.isEmpty(userName))
        {
            // txtUserName.setError("It is necessary write your nickname");
            Toast.makeText(getApplicationContext(), "Tienes que ingresar tu nombre", Toast.LENGTH_SHORT).show();
            focusView = txtUserName;
            cancel = true;
        }

        if(cancel)
        {
            focusView.requestFocus();
        }
        else
        {
            logInEmail(userName.toLowerCase(Locale.getDefault()), userPass);
            Toast.makeText(getApplicationContext(), "Iniciando sesión", Toast.LENGTH_SHORT).show();
        }


    }

    private void logInEmail(String lowercase, String userpass)
    {
        ParseUser.logInInBackground(lowercase, userpass, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    ParseQuery<ParseRole> roleadmin = ParseRole.getQuery();
                    roleadmin.whereEqualTo("name","Administrador");
                    roleadmin.whereEqualTo("users", ParseUser.getCurrentUser().getObjectId());
                    roleadmin.getFirstInBackground(new GetCallback<ParseRole>() {
                        @Override
                        public void done(ParseRole object, ParseException e) {
                            if (e == null) {
                                Intent intent = new Intent(iniciosesion_delacalleactivity.this, registrarresponsable_delacalleactivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                      loginSuccesfull();
                            }
                        }
                    });


                } else {
                    loginFail();
                }
            }
        });
    }

    public void loginSuccesfull()
    {
        Intent intent = new Intent(getApplicationContext(), menu_pestanas_delacalleactivity.class);
        startActivity(intent);

    }

    public void loginFail()
    {
        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
        showAlertDialog(iniciosesion_delacalleactivity.this, "Inicio de sesión con correo", "Tu nombre o contraseña son incorrectos, intenta otra vez", false);

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


    // Posibles errores
    private void clearErrors(){
        txtUserName.setError(null);
        txtUserPass.setError(null);
    }

   /* @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }*/



}
