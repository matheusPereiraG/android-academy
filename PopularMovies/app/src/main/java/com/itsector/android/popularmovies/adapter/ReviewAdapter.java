package com.itsector.android.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itsector.android.popularmovies.R;
import com.itsector.android.popularmovies.model.Movie;
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
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ReviewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review r = mReviewList.get(position);
        holder.bind(r);
    }


    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    public void setReviewList(List<Review> results) {
        this.mReviewList = results;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView mAuthorTv;
        TextView mCreatedAtTv;
        TextView mReviewContent;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            mAuthorTv = itemView.findViewById(R.id.author_name_tv);
            mCreatedAtTv = itemView.findViewById(R.id.review_created_tv);
            mReviewContent = itemView.findViewById(R.id.review_content_tv);
        }

        public void bind(Review r) {
            mAuthorTv.setText(r.getAuthor());
            mCreatedAtTv.setText(r.getCreatedAt());
            mReviewContent.setText(r.getContent());
        }
    }
}
