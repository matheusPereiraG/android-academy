package com.itsector.android.popularmovies.network;

import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.model.MovieCollection;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//TODO: Change path for API version with a static field

public interface MovieAPI {
    @GET("3/movie/top_rated")
    Call<MovieCollection> getTopRatedMovies();

    @GET("3/movie/popular")
    Call<MovieCollection> getPopularMovies();
}
