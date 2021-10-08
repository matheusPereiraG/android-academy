package com.itsector.android.popularmovies.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.itsector.android.popularmovies.R;
import com.itsector.android.popularmovies.adapter.MovieAdapter;
import com.itsector.android.popularmovies.database.Repository;
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

        int sortOption = getSavedPreference();
        Repository.CURRENT_PAGE = 1;

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.setContext(this);
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
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            mLayoutManager = new GridLayoutManager(this, 3);
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mLayoutManager = new GridLayoutManager(this, 2);
        }

        mRv.setAdapter(mMovieAdapter);
        mRv.setLayoutManager(mLayoutManager);

        mRv.addOnScrollListener(new MainActivityViewModel.ScrollListener(mLayoutManager,
                mainActivityViewModel));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sort_by_popular) {
            if (mainActivityViewModel.getSelectedSortOption() != 0) {
                item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_sort_by_check));
                MenuItem otherOption = (MenuItem) mMenu.findItem(R.id.sort_by_top);
                otherOption.setIcon(0);
                mRv.smoothScrollToPosition(0);
                mLayoutManager.scrollToPositionWithOffset(0, 0);

                Repository.CURRENT_PAGE = 1;
                mainActivityViewModel.setSelectedSortOption(0);
                mainActivityViewModel.loadMovies();
                savePreference(0);
            }

        }
        if (item.getItemId() == R.id.sort_by_top) {
            if (mainActivityViewModel.getSelectedSortOption() != 1) {
                item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_sort_by_check));
                MenuItem otherOption = (MenuItem) mMenu.findItem(R.id.sort_by_popular);
                otherOption.setIcon(0);
                mRv.smoothScrollToPosition(0);
                mLayoutManager.scrollToPositionWithOffset(0, 0);

                Repository.CURRENT_PAGE = 1;
                mainActivityViewModel.setSelectedSortOption(1);
                mainActivityViewModel.loadMovies();
                savePreference(1);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Function that saves preferable sorting option
     *
     * @param preferenceValue (0 for popular movies, 1 for top rated)
     */
    private void savePreference(int preferenceValue) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.preference_sort_key), preferenceValue);
        editor.apply();
    }

    private int getSavedPreference() {
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        int defaultValue = getResources().getInteger(R.integer.sort_by_default_key);
        return pref.getInt(getString(R.string.preference_sort_key), defaultValue);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        int prefOption = getSavedPreference();
        mMenu = menu;
        if(prefOption == 0){
            MenuItem item = mMenu.findItem(R.id.sort_by_popular);
            item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_sort_by_check));
            MenuItem otherOption = (MenuItem) mMenu.findItem(R.id.sort_by_top);
            otherOption.setIcon(0);
        }
        else {
            MenuItem item = mMenu.findItem(R.id.sort_by_top);
            item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_sort_by_check));
            MenuItem otherOption = (MenuItem) mMenu.findItem(R.id.sort_by_popular);
            otherOption.setIcon(0);
        }

        return true;
    }
}