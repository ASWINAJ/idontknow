package com.example.android.idontknow;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by aswin on 3/4/16.
 */
public class Delivery extends AppCompatActivity {
    private EditText delivery_name;
    private EditText delivery_pincode;
    private EditText delivery_address;
    private EditText delivery_landmark;
    private EditText delivery_city;
    private EditText delivery_phone;

    private TextInputLayout layout_delivery_name;
    private TextInputLayout layout_delivery_pincode;
    private TextInputLayout layout_delivery_address;
    private TextInputLayout layout_delivery_landmark;
    private TextInputLayout layout_delivery_city;
    private TextInputLayout layout_delivery_phone;

    private String name;
    private String pincode;
    private String address;
    private String landmark;
    private String city;
    private String phone;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery);

        delivery_name = (EditText)findViewById(R.id.delivery_name);
        delivery_pincode = (EditText)findViewById(R.id.delivery_pincode);
        delivery_address = (EditText)findViewById(R.id.delivery_address);
        delivery_landmark = (EditText)findViewById(R.id.delivery_landmark);
        delivery_city = (EditText)findViewById(R.id.delivery_city);
        delivery_phone = (EditText)findViewById(R.id.delivery_phone);

        layout_delivery_name = (TextInputLayout)findViewById(R.id.layout_delivery_name);
        layout_delivery_pincode = (TextInputLayout)findViewById(R.id.layout_delivery_pincode);
        layout_delivery_address = (TextInputLayout)findViewById(R.id.layout_delivery_address);
        layout_delivery_landmark = (TextInputLayout)findViewById(R.id.layout_delivery_landmark);
        layout_delivery_city = (TextInputLayout)findViewById(R.id.layout_delivery_city);
        layout_delivery_phone = (TextInputLayout)findViewById(R.id.layout_delivery_phone);

        name = delivery_name.getText().toString().trim();
        pincode = delivery_pincode.getText().toString().trim();
        address = delivery_address.getText().toString().trim();
        landmark = delivery_landmark.getText().toString().trim();
        city = delivery_city.getText().toString().trim();
        phone = delivery_phone.getText().toString().trim();




        Toolbar toolbar = (Toolbar) findViewById(R.id.delivery_toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLUE);
        }

        if(toolbar!=null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
            onBackPressed();

        Toast.makeText(Delivery.this,item.getTitle(),Toast.LENGTH_SHORT).show();

        return true;
    }
}
