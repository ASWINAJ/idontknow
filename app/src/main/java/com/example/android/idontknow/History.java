package com.example.android.idontknow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aswin on 4/4/16.
 */
public class History extends AppCompatActivity {

    private static final String url = "http://athena.nitc.ac.in/aswin_b130736cs/getallhistory.php";
    private ArrayList<Transactions> transactionsArrayList  = new ArrayList<Transactions>();
    private GridView listhistory;
    private CustomHistory adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        Toast.makeText(History.this,"this is histort",Toast.LENGTH_SHORT).show();

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        listhistory = (GridView) findViewById(R.id.list_history);
        adapter = new CustomHistory(this, transactionsArrayList);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLUE);
        }

        Toolbar toolbar = (Toolbar)findViewById(R.id.history_toolbar);
        if(toolbar!=null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        action();
    }



    public void action(){
        progressDialog.show();


        Toast.makeText(History.this, "im in action history", Toast.LENGTH_SHORT).show();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", SignUp.getUsername(History.this));

        PostJsonArrayRequest itemreq =new PostJsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        progressDialog.dismiss();
                        Toast.makeText(History.this,"YEA GOT IT "+ response.length(),Toast.LENGTH_SHORT).show();

                        for(int i=0;i< response.length();i++){
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                Transactions item = new Transactions();
                                item.setThumbnailUrl(obj.getString("itemurl1"));
                                item.setName(obj.getString("custname"));
                                item.setAddress(obj.getString("address"));
                                item.setCity(obj.getString("city"));
                                item.setPincode(obj.getString("pin"));
                                item.setItemname(obj.getString("itemname"));
                                item.setAmount(obj.getString("kilos"));
                                item.setNostars(obj.getInt("nostars"));
                                item.setPhone(obj.getString("phone"));
                                item.setTransId(obj.getString("transId"));
                                item.setDateoftrans(obj.getString("date"));


                                transactionsArrayList.add(item);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        listhistory.setAdapter(adapter);
                    }

                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                String err;
                progressDialog.dismiss();
                if(error instanceof NoConnectionError) {

                    err = "No internet Access, Check your internet connection.";
                    Toast.makeText(History.this,err,Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(History.this,error.toString(),Toast.LENGTH_SHORT).show();

                 Intent i = new Intent(History.this,Nointernet.class);
                 startActivity(i);
                 finish();
            }
        }


        ,params);

        //itemreq.email = "abc@xyz.com";

        int socketTimeout = 3000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        itemreq.setRetryPolicy(policy);

        AppController.getInstance().addToRequestQueue(itemreq);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return true;
    }
}
