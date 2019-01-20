package com.mobile.petrk.moviedbtest;

import android.support.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GatherMovieDetails {
    private int id;
    private String movieURL;

    RetrofitCreator rc = new RetrofitCreator();
    final RetrofitInterface dataAPI = rc.getRetrofit().create(RetrofitInterface.class);


    public String getMovieURL(int id) {
        this.id=id;
        Call<MovieDetailsPOJO> call = dataAPI.getMovieDetails(id);
        call.enqueue(new Callback<MovieDetailsPOJO>() {
            @NonNull
            @Override
            public void onResponse (Call<MovieDetailsPOJO> call, Response<MovieDetailsPOJO> response) {

                movieURL = response.body().getPosterPath();
                System.out.println("Vypis z tridy details: "+movieURL);
            }

            @Override
            public void onFailure(Call<MovieDetailsPOJO> call, Throwable t) {
                System.out.println("onFailure");
                t.printStackTrace();
            }
        });

        return movieURL;
    }


}
