package com.mobile.petrk.moviedbtest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface RetrofitInterface {
    @GET("changes?api_key=6b2be7bb3d0eba20488bb3c3d9a6e454&page=1/")
    Call<MovieListPOJO> getMovieInfo();

    @GET("{movieId}?api_key=6b2be7bb3d0eba20488bb3c3d9a6e454&language=en-US")
    Call<MovieDetailsPOJO> getMovieDetails(@Path("movieId") int movieId);

}
