package com.example.android.idontknow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by aswin on 29/2/16.
 */
public class OpenActivity extends AppCompatActivity implements Animation.AnimationListener {

    private boolean aBoolean=false;
    private ImageView imageView;
    Animation myFadeInAnimation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openactivity);


        imageView= (ImageView)findViewById(R.id.muffin_image);
        myFadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        imageView.setVisibility(View.VISIBLE);
        imageView.startAnimation(myFadeInAnimation);
        myFadeInAnimation.setAnimationListener(this);



    }

    @Override
    protected void onPause() {
        super.onPause();
        imageView.clearAnimation();

    }

    public void action(){

        new Thread(new Runnable(){

            @Override
            public void run() {

                if (isNetworkAvailable()) {
                    try {
                        HttpURLConnection urlc = (HttpURLConnection) (new URL("http://clients3.google.com/generate_204").openConnection());
                        urlc.setRequestProperty("User-Agent", "Test");
                        urlc.setRequestProperty("Connection", "close");
                        urlc.setConnectTimeout(3500);
                        urlc.connect();
                        aBoolean = (urlc.getResponseCode() == 204 && urlc.getContentLength() == 0);
                    } catch (IOException e) {
                        Log.e("ERROR", "Error checking internet connection", e);
                    }
                } else {
                    Log.d("ERROR", "No network available!");
                }

            }
        }).start();
    }

    @Override
    public void onAnimationStart(Animation animation) {
        action();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        String email = SignUp.getUsername(this);
        String pass = SignUp.getKeyPass(this);

        int fine = 0;

        String a;

        if(aBoolean == true)
            a="network is true";
        else
            a="network is false";

        Toast.makeText(OpenActivity.this,a,Toast.LENGTH_SHORT).show();

        if(aBoolean == false)
        {
            Intent i = new Intent(OpenActivity.this,Nointernet.class);
            startActivity(i);
            finish();
        }
        else {


            if ((email != null) && (pass != null)) {
                fine = 1;
            }

            final Intent mainIntent = new Intent(OpenActivity.this, User.class);
            final Intent mainIntent2 = new Intent(OpenActivity.this, Initial.class);

            final int finalFine = fine;


            if (finalFine == 0)
                startActivity(mainIntent);
            else
                startActivity(mainIntent2);
            finish();

        }



        Toast.makeText(OpenActivity.this,"this is what i do",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {


    }



}
