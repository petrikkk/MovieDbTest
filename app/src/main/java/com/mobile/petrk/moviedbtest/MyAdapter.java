package com.mobile.petrk.moviedbtest;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import com.squareup.picasso.Picasso;
import android.content.Context;
import java.util.List;

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
        TextView movieNameTextview = (TextView) itemView.findViewById(R.id.textviewName);
        ImageView moviePoster = (ImageView) itemView.findViewById(R.id.imageview);
        Result selectedResult = list.get(position);
        if (selectedResult.getImgURL()=="not available")
            Picasso.get().load("http://i.imgur.com/DvpvklR.png").resize(200, 200)
                    .centerCrop().into(moviePoster);
        else
            Picasso.get().load("https://image.tmdb.org/t/p/w500"+selectedResult.getImgURL()).resize(200, 200)
                    .into(moviePoster);
         movieNameTextview.setText(selectedResult.getMovieName());
         System.out.println(selectedResult.getMovieName());
        return itemView;

    }
}

