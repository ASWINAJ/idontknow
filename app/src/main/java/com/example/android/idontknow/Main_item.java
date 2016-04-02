package com.example.android.idontknow;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.io.Serializable;

/**
 * Created by aswin on 1/4/16.
 */
public class Main_item extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,Serializable {
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private NetworkImageView networkImageView1;
    private NetworkImageView networkImageView2;
    private NetworkImageView networkImageView3;
    private NetworkImageView networkImageView4;
    private NetworkImageView networkImageView5;



    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_item_initial);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        networkImageView1 = (NetworkImageView)findViewById(R.id.main_image_1);
        networkImageView2 = (NetworkImageView)findViewById(R.id.main_image_2);
        networkImageView3 = (NetworkImageView)findViewById(R.id.main_image_3);
        networkImageView4 = (NetworkImageView)findViewById(R.id.main_image_4);
        networkImageView5 = (NetworkImageView)findViewById(R.id.main_image_5);

        Intent i = getIntent();

         Item item = (Item)i.getExtras().getSerializable("MyClass");
        Toast.makeText(Main_item.this,"asee "+item.getItemname(),Toast.LENGTH_SHORT).show();

       networkImageView1.setImageUrl(item.getThumbnailUrl1(), imageLoader);
        networkImageView2.setImageUrl(item.getThumbnailUrl1(),imageLoader);
        networkImageView3.setImageUrl(item.getThumbnailUrl1(),imageLoader);
        networkImageView4.setImageUrl(item.getThumbnailUrl1(),imageLoader);
        networkImageView5.setImageUrl(item.getThumbnailUrl1(),imageLoader);


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
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Main_item.this,Wedding_cake.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.signout){
            SignUp.setKeys(this,null,null);
            Intent i = new Intent(Main_item.this,SignIn.class);
            startActivity(i);
            finish();
        }

        Toast.makeText(Main_item.this, item.getTitle(), Toast.LENGTH_SHORT).show();

        return true;
    }
}
