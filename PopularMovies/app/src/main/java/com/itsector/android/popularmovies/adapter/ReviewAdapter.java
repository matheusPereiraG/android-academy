package com.itsector.android.popularmovies.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itsector.android.popularmovies.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private Context mContext;
    List<Review> mReviewList;

    public ReviewAdapter(Context context) {
        super();
        mContext = context;
        mReviewList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
