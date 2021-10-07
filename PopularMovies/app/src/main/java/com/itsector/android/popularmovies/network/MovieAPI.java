package com.itsector.android.popularmovies.network;

import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.model.MovieCollection;
import com.itsector.android.popularmovies.model.TrailerCollection;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

//TODO: Change path for API version with a static field

public interface MovieAPI {
    @GET("3/movie/top_rated")
    Call<MovieCollection> getTopRatedMovies(@Query("page") int page);

    @GET("3/movie/popular")
    Call<MovieCollection> getPopularMovies(@Query("page") int page);

    @GET("3/movie/{movie_id}")
    Call<Movie> getMovieDetails(@Path("movie_id") int movieId);

    @GET("3/movie/{movie_id}/videos")
    Call<TrailerCollection> getMovieTrailers(@Path("movie_id") int movieId);

    @GET("3/movie/{movie_id}/reviews")
    Call<TrailerCollection> getMovieReviews(@Path("movie_id") int movieId, @Query("page") int page);



}
