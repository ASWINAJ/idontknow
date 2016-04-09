package com.example.android.idontknow;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aswin on 29/2/16.
 */
public class OpenActivity extends AppCompatActivity implements Animation.AnimationListener {
    private ProgressDialog progressDialog;
    private boolean aBoolean=false;
    private ImageView imageView;
    Animation myFadeInAnimation;
    private String url ="http://athena.nitc.ac.in/aswin_b130736cs/check_internet.php";

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

    public void action(){

        new Thread(new Runnable(){

            @Override
            public void run() {

                if (isNetworkAvailable()) {
                    try {
                        HttpURLConnection urlc = (HttpURLConnection) (new URL("http://clients3.google.com/generate_204").openConnection());
                        urlc.setRequestProperty("User-Agent", "Test");
                        urlc.setRequestProperty("Connection", "close");
                        urlc.setConnectTimeout(7000);
                        urlc.connect();
                        aBoolean = (urlc.getResponseCode() == 204 && urlc.getContentLength() == 0);
                    } catch (IOException e) {
                        Log.e("ERROR", "Error checking internet connection", e);
                    }
                } else {
                    Log.d("ERROR", "No network available!");
                }

            }
        }).start();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    public static ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progressbar);
        // dialog.setMessage(Message);
        return dialog;
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onAnimationEnd(Animation animation) {

        progressDialog = createProgressDialog(OpenActivity.this);
        progressDialog.show();
       // progressDialog.setIndeterminate(true);
       // progressDialog.setCancelable(false);

        StringRequest request = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if (response.trim().equals("success")) {


                            String email = SignUp.getUsername(OpenActivity.this);
                            String pass = SignUp.getKeyPass(OpenActivity.this);

                            int fine = 0;


                                if ((email != null) && (pass != null)) {
                                    fine = 1;
                                }

                                final Intent mainIntent = new Intent(OpenActivity.this, User.class);
                                final Intent mainIntent2 = new Intent(OpenActivity.this, Initial.class);

                                final int finalFine = fine;


                                if (finalFine == 0)
                                    startActivity(mainIntent);
                                else
                                    startActivity(mainIntent2);
                                finish();

                            }



                            Toast.makeText(OpenActivity.this,"this is what i do",Toast.LENGTH_SHORT).show();



                        }


                },
                new Response.ErrorListener() {
                    String err;
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if(error instanceof NoConnectionError) {
                            err = "No internet Access, Check your internet connection.";
                            Toast.makeText(OpenActivity.this,err,Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(OpenActivity.this,Nointernet.class);
                            startActivity(i);
                            finish();

                        }
                        else
                            Toast.makeText(OpenActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("email","check");
                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {


    }



}
