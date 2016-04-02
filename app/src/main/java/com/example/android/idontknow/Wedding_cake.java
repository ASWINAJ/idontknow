package com.example.android.idontknow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Movie;
import android.media.audiofx.NoiseSuppressor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aswin on 31/3/16.
 */
public class Wedding_cake extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,Serializable,SwipeRefreshLayout.OnRefreshListener {

    private Boolean aBoolean=false;
    private DrawerLayout drawerLayout;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String url = "http://athena.nitc.ac.in/aswin_b130736cs/mine.json";
    private ArrayList<Item> Itemlist  = new ArrayList<Item>();
    private GridView listView;
    private CustomListAdapter adapter;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.wedding_cake_initial);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        listView = (GridView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, Itemlist);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        Itemlist.clear();
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        swipeRefreshLayout.setRefreshing(true);

                                        action();
                                    }
                                }
        );



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
        action();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Wedding_cake.this," " + position,Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Wedding_cake.this,Main_item.class);
                Item obj = Itemlist.get(position);
                i.putExtra("MyClass", (Serializable) obj);
                startActivity(i);
                finish();
            }
        });


    }

    public void action(){
        swipeRefreshLayout.setRefreshing(true);
        progressDialog.show();


        Toast.makeText(Wedding_cake.this,"im in action",Toast.LENGTH_SHORT).show();

        JsonArrayRequest itemreq =new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Itemlist.clear();
                        //progressDialog.dismiss();
                        for(int i=0;i< response.length();i++){
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Item item = new Item();
                                item.setItemname(obj.getString("title"));
                                item.setThumbnailUrl(obj.getString("image"));
                                item.setThumbnailUrl1(obj.getString("sub"));

                                Itemlist.add(item);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        listView.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);
                        progressDialog.dismiss();
                    }

                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                String err;
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                if(error instanceof NoConnectionError) {

                    err = "No internet Access, Check your internet connection.";
                    Toast.makeText(Wedding_cake.this,err,Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(Wedding_cake.this,error.toString(),Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Wedding_cake.this,Nointernet.class);
                startActivity(i);
                finish();
            }
        });
        int socketTimeout = 3000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 0, 0);
        itemreq.setRetryPolicy(policy);

        AppController.getInstance().addToRequestQueue(itemreq);

    }



    @Override
    public void onBackPressed() {


        Intent i= new Intent(Wedding_cake.this,Cakes_list.class);
        startActivity(i);
        finish();
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

    @Override
    public void onRefresh() {
        Itemlist.clear();
        action();
    }
}
