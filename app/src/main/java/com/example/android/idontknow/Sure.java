package com.example.android.idontknow;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aswin on 4/4/16.
 */
public class Sure extends AppCompatActivity {
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    private NetworkImageView sure_image;
    private TextView sure_item_name;
    private TextView sure_kilos;
    private TextView sure_price;
    private TextView sure_name;
    private TextView sure_address;
    private TextView sure_pincode;
    private TextView sure_phone;
    private Transactions transaction;
    private ProgressDialog progressDialog;
    private String url;
    private int transId;
    private ProgressDialog progressDialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sure);

        url = "http://athena.nitc.ac.in/aswin_b130736cs/buy.php";

        Intent i = getIntent();
        transaction = (Transactions)i.getExtras().getSerializable("MyClass");

        Toast.makeText(Sure.this,transaction.getItemname()+transaction.getName(),Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLUE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.sure_toolbar);

        if(toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        sure_image = (NetworkImageView) findViewById(R.id.sure_image);
        sure_item_name = (TextView) findViewById(R.id.sure_item_name);
        sure_kilos = (TextView) findViewById(R.id.sure_kilos);
        sure_price = (TextView) findViewById(R.id.sure_price);
        sure_name = (TextView) findViewById(R.id.sure_name);
        sure_address = (TextView) findViewById(R.id.sure_address);
        sure_pincode = (TextView) findViewById(R.id.sure_pincode);
        sure_phone = (TextView) findViewById(R.id.sure_phone);

        sure_image.setImageUrl(transaction.getThumbnailUrl1(),imageLoader);
        sure_item_name.setText(transaction.getItemname() + transaction.getItemid());
        sure_kilos.setText( transaction.getAmount());
        sure_price.setText("250.00");
        sure_name.setText( transaction.getName());
        sure_address.setText(transaction.getAddress());
        sure_pincode.setText( transaction.getPincode());
        sure_phone.setText(transaction.getPhone());
    }

    public void click_sure(View view){
        if(view.getId() == R.id.sure_sure){

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loadind..");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();

            StringRequest request = new StringRequest(Request.Method.POST,url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            transId = Integer.parseInt(response.toString().trim());
                            Toast.makeText(Sure.this,"this is the val" + transId,Toast.LENGTH_SHORT).show();
                            if (transId > 0) {

                                Toast.makeText(Sure.this, "Successfully entered", Toast.LENGTH_SHORT).show();

                                final AlertDialog.Builder builder = new AlertDialog.Builder(Sure.this);
                                builder.setTitle("Request Recieved"+transaction.getPhone());
                                builder.setCancelable(false);
                                builder.setMessage("Your transaction id is " + transId + "\nIt will be delivered within" +
                                        " 2 days from today.\nHappy to be a part of your happiness.\nThank you.");

                                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        progressDialog1 = new ProgressDialog(Sure.this);
                                        progressDialog1.setIndeterminate(true);
                                        progressDialog1.setCancelable(false);
                                        progressDialog1.setMessage("Redirecting...");
                                        progressDialog1.show();
                                        new Handler().postDelayed(new Runnable(){

                                            @Override
                                            public void run() {
                                                progressDialog1.dismiss();
                                                Intent i = new Intent(Sure.this, Initial.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        },3000);


                                    }
                                });

                                final  AlertDialog dialog = builder.create();
                                dialog.show();

                            } else {
                                Toast.makeText(Sure.this, response.trim(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        String err;
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            if(error instanceof NoConnectionError) {
                                err = "No internet Access, Check your internet connection.";
                                Toast.makeText(Sure.this,err,Toast.LENGTH_SHORT).show();

                            }
                            else
                                Toast.makeText(Sure.this,error.toString(),Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("custname",transaction.getName());
                    params.put("customeremail",SignUp.getUsername(Sure.this));
                    params.put("itemId",transaction.getItemid());
                    params.put("kilos",transaction.getAmount());
                    params.put("price","250.00");
                    params.put("address",transaction.getAddress());
                    params.put("pin",transaction.getPincode());
                    params.put("phone",transaction.getPhone());
                    params.put("landmark",transaction.getLandmark());
                    params.put("city",transaction.getCity());

                    return params;
                }
            };

            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);



            Toast.makeText(Sure.this,"We are processing",Toast.LENGTH_SHORT).show();


        }
        else if(view.getId() == R.id.sure_cancel){
            onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
            onBackPressed();

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }
}
