package com.itsector.android.popularmovies.adapter;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.itsector.android.popularmovies.R;
import com.itsector.android.popularmovies.model.Movie;
import com.itsector.android.popularmovies.model.Review;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private Context mContext;
    List<Review> mReviewList;
    static final int CONTENT_REVIEW_MAX_BYTES = 300;

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

    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Review mReview;
        TextView mAuthorTv;
        TextView mCreatedAtTv;
        TextView mReviewContent;
        Button mReadMore;
        Button mShowLess;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            mAuthorTv = itemView.findViewById(R.id.author_name_tv);
            mCreatedAtTv = itemView.findViewById(R.id.review_created_tv);
            mReviewContent = itemView.findViewById(R.id.review_content_tv);
            mReadMore = itemView.findViewById(R.id.read_more_btn);
            mShowLess = itemView.findViewById(R.id.show_less_btn);
        }

        public void bind(Review r) {
            mReview = r;
            mShowLess.setOnClickListener(this);
            mReadMore.setOnClickListener(this);
            mAuthorTv.setText(mReview.getAuthor());

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(mReview.getCreatedAt());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            mCreatedAtTv.setText(year + "-" + month + "-" + day);

            String content = mReview.getContent();
            if(isStringTooBig(content)){
                content = getSmallString();
                mReadMore.setVisibility(View.VISIBLE);
            }

            mReviewContent.setText(content);
        }

        private String getSmallString() {
            String content = mReview.getContent().substring(0, CONTENT_REVIEW_MAX_BYTES);
            content += " ...";
            return content;
        }

        private boolean isStringTooBig(String content) {
            return content.getBytes(StandardCharsets.UTF_8).length > CONTENT_REVIEW_MAX_BYTES;
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.read_more_btn) {
                mReviewContent.setText(mReview.getContent());
                mReadMore.setVisibility(View.GONE);
                mShowLess.setVisibility(View.VISIBLE);
            }
            if (view.getId() == R.id.show_less_btn) {
                mReviewContent.setText(getSmallString());
                mShowLess.setVisibility(View.GONE);
                mReadMore.setVisibility(View.VISIBLE);
            }

        }
    }
}
