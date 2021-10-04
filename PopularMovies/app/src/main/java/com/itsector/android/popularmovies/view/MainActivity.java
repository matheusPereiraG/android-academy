package com.itsector.android.popularmovies.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.itsector.android.popularmovies.R;
import com.itsector.android.popularmovies.adapter.MovieAdapter;
import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.network.MovieClient;
import com.itsector.android.popularmovies.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private MovieAdapter mMovieAdapter;
    private MainActivityViewModel mainActivityViewModel;
    private RecyclerView mRv;
    private GridLayoutManager mLayoutManager;
    private Menu mMenu;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewModel();

        initRecyclerView();

        mProgressBar = findViewById(R.id.progress_bar);
    }

    private void initViewModel() {
        // get preferences for sort option
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        int defaultValue = getResources().getInteger(R.integer.sort_by_default_key);
        int sortOption = pref.getInt(getString(R.string.preference_sort_key), defaultValue);

        Log.v(this.getClass().getSimpleName(), "SORT OPTION: " + sortOption);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.setSelectedSortOption(sortOption);
        mainActivityViewModel.getMovieCollection().observe(this, collection -> {
            mMovieAdapter.setMoviesList(collection.getResults());
            mMovieAdapter.notifyItemRangeChanged(collection.getNewMoviesStartIndex()
                    , collection.getItemsSize());
            mProgressBar.setVisibility(View.GONE);
        });
    }

    private void initRecyclerView() {
        mRv = findViewById(R.id.rv_movies);
        mMovieAdapter = new MovieAdapter(this);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRv.setAdapter(mMovieAdapter);
        mRv.setLayoutManager(mLayoutManager);

        mRv.addOnScrollListener(new MainActivityViewModel.ScrollListener(mLayoutManager,
                mainActivityViewModel));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sort_by_popular) {
            item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_sort_by_check));
            MenuItem otherOption = (MenuItem) mMenu.findItem(R.id.sort_by_top);
            otherOption.setIcon(0);

            mRv.smoothScrollToPosition(0);
            mLayoutManager.scrollToPositionWithOffset(0,0);

            MovieClient.CURRENT_PAGE = 1;
            mainActivityViewModel.setSelectedSortOption(0);
            mainActivityViewModel.loadMovies();
        }
        if (item.getItemId() == R.id.sort_by_top) {
            item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_sort_by_check));
            MenuItem otherOption = (MenuItem) mMenu.findItem(R.id.sort_by_popular);
            otherOption.setIcon(0);

            mRv.smoothScrollToPosition(0);
            mLayoutManager.scrollToPositionWithOffset(0,0);

            MovieClient.CURRENT_PAGE = 1;
            mainActivityViewModel.setSelectedSortOption(1);
            mainActivityViewModel.loadMovies();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        mMenu = menu;
        return true;
    }
}