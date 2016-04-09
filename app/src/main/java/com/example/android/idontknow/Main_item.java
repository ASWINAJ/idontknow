package com.example.android.idontknow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
    private Button add_to_cart;
    private Button buy;
    private Button click_to_check;
    private EditText pincode;
    private int pin;
    private int avail = -1;
    private Item item;
    public String url = "http://athena.nitc.ac.in/aswin_b130736cs/to_cart.php";
    public String from;


    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_item_initial);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        add_to_cart = (Button) findViewById(R.id.add_to_cart);
        buy = (Button) findViewById(R.id.buy_button);
        click_to_check = (Button) findViewById(R.id.check_availability);
        pincode = (EditText) findViewById(R.id.pincode);

        buy.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
        add_to_cart.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_white));

        networkImageView1 = (NetworkImageView) findViewById(R.id.main_image_1);
        networkImageView2 = (NetworkImageView) findViewById(R.id.main_image_2);
        networkImageView3 = (NetworkImageView) findViewById(R.id.main_image_3);
        networkImageView4 = (NetworkImageView) findViewById(R.id.main_image_4);
        networkImageView5 = (NetworkImageView) findViewById(R.id.main_image_5);

        Intent i = getIntent();

        from = i.getStringExtra("from");
        item = (Item) i.getExtras().getSerializable("MyClass");
        Toast.makeText(Main_item.this, "asee " + item.getItemname(), Toast.LENGTH_SHORT).show();

        networkImageView1.setImageUrl(item.getThumbnailUrl1(), imageLoader);
        networkImageView2.setImageUrl(item.getThumbnailUrl1(), imageLoader);
        networkImageView3.setImageUrl(item.getThumbnailUrl1(), imageLoader);
        networkImageView4.setImageUrl(item.getThumbnailUrl1(), imageLoader);
        networkImageView5.setImageUrl(item.getThumbnailUrl1(), imageLoader);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLUE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.initial_toolbar);
        if (toolbar != null) {
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

        if (from == null) {

            Intent i = new Intent(Main_item.this, History.class);
            startActivity(i);
            finish();

        } else {
            Intent i = new Intent(Main_item.this, Wedding_cake.class);
            i.putExtra("url", from);
            startActivity(i);
            finish();
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_history)
        {
            Intent i = new Intent(Main_item.this,History.class);
            startActivity(i);
        }

        if(item.getItemId() == R.id.action_history){
            Intent i = new Intent(Main_item.this,History.class);
            startActivity(i);
        }

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

    public void click_check_avail(View view){

        if(view.getId()==R.id.check_availability){

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            String pinc;
            pinc = pincode.getText().toString().trim();
            if(pinc.isEmpty())
                Toast.makeText(Main_item.this,"Not a valid pincode",Toast.LENGTH_SHORT).show();

            else {
                pin = Integer.parseInt(pincode.getText().toString().trim());

                if(pinc.length()==6)
                {
                    if (pin == 673601) {
                        Toast.makeText(Main_item.this, "Available in this locality", Toast.LENGTH_SHORT).show();
                        avail = 1;
                    }
                    else {
                        Toast.makeText(Main_item.this, "Not available in this locality", Toast.LENGTH_SHORT).show();
                        avail = 0;
                    }
                }

                else
                    Toast.makeText(Main_item.this,"Heyy this is not even a pincode",Toast.LENGTH_SHORT).show();


            }

        }else if(view.getId() == R.id.buy_button){
            if(avail==-1)
                Toast.makeText(Main_item.this,"Enter your pin",Toast.LENGTH_SHORT).show();
            else if(avail==0)
                Toast.makeText(Main_item.this,"Try with another locality",Toast.LENGTH_SHORT).show();
            else if(avail==1) {
                Toast.makeText(Main_item.this, "Yeah baby now u can buy", Toast.LENGTH_SHORT).show();
                    Intent i =new Intent(Main_item.this,Delivery.class);
                    i.putExtra("MyClass", (Serializable) item);
                    startActivity(i);

            }
        }
        else if(view.getId() == R.id.add_to_cart){


            StringRequest request = new StringRequest(Request.Method.POST,url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("success")) {

                                Toast.makeText(Main_item.this, "Successfully entered", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(Main_item.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();



                            }
                        }
                    },
                    new Response.ErrorListener() {
                        String err;
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if(error instanceof NoConnectionError) {
                                err = "No internet Access, Check your internet connection.";
                                Toast.makeText(Main_item.this,err,Toast.LENGTH_SHORT).show();

                            }
                            else
                                Toast.makeText(Main_item.this,error.toString(),Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("custemail",SignUp.getUsername(Main_item.this));
                    params.put("itemid",item.getItemid());
                    return params;
                }
            };

            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);


        }

    }
}
