package com.example.android.idontknow;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;

/**
 * Created by aswin on 3/4/16.
 */
public class Delivery extends AppCompatActivity implements NumberPicker.OnValueChangeListener,Serializable {
    private EditText delivery_name;
    private EditText delivery_pincode;
    private EditText delivery_address;
    private EditText delivery_landmark;
    private EditText delivery_city;
    private EditText delivery_phone;
    private EditText delivery_amount;

    private TextInputLayout layout_delivery_name;
    private TextInputLayout layout_delivery_pincode;
    private TextInputLayout layout_delivery_address;
    private TextInputLayout layout_delivery_landmark;
    private TextInputLayout layout_delivery_city;
    private TextInputLayout layout_delivery_phone;
    private TextInputLayout layout_delivery_amount;
    private NumberPicker numberPicker;

    private String name;
    private String pincode;
    private String address;
    private String landmark;
    private String city;
    private String phone;
    private String amount;

    private Item item;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery);

        Intent i =getIntent();
        item = (Item)i.getExtras().getSerializable("MyClass");


        delivery_name = (EditText)findViewById(R.id.delivery_name);
        delivery_pincode = (EditText)findViewById(R.id.delivery_pincode);
        delivery_address = (EditText)findViewById(R.id.delivery_address);
        delivery_landmark = (EditText)findViewById(R.id.delivery_landmark);
        delivery_city = (EditText)findViewById(R.id.delivery_city);
        delivery_phone = (EditText)findViewById(R.id.delivery_phone);
        delivery_amount = (EditText)findViewById(R.id.delivery_amount);

        layout_delivery_name = (TextInputLayout)findViewById(R.id.layout_delivery_name);
        layout_delivery_pincode = (TextInputLayout)findViewById(R.id.layout_delivery_pincode);
        layout_delivery_address = (TextInputLayout)findViewById(R.id.layout_delivery_address);
        layout_delivery_landmark = (TextInputLayout)findViewById(R.id.layout_delivery_landmark);
        layout_delivery_city = (TextInputLayout)findViewById(R.id.layout_delivery_city);
        layout_delivery_phone = (TextInputLayout)findViewById(R.id.layout_delivery_phone);
        layout_delivery_amount = (TextInputLayout)findViewById(R.id.layout_delivery_amount);
        //numberPicker = (NumberPicker)findViewById(R.id.number);
        //numberPicker.setMaxValue(10);
        //numberPicker.setMinValue(0);



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

        delivery_amount.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Delivery.this);
                View view1 = getLayoutInflater().inflate(R.layout.numberpicker, null);
                builder.setTitle("NumberPicker1");
                builder.setCancelable(true);
                builder.setView(view1);
                final NumberPicker np = (NumberPicker) view1.findViewById(R.id.number);
                np.setMaxValue(100);
                np.setMinValue(0);
                np.setWrapSelectorWheel(false);

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int pickedValue = np.getValue();
                        // set your TextView id instead of R.id.textView1
                        delivery_amount.setText(Integer.toString(pickedValue));
                        return;
                    }
                });

                final AlertDialog d = builder.create();

                d.show();




            }
        });

        delivery_amount.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(Delivery.this);
                    View view1 = getLayoutInflater().inflate(R.layout.numberpicker, null);
                    builder.setTitle("NumberPicker1");
                    builder.setCancelable(true);
                    builder.setView(view1);
                    final NumberPicker np = (NumberPicker) view1.findViewById(R.id.number);
                    np.setMaxValue(100);
                    np.setMinValue(0);
                    np.setWrapSelectorWheel(false);

                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            int pickedValue = np.getValue();
                            // set your TextView id instead of R.id.textView1
                            delivery_amount.setText(Integer.toString(pickedValue));
                            return;
                        }
                    });

                    final AlertDialog d = builder.create();

                    d.show();



                }
            }
        });
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

        if(item.getItemId() == R.id.action_history){
            Intent i = new Intent(Delivery.this,History.class);
            startActivity(i);
        }

        Toast.makeText(Delivery.this,item.getTitle(),Toast.LENGTH_SHORT).show();

        return true;
    }


    public void click_delivery(View view){
        if(view.getId() == R.id.place_order){
            int a,b,c,d,e,f;
            a=b=c=d=e=f=0;

            name = delivery_name.getText().toString().trim();
            pincode = delivery_pincode.getText().toString().trim();
            address = delivery_address.getText().toString().trim();
            landmark = delivery_landmark.getText().toString().trim();
            city = delivery_city.getText().toString().trim();
            phone = delivery_phone.getText().toString().trim();
            amount = delivery_amount.getText().toString().trim();


            if(name.isEmpty()){
                Toast.makeText(Delivery.this,"Cannot be null",Toast.LENGTH_SHORT).show();
                layout_delivery_name.setError("Cannot be empty");
                a=0;
            }else {
                layout_delivery_name.setErrorEnabled(false);
                a=1;
            }

            if(amount.isEmpty()){
                Toast.makeText(Delivery.this,"Cannot be null",Toast.LENGTH_SHORT).show();
                layout_delivery_amount.setError("Cannot be empty");
                f=0;
            }else {
                layout_delivery_amount.setErrorEnabled(false);
                f=1;
            }

            if(pincode.isEmpty() || pincode.length()!=6){
                b=0;
                layout_delivery_pincode.setError("Invalid pincode");
            }else {
                b=1;
                layout_delivery_pincode.setErrorEnabled(false);
            }

            if(address.isEmpty()) {
                c=0;
                layout_delivery_address.setError("cannot be null");
            }
            else {
                c=1;
                layout_delivery_address.setErrorEnabled(false);
            }

            if(city.isEmpty()) {
                d=0;
                layout_delivery_city.setError("cannot be null");
            }
            else {
                d=1;
                layout_delivery_city.setErrorEnabled(false);
            }

            if(phone.isEmpty()) {
                e=0;
                layout_delivery_phone.setError("cannot be null");
            }
            else {
                e=1;
                layout_delivery_phone.setErrorEnabled(false);
            }

            if(a==1 && b==1 && c==1 && d==1 && e==1 && f==1){
                Toast.makeText(Delivery.this,"You are ready to go",Toast.LENGTH_SHORT).show();
                Transactions transactions = new Transactions();
                transactions.setName(name);
                transactions.setPincode(pincode);
                transactions.setAddress(address);
                transactions.setLandmark(landmark);
                transactions.setCity(city);
                transactions.setPhone(phone);
                transactions.setAmount(amount);
                transactions.setItemid(item.getItemid());
                transactions.setItemname(item.getItemname());
                transactions.setThumbnailUrl(item.getThumbnailUrl());
                transactions.setThumbnailUrl1(item.getThumbnailUrl1());

                Intent i = new Intent(Delivery.this,Sure.class);
                i.putExtra("MyClass",(Serializable)transactions);
                startActivity(i);
            }
        }


    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }
}
