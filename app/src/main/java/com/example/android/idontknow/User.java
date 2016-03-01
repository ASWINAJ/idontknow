package com.example.android.idontknow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by aswin on 29/2/16.
 */
public class User extends AppCompatActivity {
    private Button sign_in ;
    private Button sign_up ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        sign_in= (Button)findViewById(R.id.button_sign_in);
        sign_up = (Button)findViewById(R.id.button_sign_up);

    }

    public void onclick(View v)
    {
        if(v.getId()==R.id.button_sign_in){
            Toast.makeText(User.this,"signin",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(User.this,SignIn.class);
            startActivity(i);
        }
        else if(v.getId()==R.id.button_sign_up){
            Toast.makeText(User.this,"signup",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(User.this,SignUp.class);
            startActivity(i);
        }
    }


}
