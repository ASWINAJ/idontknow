package com.example.android.idontknow;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


public class User extends AppCompatActivity {
    private Button sign_in ;
    private Button sign_up ;
    private int pressed =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLUE);
        }

        Toolbar toolbar = (Toolbar)findViewById(R.id.open_toolbar);
        setSupportActionBar(toolbar);

        sign_in= (Button)findViewById(R.id.button_sign_in);
        sign_up = (Button)findViewById(R.id.button_sign_up);

    }


    @Override
    public void onBackPressed() {
        pressed++;

        if(pressed>1) {
            super.onBackPressed();
            finish();
        }
        else
            Toast.makeText(User.this,"Press again if you want to exit",Toast.LENGTH_SHORT).show();

    }

    public void onclick(View v)
    {
        if(v.getId()==R.id.button_sign_in){
            //Toast.makeText(User.this,"signin",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(User.this,SignIn.class);
            startActivity(i);
            finish();
        }
        else if(v.getId()==R.id.button_sign_up){
            //Toast.makeText(User.this,"signup",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(User.this,SignUp.class);
            startActivity(i);
            finish();
        }
    }


}
