package com.mobile.petrk.moviedbtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import pojo.MovieDetailsPOJO;
import support.ComposeDetails;
import support.LoadLists;


public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        //extrakce id filmu z intentu preneseneho z predchozi akticity
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        StringBuilder stringBuilder;

        TextView textView = (TextView)findViewById(R.id.textMovieDetails);
        ImageView moviePoster = (ImageView) findViewById(R.id.moviePoster);
        MovieDetailsPOJO movieDetailsPOJO;
        LoadLists loadLists = new LoadLists();
        int movieId = Integer.parseInt(id);
        //naplneni pojo
        movieDetailsPOJO = loadLists.getDetailsList(movieId);
        ComposeDetails composeDetails = new ComposeDetails();
        //seskladani textu z pojo a naplneni textview
        try {
            stringBuilder = composeDetails.getComposedDetails(movieDetailsPOJO);
            if (movieDetailsPOJO.getPosterPath() != null)
                Picasso.get().load("https://image.tmdb.org/t/p/w500" + movieDetailsPOJO.getPosterPath()).resize(800, 800)
                        .into(moviePoster);
            else Picasso.get().load("http://i.imgur.com/DvpvklR.png").resize(800, 800)
                    .centerCrop().into(moviePoster);
            textView.setMovementMethod(new ScrollingMovementMethod());
            textView.setText(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
