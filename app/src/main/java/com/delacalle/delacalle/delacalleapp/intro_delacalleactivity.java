package com.delacalle.delacalle.delacalleapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class intro_delacalleactivity extends AppIntro2 {

    // Please DO NOT override onCreate. Use init.
    @Override
    public void init(Bundle savedInstanceState) {

        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if(pref.getBoolean("activity_executed", false)){
            Intent intent = new Intent(this, iniciosesion_delacalleactivity.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("activity_executed", true);
            ed.commit();
        }


        // Add your slide's fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(AppIntroFragment.newInstance("Tutorial", "Te explicamos como usar el club de la calle", R.drawable.tuto,  Color.rgb(26,26,26)));
        addSlide(AppIntroFragment.newInstance("Contacto", "Contactános por correo, por Whatsapp, por Facebook o por delacalle.co", R.drawable.tuto1, Color.rgb(26,26,26)));
        addSlide(AppIntroFragment.newInstance("¿Cuanto cuesta?", "Pagas solamente $5.000 pesos por un mes", R.drawable.tuto3, Color.rgb(26,26,26)));
        addSlide(AppIntroFragment.newInstance("¿Que beneficios obtienes?", "¡Todo el mes recibes descuentos de nuestros establecimientos aliados! ", R.drawable.tuto4, Color.rgb(26,26,26)));
        addSlide(AppIntroFragment.newInstance("", "", R.drawable.tuto5, Color.rgb(26,26,26)));
        addSlide(AppIntroFragment.newInstance("¿Como es la membresía?", "La membresía es una tarjeta con el logo del club De La Calle ", R.drawable.tuto6, Color.rgb(26,26,26)));
        addSlide(AppIntroFragment.newInstance("¿Como buscas ofertas?", "Puedes buscar las ofertas usando nuestra app o visitando la web delacalle.co ", R.drawable.tuto7, Color.rgb(26,26,26)));
        addSlide(AppIntroFragment.newInstance("Presentar membresía", "Ve a un establecimiento, presentas la membresía delacalle y obtendrás tu descuento.¡Hazlo ya! ", R.drawable.tuto8, Color.rgb(26,26,26)));
  //      addSlide(AppIntroFragment.newInstance(title, description, image, background_colour));
      //  addSlide(third_fragment);
     //   addSlide(fourth_fragment);

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
   //     addSlide(AppIntroFragment.newInstance(title, description, image, background_colour));

        // OPTIONAL METHODS
        // Override bar/separator color.
     //   setBarColor(Color.parseColor("#3F51B5"));
       // setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
      //  showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
        setVibrate(false);
        setVibrateIntensity(30);
    }

   /* @Override
    public void onSkipPressed() {
        // Do something when users tap on Skip button.
        Intent intent = new Intent(intro_delacalleactivity.this,iniciosesion_delacalleactivity.class);
        startActivity(intent);
    }*/

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
        Intent intent = new Intent(intro_delacalleactivity.this,iniciosesion_delacalleactivity.class);
        startActivity(intent);
    }

    @Override
    public void onSlideChanged() {
        // Do something when the slide changes.
    }

    @Override
    public void onNextPressed() {
        // Do something when users tap on Next button.
    }
}
