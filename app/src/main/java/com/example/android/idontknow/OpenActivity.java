package com.example.android.idontknow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by aswin on 29/2/16.
 */
public class OpenActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openactivity);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(OpenActivity.this, User.class);
                startActivity(mainIntent);
                OpenActivity.this.finish();
            }
        }, 3000);




    }
}
