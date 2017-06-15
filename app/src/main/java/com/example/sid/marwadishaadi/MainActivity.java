package com.example.sid.marwadishaadi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.example.sid.marwadishaadi.Intro_Slides.Intro1Fragment;
import com.example.sid.marwadishaadi.Intro_Slides.Intro2Fragment;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppIntro2 implements Intro1Fragment.OnFragmentInteractionListener,Intro2Fragment.OnFragmentInteractionListener{

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(new Intro1Fragment());
        addSlide(new Intro2Fragment());
        showStatusBar(false);

        // Hide Skip/Done button.
        showSkipButton(false);

        setProgressButtonEnabled(true);

        setFadeAnimation();

        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        SharedPreferences sharedpref=getSharedPreferences("userinfo",MODE_PRIVATE);
        boolean check = sharedpref.getBoolean("isLoggedIn",false);

        Log.d(":", "onDonePressed:--------------------------- bool is  "+check);
        if(check){
            Intent i = new Intent(MainActivity.this, Dashboard.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
        else {
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
