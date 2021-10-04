package com.itsector.android.popularmovies.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.itsector.android.popularmovies.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private MovieAdapter mMovieAdapter;
    private MainActivityViewModel mainActivityViewModel;
    private RecyclerView mRv;
    private Menu mMenu;
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.sort_by_popular){
            item.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_sort_by_check));
            MenuItem otherOption = (MenuItem) mMenu.findItem(R.id.sort_by_top);
            otherOption.setIcon(0);
        }
        if(item.getItemId() == R.id.sort_by_top){
            item.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_sort_by_check));
            MenuItem otherOption = (MenuItem) mMenu.findItem(R.id.sort_by_popular);
            otherOption.setIcon(0);
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