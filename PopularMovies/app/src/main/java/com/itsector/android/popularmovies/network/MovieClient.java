package com.itsector.android.popularmovies.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.model.MovieCollection;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieClient {

    private static MovieClient instance;

    public static final String BASE_URL = "https://api.themoviedb.org/";
    private Retrofit retrofit;
    private MovieAPI service;
    private static MutableLiveData<List<Movie>> movies;

    private MovieClient() {
        movies = new MutableLiveData<>();

        RequestInterceptor interceptor = new RequestInterceptor();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);
        OkHttpClient client = builder.build();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(MovieAPI.class);
    }

    public static synchronized MovieClient getInstance(){
        if(instance == null)
            instance = new MovieClient();
        return instance;
    }

    public MutableLiveData<List<Movie>> getTopRatedMovies(){
        Call<MovieCollection> call = service.getPopularMovies();
        call.enqueue(new Callback<MovieCollection>() {
            @Override
            public void onResponse(Call<MovieCollection> call, Response<MovieCollection> response) {
                if(!response.isSuccessful()){
                    //TODO: Handle error codes
                }
                else {
                    response.body().getResults().forEach((movie -> Log.v(this.getClass().getSimpleName(), movie.getOriginalTitle())));
                    movies.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieCollection> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return movies;
    }

}
