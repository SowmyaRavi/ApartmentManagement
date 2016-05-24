package com.example.piyushravi.finalapartment;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Piyush Ravi on 5/7/2016.
 */
public class CustomSlider extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    private final String className;

    public CustomSlider(Activity context, String[] web, Integer[] imageId, String className) {
        super(context, R.layout.list_slider,web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.className=className;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_slider, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        if(this.className.equals(web[position])){
            txtTitle.setTextColor(Color.parseColor("#FFBA00"));
        }
        txtTitle.setText(web[position]);
        try{
            imageView.setImageResource(imageId[position]);
        }catch(Exception e){
            imageView.setImageResource(imageId[0]);
        }

        return rowView;
    }


}
