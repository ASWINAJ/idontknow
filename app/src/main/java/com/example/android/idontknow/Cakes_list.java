package com.example.android.idontknow;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;


/**
 * Created by aswin on 30/3/16.
 */
public class Cakes_list extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Animation.AnimationListener {

    private DrawerLayout drawerLayout;
    private ImageView birthday_cake;
    private ImageView wedding_cake;
    private ImageView fondant;
    private ImageView baby;
    private Animation animation_1;
    private Animation animation_2;
    private Animation animation_3;
    private Animation animation_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cake_initial);

        animation_1 = AnimationUtils.loadAnimation(this, R.anim.button_rotate);
        animation_2 = AnimationUtils.loadAnimation(this,R.anim.button_rotate2);
        animation_3 = AnimationUtils.loadAnimation(this,R.anim.button_rotate);
        animation_4 = AnimationUtils.loadAnimation(this,R.anim.button_rotate2);

        //animation_left.setRepeatCount(0);
        //animation_right.setRepeatCount(0);

        animation_1.setAnimationListener(this);
        animation_2.setAnimationListener(this);
        animation_3.setAnimationListener(this);
        animation_4.setAnimationListener(this);

        birthday_cake = (ImageView)findViewById(R.id.birthday_cake_image);
        wedding_cake = (ImageView)findViewById(R.id.wedding_cake_image);
        fondant =(ImageView)findViewById(R.id.fondant_image);
        baby = (ImageView)findViewById(R.id.baby_image);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLUE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.initial_toolbar);
        if(toolbar!=null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(false);

        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(Cakes_list.this);


    }

    public void click_cake(View view){

        if(view.getId() == R.id.wedding_cake_layout){

            wedding_cake.startAnimation(animation_1);

        }

        else if(view.getId() == R.id.birthday_cake_layout){

            birthday_cake.startAnimation(animation_2);
        }

        else if(view.getId() == R.id.fondant_layout){

            fondant.startAnimation(animation_3);


        }

        else if(view.getId() == R.id.baby_layout){

            baby.startAnimation(animation_4);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Cakes_list.this,Initial.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.signout){
            SignUp.setKeys(this,null,null);
            Intent i = new Intent(Cakes_list.this,SignIn.class);
            startActivity(i);
            finish();
        }

        Toast.makeText(Cakes_list.this,item.getTitle(),Toast.LENGTH_SHORT).show();

        return true;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        String a;
        boolean bool = isNetworkAvailable();
        if(bool == true)
            a = "true";
        else
            a= "false";


        if(animation.equals(animation_1)) {
            Toast.makeText(Cakes_list.this, "this is the wedding" + a, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Cakes_list.this,Wedding_cake.class);
            startActivity(i);
        }
        else if(animation.equals(animation_2)) {
            Toast.makeText(Cakes_list.this, "thisis the birthday", Toast.LENGTH_SHORT).show();
        }
        else if(animation.equals(animation_3)) {
            Toast.makeText(Cakes_list.this, "this is the fondant", Toast.LENGTH_SHORT).show();
        }
        else if(animation.equals(animation_4)) {
            Toast.makeText(Cakes_list.this, "this is the baby", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_history){
            Intent i = new Intent(Cakes_list.this,History.class);
            startActivity(i);
        }
            return true;
    }


    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
