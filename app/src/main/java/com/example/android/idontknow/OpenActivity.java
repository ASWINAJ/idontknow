package com.example.android.idontknow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by aswin on 29/2/16.
 */
public class OpenActivity extends AppCompatActivity implements Animation.AnimationListener {

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

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        String email = SignUp.getUsername(this);
        String pass = SignUp.getKeyPass(this);

        int fine = 0;

        if((email!=null) && (pass!=null)) {
            fine = 1;
        }

        final Intent mainIntent = new Intent(OpenActivity.this, User.class);
        final Intent mainIntent2 = new Intent(OpenActivity.this,Initial.class);

        final int finalFine = fine;


        if (finalFine == 0)
            startActivity(mainIntent);
        else
            startActivity(mainIntent2);
        finish();



        Toast.makeText(OpenActivity.this,"this is what i do",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {


    }
}
