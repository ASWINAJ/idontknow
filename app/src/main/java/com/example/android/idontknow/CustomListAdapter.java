package com.example.android.idontknow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aswin on 1/4/16.
 */
public class CustomListAdapter extends BaseAdapter{

    private int from;
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<Item> Itemlist;
    private String url;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();



    public CustomListAdapter(Activity activity, ArrayList<Item> Itemlist,int from) {
        this.activity = activity;
        this.Itemlist = Itemlist ;
        this.from = from;
    }

    @Override
    public int getCount() {
        return Itemlist.size();
    }

    @Override
    public Object getItem(int position) {
        return Itemlist.get(position);
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
            convertView = inflater.inflate(R.layout.list_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.image);
        TextView itemname = (TextView) convertView.findViewById(R.id.itemname);
        TextView price = (TextView)convertView.findViewById(R.id.price);
        RatingBar ratingBar = (RatingBar)convertView.findViewById(R.id.rating);
        Button item_remove = (Button)convertView.findViewById(R.id.item_remove);

        if(from==0)
            item_remove.setVisibility(View.INVISIBLE);

        if(from==1) {
        item_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Itemlist.remove(position);
                CustomListAdapter.this.notifyDataSetChanged();

                url = "http://athena.nitc.ac.in/aswin_b130736cs/remove_cart.php";
                Toast.makeText(CustomListAdapter.this.activity,"This is to be removed",Toast.LENGTH_SHORT).show();

                StringRequest request = new StringRequest(Request.Method.POST,url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.trim().equals("success")) {

                                    Toast.makeText(CustomListAdapter.this.activity, "Successfully removed", Toast.LENGTH_SHORT).show();
                                    //Intent i = new Intent(CustomListAdapter.this.activity,Wedding_cake.class);
                                    //i.putExtra("url","http://athena.nitc.ac.in/aswin_b130736cs/from_cart.php");

                                    //finish();
                                } else {
                                    Toast.makeText(CustomListAdapter.this.activity, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();



                                }
                            }
                        },
                        new Response.ErrorListener() {
                            String err;
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if(error instanceof NoConnectionError) {
                                    err = "No internet Access, Check your internet connection.";
                                    Toast.makeText(CustomListAdapter.this.activity,err,Toast.LENGTH_SHORT).show();

                                }
                                else
                                    Toast.makeText(CustomListAdapter.this.activity,error.toString(),Toast.LENGTH_SHORT).show();
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("custemail",SignUp.getUsername(CustomListAdapter.this.activity));
                        params.put("itemid",Itemlist.get(position).getItemid());
                        return params;
                    }
                };

                int socketTimeout = 30000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                request.setRetryPolicy(policy);

                RequestQueue requestQueue = Volley.newRequestQueue(CustomListAdapter.this.activity);
                requestQueue.add(request);


            }
        });
        }

        // getting movie data for the row
        Item m = Itemlist.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);


        // title
        itemname.setText(m.getItemname());
        price.setText("Rs." +m.getPrice() );
        ratingBar.setRating(Float.parseFloat(m.getRating()));

        return convertView;

    }
}
