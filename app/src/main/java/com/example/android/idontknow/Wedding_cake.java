package com.example.android.idontknow;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aswin on 31/3/16.
 */
public class Wedding_cake extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String url = "http://athena.nitc.ac.in/aswin_b130736cs/mine.json";
    private ArrayList<Item> Itemlist  = new ArrayList<Item>();
    private GridView listView;
    private CustomListAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wedding_cake_initial);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        listView = (GridView) findViewById(R.id.list);


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

        // Creating volley request obj
        action();

    }

    public void action(){

        Toast.makeText(Wedding_cake.this,"im in action",Toast.LENGTH_SHORT).show();
        JsonArrayRequest itemreq =new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0;i< response.length();i++){
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Item item = new Item();
                                item.setItemname(obj.getString("title"));
                                item.setThumbnailUrl(obj.getString("image"));

                                Itemlist.add(item);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Wedding_cake.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.signout){
            SignUp.setKeys(this,null,null);
            Intent i = new Intent(Wedding_cake.this,SignIn.class);
            startActivity(i);
            finish();
        }


        Toast.makeText(Wedding_cake.this,item.getTitle(),Toast.LENGTH_SHORT).show();

        return true;
    }
}
