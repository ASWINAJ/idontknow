package com.example.android.idontknow;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
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


public class SignIn extends AppCompatActivity {
    private static final String url = "http://athena.nitc.ac.in/aswin_b130736cs/getone.php";
    private EditText edit_email;
    private EditText edit_password;
    private Button button_orig_sign_in;
    private String email;
    private String password;
    private ProgressDialog progressDialog;
    private TextInputLayout layout_signin_username;
    private TextInputLayout layout_signin_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLUE);
        }
        layout_signin_username = (TextInputLayout)findViewById(R.id.layout_signin_username);
        layout_signin_password = (TextInputLayout) findViewById(R.id.layout_signin_password);
        Toolbar toolbar = (Toolbar)findViewById(R.id.signin_toolbar);
        if(toolbar!=null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setHomeButtonEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
           // getSupportActionBar().setElevation(0);
        }

        edit_email = (EditText)findViewById(R.id.edit_email);
        edit_password = (EditText)findViewById(R.id.edit_pass);
        button_orig_sign_in = (Button)findViewById(R.id.button_orig_sign_in);


      //  Toast.makeText(SignIn.this, SignUp.getKeyPass(this), Toast.LENGTH_SHORT).show();
      //  Toast.makeText(SignIn.this, SignUp.getUsername(this), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SignIn.this,User.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home) {
            finish();
            onBackPressed();
        }
        return true;
    }

    public void login()
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,
        new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response.trim().equals("success")) {
                        SignUp.setKeys(SignIn.this, email, password);

                    Toast.makeText(SignIn.this, "Successfully entered", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SignIn.this,Initial.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(SignIn.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                    edit_email.setText(null);
                    edit_password.setText(null);


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
                    Toast.makeText(SignIn.this,err,Toast.LENGTH_SHORT).show();

                }
                else
                Toast.makeText(SignIn.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    public void Click(View v){

        email = edit_email.getText().toString();
        password = edit_password.getText().toString();

         if(v.getId()==R.id.text_create){
            Intent i = new Intent(SignIn.this,SignUp.class);
            startActivity(i);
             finish();
        }else if(v.getId()==R.id.button_orig_sign_in) {

             if (email.isEmpty()) {
                 layout_signin_username.setError("Required");
             }
             else if(!email.isEmpty())
                 layout_signin_username.setErrorEnabled(false);

             if (password.isEmpty()) {
                 layout_signin_password.setError("Required");
             }
             else if(!password.isEmpty())
                 layout_signin_password.setErrorEnabled(false);

             if(!email.isEmpty() && !password.isEmpty()){
                 progressDialog = new ProgressDialog(this);
                 progressDialog.setIndeterminate(true);
                 progressDialog.setCancelable(false);
                 progressDialog.setMessage("Authenticating...");
                 progressDialog.show();

                 new Handler().postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         login();


                     }
                 }, 3000);

             }
         }

    }
}
