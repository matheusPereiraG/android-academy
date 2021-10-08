package com.itsector.android.popularmovies.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.model.MovieCollection;
import com.itsector.android.popularmovies.model.Review;
import com.itsector.android.popularmovies.model.ReviewCollection;
import com.itsector.android.popularmovies.model.TrailerCollection;
import com.itsector.android.popularmovies.network.MovieAPI;
import com.itsector.android.popularmovies.network.RequestInterceptor;
import com.itsector.android.popularmovies.utils.ConcurrentExecutor;

import java.text.DateFormat;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private static Repository instance;

    public static final String BASE_URL = "https://api.themoviedb.org/";
    public static final String TAG = "Repository";
    public static int CURRENT_PAGE = 1;
    public static int ITEMS_PER_PAGE = 20;
    //TODO: Max pages?
    private Retrofit retrofit;
    private MovieAPI service;

    private Repository() {
        initRetrofit();
    }

    private void initRetrofit() {
        RequestInterceptor interceptor = new RequestInterceptor();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);
        OkHttpClient client = builder.build();

        Gson gson = new GsonBuilder()
                .setDateFormat(DateFormat.FULL, DateFormat.FULL)
                .create();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.service = retrofit.create(MovieAPI.class);
    }

    public static synchronized Repository getInstance() {
        if (instance == null)
            instance = new Repository();
        return instance;
    }

    /*                         Retrofit calls                                       */
    public void getPopularMovies(MutableLiveData<MovieCollection> mMovieCol) {
        Call<MovieCollection> call = service.getPopularMovies(CURRENT_PAGE);
        call.enqueue(new Callback<MovieCollection>() {
            @Override
            public void onResponse(Call<MovieCollection> call, Response<MovieCollection> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, String.valueOf(response.code()));
                    Log.e(TAG, response.errorBody().toString());
                } else {
                    MovieCollection newCol;
                    MovieCollection oldCol;
                    try {
                        newCol = response.body();
                        newCol.setCollectionType("popular");
                        oldCol = mMovieCol.getValue();
                        if (oldCol == null)
                            mMovieCol.setValue(newCol);
                        else {
                            oldCol.mergeMovieCollection(newCol);
                            mMovieCol.setValue(oldCol);
                        }

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieCollection> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getTopRatedMovies(MutableLiveData<MovieCollection> mMovieCol) {
        Call<MovieCollection> call = service.getTopRatedMovies(CURRENT_PAGE);
        call.enqueue(new Callback<MovieCollection>() {
            @Override
            public void onResponse(Call<MovieCollection> call, Response<MovieCollection> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, String.valueOf(response.code()));
                    Log.e(TAG, response.errorBody().toString());
                } else {
                    MovieCollection newCol;
                    MovieCollection oldCol;
                    try {
                        newCol = response.body();
                        newCol.setCollectionType("top_rated");
                        oldCol = mMovieCol.getValue();
                        if (oldCol == null)
                            mMovieCol.setValue(newCol);
                        else {
                            oldCol.mergeMovieCollection(newCol);
                            mMovieCol.setValue(oldCol);
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieCollection> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getMovieDetails(MutableLiveData<Movie> mMovieDetails, int id) {
        Call<Movie> call = service.getMovieDetails(id);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, String.valueOf(response.code()));
                    Log.e(TAG, response.errorBody().toString());
                } else {
                    Movie movieDetails;
                    try {
                        movieDetails = response.body();
                        mMovieDetails.setValue(movieDetails);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getMovieTrailers(MutableLiveData<TrailerCollection> mMovieTrailers, int id) {
        Call<TrailerCollection> call = service.getMovieTrailers(id);
        call.enqueue(new Callback<TrailerCollection>() {
            @Override
            public void onResponse(Call<TrailerCollection> call, Response<TrailerCollection> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, String.valueOf(response.code()));
                    Log.e(TAG, response.errorBody().toString());
                } else {
                    TrailerCollection movieTrailers;
                    try {
                        movieTrailers = response.body();
                        mMovieTrailers.setValue(movieTrailers);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TrailerCollection> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getMovieReviews(MutableLiveData<ReviewCollection> mMovieReviews, int id) {
        int nextPage = 1;
        if (mMovieReviews.getValue() != null) {
            nextPage = mMovieReviews.getValue().getResults().size() / ITEMS_PER_PAGE + 1;
            if(mMovieReviews.getValue().getPage() == nextPage)
                return;
            if (nextPage > mMovieReviews.getValue().getTotalPages())
                return;
        }

        Call<ReviewCollection> call = service.getMovieReviews(id, nextPage);
        call.enqueue(new Callback<ReviewCollection>() {
            @Override
            public void onResponse(Call<ReviewCollection> call, Response<ReviewCollection> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, String.valueOf(response.code()));
                    Log.e(TAG, response.errorBody().toString());
                } else {
                    ReviewCollection movieReviews;
                    try {
                        movieReviews = response.body();
                        mMovieReviews.setValue(mergeReviews(mMovieReviews.getValue(), movieReviews));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReviewCollection> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /*                         Room calls                                       */
    public void addFavorite(final Context context, final Movie toAdd){
        Runnable r = () -> MovieDatabase.getInstance(context).movieDao().addFavorite(toAdd);
        ConcurrentExecutor.getInstance().submit(r);
    }

    public void deleteFavorite(final Context context, final Movie toDelete){
        Runnable r = () -> MovieDatabase.getInstance(context).movieDao().deleteFavorite(toDelete);
        ConcurrentExecutor.getInstance().submit(r);
    }

    public void checkFavorite(final Context context, MutableLiveData<Boolean> isFav, final Movie toCheck){
        Runnable r = () -> {
            Boolean b = MovieDatabase.getInstance(context).movieDao().findMovie(toCheck.getId());
            if(Boolean.TRUE.equals(b)){
                isFav.postValue(Boolean.TRUE);
            }
        };
        ConcurrentExecutor.getInstance().submit(r);
    }

    //TODO: get favorites
    public void getFavorites(final Context context, MutableLiveData<List<Movie>> favorites){
        Runnable r = () -> {
            List<Movie> favs = MovieDatabase.getInstance(context).movieDao().getFavorites();
            if(favs != null)
                favs.forEach(movie -> Log.v("MOVIE IN DB", movie.getTitle()));
        };
        ConcurrentExecutor.getInstance().submit(r);
    }

    private ReviewCollection mergeReviews(ReviewCollection oldR, ReviewCollection newR) {
        if (oldR == null) return newR;

        List<Review> tempList = oldR.getResults();
        tempList.addAll(newR.getResults());
        oldR.setResults(tempList);

        oldR.setPage(newR.getPage());
        oldR.setTotalPages(newR.getTotalPages());
        oldR.setTotalResults(newR.getTotalResults());

        return oldR;
    }
}
