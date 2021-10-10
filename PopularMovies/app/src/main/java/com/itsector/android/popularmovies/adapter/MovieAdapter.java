package com.itsector.android.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.itsector.android.popularmovies.R;
import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.network.GlideModule;
import com.itsector.android.popularmovies.view.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
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
        String url = GlideModule.buildUri(m.getPosterPath());
        Glide.with(mContext)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target,
                                                boolean isFirstResource) {
                        Log.e("TAG", "Error loading image", e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.mPoster);
        holder.setMovie(m);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public void setMoviesList(List<Movie> results) {
        mMovieList = results;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mPoster;
        Movie mMovie;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mPoster = itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra("Movie",mMovie);
            mContext.startActivity(intent);
        }

        public void setMovie(Movie m) {
            mMovie = m;
        }
    }
}
