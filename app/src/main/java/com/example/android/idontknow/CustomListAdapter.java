package com.example.android.idontknow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aswin on 1/4/16.
 */
public class CustomListAdapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<Item> Itemlist;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();



    public CustomListAdapter(Activity activity, ArrayList<Item> Itemlist) {
        this.activity = activity;
        this.Itemlist = Itemlist ;
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
    public View getView(int position, View convertView, ViewGroup parent) {

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


        // getting movie data for the row
        Item m = Itemlist.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        itemname.setText(m.getItemname());


        return convertView;

    }
}
