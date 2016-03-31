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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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
import org.json.JSONObject;
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

 //   private LoginButton loginButton;

    private ImageView loginfacebook;
    private ImageView logininstagram;


    private TextView txtUserName;
    private TextView txtUserPass;
    private Button  btnLogInEmail;
    private Button btnLinkTosignupEmail;
    private Button btnLinkToResetPass;
    private Button btnlinkregistrar;


    private String userName;
    private String userPass;


    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;
    CallbackManager callbackManager;

    String email;
    String nombre;

    public static final String CLIENT_ID = "your client id";
    public static final String CLIENT_SECRET = "your client secret";
    public static final String CALLBACK_URL = "redirect uri here";






    Button buttoniniciarsesion;

    public static final List<String> mPermissions = new ArrayList<String>() {{
        add("public_profile");
        add("email");
    }};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(iniciosesion_delacalleactivity.this);
        setContentView(R.layout.activity_iniciosesion_delacalleactivity);

       /* // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }*/


        /*callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(getApplicationContext(), "Estas dentro", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });*/

     //   loginButton = (LoginButton) findViewById(R.id.login_button);

     //   FacebookSdk.sdkInitialize(this.getApplicationContext());

       /* accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };

        accessToken = AccessToken.getCurrentAccessToken();
     //*/

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        try {
            FacebookSdk.sdkInitialize(this);
            ParseFacebookUtils.initialize(this);
            ParseTwitterUtils.initialize("xKhBquBjUbGas2xxna19spCYS", "qWtUR7dq9U0deJ8BiRQx2UZ79VNgG6agI1l7YD4uCX4QpEQ0cC");

        }catch(Exception e)
        {
e.printStackTrace();
        }
//**
        // Obtener el KeyHash que piden cuando agregas un dispositivo a tu app en Facebook, el KeyHash se puede ver en los logs
       /* try {
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
        loginfacebook = (ImageView) findViewById(R.id.btnSignInFacebook);
        logininstagram = (ImageView) findViewById(R.id.btnSignInInstagram);


        txtUserName = (TextView) findViewById(R.id.editTextnombreiniciarsesion);
        txtUserPass = (TextView) findViewById(R.id.editTextcontrasenainiciarsesion);



        btnLogInEmail = (Button) findViewById(R.id.btniniciarsesion);
        btnlinkregistrar = (Button) findViewById(R.id.btnlinkregistrar);
        //    btnLinkTosignupEmail = (Button) layout.findViewById(R.id.btnLinkToSignUp);
        //    btnLinkToResetPass = (Button) layout.findViewById(R.id.btnLinkToresetpass);

        //     final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        //     final Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        loginfacebook.setClickable(true);
        logininstagram.setClickable(true);



        btnLogInEmail.setOnClickListener(new View.OnClickListener() {

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


        loginfacebook.setOnClickListener(new View.OnClickListener() {
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

             /*   ParseUser.becomeInBackground("f5b05e1ce25f9c1a70bd000d212fb3de", new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            Intent intent = new Intent(iniciosesion_delacalleactivity.this, menu_pestanas_delacalleactivity.class);
                            startActivity(intent);
                            // The current user is now set to user.
                        } else {
                            // The token could not be validated.
                            Log.d("De La Calle", "Error");
                        }
                    }
                });*/
            }
        });

        logininstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
            }
        });



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
       //    ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
         /* if (requestCode == 32665) {
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);


        }*/

        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);

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

    // Ocultar el teclado
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
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




    /*GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(JSONObject user, GraphResponse response) {
            if (user != null) {
             //   profilePictureView.setProfileId(user.optString("id"));
                email = user.optString("email");
                nombre = user.optString("name");

            }
        }
        Bundle parameters = new Bundle();

    }).executeAsync();
*/

    /*InstagramApp mApp; = new InstagramApp(this,
                                          ApplicationData.CLIENT_ID,
                                          ApplicationData.CLIENT_SECRET,
                                          ApplicationData.CALLBACK_URL);*/



    public class ApplicationData {
        public static final String CLIENT_ID = "";
        public static final String CLIENT_SECRET = "";
        public static final String CALLBACK_URL = "";
    }

}
