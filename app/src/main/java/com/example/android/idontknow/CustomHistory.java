package com.example.android.idontknow;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aswin on 5/4/16.
 */

public class CustomHistory extends BaseAdapter {

    private final String url = "http://athena.nitc.ac.in/aswin_b130736cs/setstars.php";
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<Transactions> transactionsArrayList;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();



    public CustomHistory(Activity activity, ArrayList<Transactions> transactionsArrayList) {
        this.activity = activity;
        this.transactionsArrayList = transactionsArrayList ;
    }

    @Override
    public int getCount() {
        return transactionsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return transactionsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_history, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.history_image);
        //TextView name = (TextView) convertView.findViewById(R.id.history_name);
        //TextView address = (TextView)convertView.findViewById(R.id.history_address);
        //TextView city = (TextView)convertView.findViewById(R.id.history_city);
        //TextView pincode = (TextView)convertView.findViewById(R.id.history_pincode);
        TextView itemname = (TextView) convertView.findViewById(R.id.history_item_name);
        TextView price = (TextView) convertView.findViewById(R.id.history_price);
        TextView amount = (TextView)convertView.findViewById(R.id.history_amount);
        TextView date = (TextView)convertView.findViewById(R.id.history_date);
        final RatingBar ratingBar = (RatingBar)convertView.findViewById(R.id.history_rating);
        //TextView phone = (TextView)convertView.findViewById(R.id.history_phone);


        // getting movie data for the row
        Transactions m = transactionsArrayList.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
        //name.setText(m.getName());
        //address.setText(m.getAddress());
        //city.setText(m.getCity());
        //pincode.setText(m.getPincode());
        price.setText("250.00");
        amount.setText(m.getAmount());
        ratingBar.setRating((float)(m.getNostars()));
        ratingBar.setEnabled(true);
        ratingBar.setClickable(true);
        date.setText(m.getDateoftrans());
        //phone.setText(m.getPhone());
        itemname.setText(m.getItemname());

        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float touchPositionX = event.getX();
                    float width = ratingBar.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    int stars = (int) starsf + 1;
                    ratingBar.setRating(stars);
                    v.setPressed(false);
                    transactionsArrayList.get(position).setNostars(stars);

                    Toast.makeText(CustomHistory.this.activity,"trasnId = " + transactionsArrayList.get(position).getTransId(),Toast.LENGTH_SHORT).show();
                    StringRequest request = new StringRequest(Request.Method.POST,url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.trim().equals("success")) {

                                        Toast.makeText(CustomHistory.this.activity, "Successfully entered", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(CustomHistory.this.activity, response, Toast.LENGTH_SHORT).show();



                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                String err;
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    if(error instanceof NoConnectionError) {
                                        err = "No internet Access, Check your internet connection.";
                                        Toast.makeText(CustomHistory.this.activity,err,Toast.LENGTH_SHORT).show();

                                    }
                                    else
                                        Toast.makeText(CustomHistory.this.activity,"ERROR" + error.toString(),Toast.LENGTH_SHORT).show();
                                }
                            })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String,String>();
                            params.put("transId",transactionsArrayList.get(position).getTransId());
                            params.put("nostars",Float.toString(ratingBar.getRating()));
                            return params;
                        }
                    };

                    int socketTimeout = 30000;
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    request.setRetryPolicy(policy);

                    RequestQueue requestQueue = Volley.newRequestQueue(CustomHistory.this.activity);
                    requestQueue.add(request);



                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setPressed(true);
                }

                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setPressed(false);
                }



                return true;
            }
        });
        return convertView;

    }


}
