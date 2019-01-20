package com.mobile.petrk.moviedbtest;

import android.app.Application;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;

import retrofit2.Response;

public class GatherMovieIDs extends Application {


    private List<Result> results;
    private int[] idList;
    private int i = 0;


    private List<Result> doInBackground(String name) {
        RetrofitCreator rc = new RetrofitCreator();
        final RetrofitInterface dataAPI = rc.getRetrofit().create(RetrofitInterface.class);
        Call<MovieListPOJO> call = dataAPI.getMovieInfo();

        try {
            Response<MovieListPOJO> response = call.execute();
            MovieListPOJO movieListPOJO = response.body();
            results = movieListPOJO.getResults();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }

    protected void onPostExecute(String result) {
        System.out.println(result);
    }
}
    /*public int [] getMovieIds() {

        Call<MovieListPOJO> call = dataAPI.getMovieInfo();

        call.enqueue(new Callback<MovieListPOJO>()    {

        @NonNull
        @Override
        public void onResponse (Call < MovieListPOJO > call, Response < MovieListPOJO > response) {

                MovieListPOJO movieListPOJO = response.body();
                results = movieListPOJO.getResults();
                System.out.println("Vystup ve vedlejsi tride: " +results.size());

            //idList=results.toArray(Result);
                TestContainer ts = new TestContainer();

            for (Result list : results) {
                    System.out.println(list.getId());
                    try {
                    idList[i]=list.getId();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        i++;
                    }
                    //lists[i].setImgURL(movieDetails.getMovieURL(lists.getId()));
                    //System.out.println("Vypis id z pomocne metody: " + list.getId());
                    //System.out.println("Vypis adult z pomocne metody: " + list.getAdult());
                    System.out.println(i);
                    //list.setImgURL(movieDetails.getMovieURL(list.getId()));

            }
            //System.out.println(results.size());
        }
        @Override
        public void onFailure (Call < MovieListPOJO > call, Throwable t){
        System.out.println("onFailure");
        t.printStackTrace();
    }

    });

        return idList;
}

}*/
