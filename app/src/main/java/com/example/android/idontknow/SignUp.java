package com.example.android.idontknow;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Sampler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

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

import java.util.HashMap;
import java.util.Map;


public class SignUp extends AppCompatActivity {
    private Button sign_up;
    private EditText edit_username;
    private EditText edit_pass;
    private EditText edit_email;
    private EditText edit_contact;
    private static final String url = "http://athena.nitc.ac.in/aswin_b130736cs/register.php";
    private static final String KEY_USER = "username";
    private static final String KEY_PASS = "password";
    private static final String KEY_EMAIL = "email";
    private String username;
    private String password;
    private String email;
    private ProgressDialog progressDialog;
    private static SharedPreferences sharedPreferences;
    private static String DbName = "LOGIN";
    private Context mContext;
    private TextInputLayout layout_signup_usename;
    private TextInputLayout layout_signup_email;
    private TextInputLayout layout_signup_password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mContext = this;

        layout_signup_usename = (TextInputLayout)findViewById(R.id.layout_signup_username);
        layout_signup_email = (TextInputLayout) findViewById(R.id.layout_signup_email);
        layout_signup_password = (TextInputLayout)findViewById(R.id.layout_signup_password);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLUE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.signup_toolbar);

        if(toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setElevation(0);
        }

        sign_up = (Button) findViewById(R.id.button_orig_sign_up);
        edit_username = (EditText) findViewById(R.id.edit_username);
        edit_pass = (EditText) findViewById(R.id.edit_pass);
        edit_email = (EditText) findViewById(R.id.edit_email);
    }

    private static SharedPreferences getvals(Context context){
        return context.getSharedPreferences(DbName,Context.MODE_PRIVATE);
    }
    public static String getUsername(Context context){
        return getvals(context).getString("Email", null);
    }
    public static String getKeyPass(Context context){
        return getvals(context).getString("Password",null);
    }
    public static void setKeys(Context context,String email,String password){
        SharedPreferences.Editor sharedPreferences =   getvals(context).edit();
        sharedPreferences.putString("Email",email);
        sharedPreferences.putString("Password",password);
        sharedPreferences.commit();
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(SignUp.this,User.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home)
            onBackPressed();
        return true;
    }

    public void register() {

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Toast.makeText(SignUp.this, response, Toast.LENGTH_SHORT).show();

                        if(response.toString().trim().equals("Successfully Registered")){
                            setKeys(SignUp.this, email, password);
                            Intent i = new Intent(SignUp.this,Initial.class);
                            startActivity(i);
                            finish();
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
                            Toast.makeText(SignUp.this,err,Toast.LENGTH_SHORT).show();

                        }else
                        Toast.makeText(SignUp.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_USER, username);
                params.put(KEY_PASS, password);
                params.put(KEY_EMAIL, email);
                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        RequestQueue requestQueue = Volley.newRequestQueue(SignUp.this);
        requestQueue.add(request);

    }


    public void onclick(View v) {

        int a,b,c;
        a=b=c=0;
        if (v.getId() == R.id.button_orig_sign_up) {

            username = edit_username.getText().toString();
            password = edit_pass.getText().toString();
            email = edit_email.getText().toString();
            String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (username.isEmpty()) {
                layout_signup_usename.setError("required");
                a=0;
            }
            else {
                layout_signup_usename.setErrorEnabled(false);
                a=1;
            }

            if (email.isEmpty() || !email.matches(pattern)) {
                layout_signup_email.setError("Email not valid");
                b=0;
            }
            else {
                layout_signup_email.setErrorEnabled(false);
                b=1;
            }
            if (password.isEmpty() || (password.length()<5)) {
                layout_signup_password.setError("atleast 5 characters");
                c=0;
            }else {
                layout_signup_password.setErrorEnabled(false);
                c=1;
            }
            if(a==1 && b==1 && c==1) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Creating account...");
                progressDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        register();



                    }

                },3000);



                }
            } else if (v.getId() == R.id.already) {
            Intent i = new Intent(SignUp.this, SignIn.class);
            startActivity(i);
            finish();
        }
    }
}
