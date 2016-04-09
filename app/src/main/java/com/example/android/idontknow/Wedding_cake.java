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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aswin on 31/3/16.
 */
public class Wedding_cake extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,Serializable,SwipeRefreshLayout.OnRefreshListener {

    private Boolean aBoolean=false;
    private DrawerLayout drawerLayout;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static String url ;
    private ArrayList<Item> Itemlist  = new ArrayList<Item>();
    private GridView listView;
    private CustomListAdapter adapter;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static String u ;
    //private Button item_remove;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.wedding_cake_initial);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        Intent i = getIntent();
        u = i.getStringExtra("url");
        //item_remove = (Button)findViewById(R.id.item_remove);

        /*if(item_remove==null)
            Toast.makeText(Wedding_cake.this,"THIS IS SHIT",Toast.LENGTH_SHORT).show();
*/
        //if(u.equals("http://athena.nitc.ac.in/aswin_b130736cs/getitems.php"))
           // item_remove.setVisibility(View.INVISIBLE);

        url=u;
        Toast.makeText(Wedding_cake.this,"this is the url" + u,Toast.LENGTH_SHORT).show();

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        int from;
        if(url.equals("http://athena.nitc.ac.in/aswin_b130736cs/getitems.php"))
            from = 0;
        else
            from = 1;

        listView = (GridView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, Itemlist,from);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        // sets the colors used in the refresh animation
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.red,
                R.color.colorPrimary);


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                boolean enable = false;
                if(listView!=null && listView.getChildCount()>0){
                    boolean firstItemVisible = listView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = listView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipeRefreshLayout.setEnabled(enable);

            }
        });



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
            if(url.equals("http://athena.nitc.ac.in/aswin_b130736cs/from_cart.php"))
            getSupportActionBar().setTitle("Cart");
            else
                getSupportActionBar().setTitle("Items");

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
                i.putExtra("from",url);
                startActivity(i);
                finish();
            }
        });


    }

    public void action(){
        swipeRefreshLayout.setRefreshing(true);
        progressDialog.show();


        Toast.makeText(Wedding_cake.this,"im in action" + SignUp.getUsername(Wedding_cake.this),Toast.LENGTH_SHORT).show();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", SignUp.getUsername(Wedding_cake.this));

        PostJsonArrayRequest itemreq =new PostJsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Itemlist.clear();
                        for(int i=0;i< response.length();i++){
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Item item = new Item();
                                item.setItemid(obj.getString("itemno"));
                                item.setItemname(obj.getString("title"));
                                item.setThumbnailUrl(obj.getString("image"));
                                item.setThumbnailUrl1(obj.getString("sub"));
                                item.setPrice(obj.getString("price"));
                                item.setRating(obj.getString("rating"));

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
                Toast.makeText(Wedding_cake.this,"im not in nitd",Toast.LENGTH_SHORT).show();
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
        },params)
        ;
        int socketTimeout = 3000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        itemreq.setRetryPolicy(policy);

        AppController.getInstance().addToRequestQueue(itemreq);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_history){
            Intent i = new Intent(Wedding_cake.this,History.class);
            startActivity(i);
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        if (url.equals("http://athena.nitc.ac.in/aswin_b130736cs/getitems.php")) {

            Intent i = new Intent(Wedding_cake.this, Cakes_list.class);
            startActivity(i);
            finish();
        }else
        {
            Intent i = new Intent(Wedding_cake.this, Initial.class);
            startActivity(i);
            finish();

        }
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
