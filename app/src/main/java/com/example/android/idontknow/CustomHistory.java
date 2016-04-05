package com.example.android.idontknow;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by aswin on 5/4/16.
 */

public class CustomHistory extends BaseAdapter {

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
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_history, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.history_image);
        TextView name = (TextView) convertView.findViewById(R.id.history_name);
        TextView address = (TextView)convertView.findViewById(R.id.history_address);
        TextView city = (TextView)convertView.findViewById(R.id.history_city);
        TextView pincode = (TextView)convertView.findViewById(R.id.history_pincode);
        TextView itemname = (TextView) convertView.findViewById(R.id.history_item_name);
        TextView price = (TextView) convertView.findViewById(R.id.history_price);
        TextView amount = (TextView)convertView.findViewById(R.id.history_amount);


        // getting movie data for the row
        Transactions m = transactionsArrayList.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
        name.setText(m.getName());
        address.setText(m.getAddress());
        city.setText(m.getCity());
        pincode.setText(m.getPincode());
        price.setText("250.00");
        amount.setText(m.getAmount());


        // title
        itemname.setText(m.getItemname());


        return convertView;

    }
}
