package com.itsector.android.popularmovies.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.itsector.android.popularmovies.R;
import com.itsector.android.popularmovies.adapter.MovieAdapter;
import com.itsector.android.popularmovies.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private MovieAdapter mMovieAdapter;
    private MainActivityViewModel mainActivityViewModel;
    private RecyclerView mRv;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRv = findViewById(R.id.rv_movies);
        mMovieAdapter = new MovieAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        mRv.setAdapter(mMovieAdapter);
        mRv.setLayoutManager(layoutManager);


        mProgressBar = findViewById(R.id.progress_bar);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getMovieCollection().observe(this, collection -> {
            mMovieAdapter.addToMovieList(collection.getResults());
            int moviePackSize = collection.getResults().size();
            int startIndexNewMovies = collection.getPage() * moviePackSize - moviePackSize;
            int itemCount = moviePackSize * collection.getPage();
            mMovieAdapter.notifyItemRangeChanged(startIndexNewMovies, itemCount);
            mProgressBar.setVisibility(View.GONE);
        });

        mRv.addOnScrollListener(new MainActivityViewModel.ScrollListener(layoutManager,
                mainActivityViewModel));
    }
}