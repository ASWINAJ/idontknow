package com.example.android.idontknow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by aswin on 29/3/16.
 */
public class Initial extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    int back = 0;

    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initialpage);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);




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
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        back++;

        Toast.makeText(Initial.this,"Press again if u want to exit",Toast.LENGTH_SHORT).show();

        if(back ==2) {
            moveTaskToBack(true);
            finish();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.signout){
            SignUp.setKeys(this,null,null);
            Intent i = new Intent(Initial.this,SignIn.class);
            startActivity(i);
            finish();
        }

        Toast.makeText(Initial.this,item.getTitle(),Toast.LENGTH_SHORT).show();

        return true;
    }

    public void click(View view){
        if(view.getId() == R.id.cakes_button){
            Intent i = new Intent(Initial.this,Cakes_list.class);
            startActivity(i);
            finish();

        }else if(view.getId() == R.id.muffins_button){
            Intent i = new Intent(Initial.this,Muffin_list.class);
            startActivity(i);
            finish();

        }else if(view.getId() == R.id.party_button){
            Intent i = new Intent(Initial.this,Party_list.class);
            startActivity(i);
            finish();

        }

    }


}
