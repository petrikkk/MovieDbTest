package com.mobile.petrk.moviedbtest;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pojo.Result;

//adapter pro naplneni ListView
class MyAdapter extends BaseAdapter {
    List<Result> list;
    Activity context;
    private static LayoutInflater inflater = null;



    public MyAdapter (Activity context, List<Result> list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return list.size();

    }

    @Override
    public Result getItem(int position) {
        return list.get(position);
    }

     @Override
     public long getItemId(int position) {
         return position;
     }

     @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.linear_layout_output,null): itemView;
        ImageView moviePoster = (ImageView) itemView.findViewById(R.id.imageview);
        TextView movieNameTextview = (TextView) itemView.findViewById(R.id.textviewName);
        Result selectedResult = list.get(position);
         //nahrazuje "adult" nebo prazdne obrazky, jinak nahraje spravny poster
        if   (selectedResult.getImgURL()==null | (selectedResult.getAdult()==true))
            Picasso.get().load("http://i.imgur.com/DvpvklR.png").resize(800, 800)
                    .centerCrop().into(moviePoster);
        else
            Picasso.get().load("https://image.tmdb.org/t/p/w500"+selectedResult.getImgURL())
                    .into(moviePoster);
         movieNameTextview.setText(selectedResult.getMovieName());
        return itemView;

    }
}

