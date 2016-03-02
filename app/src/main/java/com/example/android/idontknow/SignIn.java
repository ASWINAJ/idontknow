package com.example.android.idontknow;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        Toolbar toolbar = (Toolbar)findViewById(R.id.signin_toolbar);
        if(toolbar!=null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setElevation(0);
        }


        edit_email = (EditText)findViewById(R.id.edit_email);
        edit_password = (EditText)findViewById(R.id.edit_pass);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home)
            onBackPressed();
        return true;
    }

    public void login()
    {
        email = edit_email.getText().toString();
        password = edit_password.getText().toString();
        StringRequest request = new StringRequest(Request.Method.POST,url,
        new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success")){
                    Toast.makeText(SignIn.this,"Successfully entered",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(SignIn.this,"Incorrect Username or Password",Toast.LENGTH_SHORT).show();
            }
        },
        new Response.ErrorListener() {
            String err;
            @Override
            public void onErrorResponse(VolleyError error) {
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void Click(View v){

        if(v.getId()==R.id.text_create){
            Intent i = new Intent(SignIn.this,SignUp.class);
            startActivity(i);
        }else if(v.getId()==R.id.button_orig_sign_in){
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    login();

                }
            }, 2000);

        }
    }
}
