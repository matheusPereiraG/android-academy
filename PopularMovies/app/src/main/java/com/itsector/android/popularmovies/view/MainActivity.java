package com.itsector.android.popularmovies.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.itsector.android.popularmovies.BuildConfig;
import com.itsector.android.popularmovies.R;
import com.itsector.android.popularmovies.adapter.MovieAdapter;
import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mMovieRV;
    private MovieAdapter mMovieAdapter;
    private MainActivityViewModel mainActivityViewModel;
    private List<Movie> listOfMovies = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getMovieList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                listOfMovies = movies;
            }
        });
    }
}