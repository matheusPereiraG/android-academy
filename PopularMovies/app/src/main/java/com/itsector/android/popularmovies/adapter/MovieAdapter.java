package com.itsector.android.popularmovies.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.itsector.android.popularmovies.R;
import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.network.GlideModule;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    //TODO: List of movies field to show and manipulate

    private Context mContext;
    List<Movie> mMovieList;


    public MovieAdapter(Context context) {
        super();
        mContext = context;
        mMovieList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        Movie m = mMovieList.get(position);
        Uri uri = GlideModule.buildUri(m.getPosterPath());
        Log.v(this.getClass().getSimpleName(), uri.toString());
        Glide.with(mContext)
                .load(uri)
                .into(holder.mPoster);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public void setMovieList(List<Movie> list){
        mMovieList = list;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView mPoster;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mPoster = itemView.findViewById(R.id.iv_poster);
        }
    }
}
