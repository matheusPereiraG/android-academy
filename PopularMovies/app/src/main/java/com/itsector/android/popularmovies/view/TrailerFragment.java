package com.itsector.android.popularmovies.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.itsector.android.popularmovies.R;
import com.itsector.android.popularmovies.model.Trailer;

public class TrailerFragment extends Fragment implements View.OnClickListener {

    private Trailer mTrailer;
    private TextView mTrailerNameTv;
    private ImageButton mPlayBtn;

    public TrailerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTrailer = getArguments().getParcelable("Trailer");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trailer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        populateViews();
    }

    private void populateViews() {
        try {
            mTrailerNameTv = getView().findViewById(R.id.trailer_name_tv);
            mPlayBtn = getView().findViewById(R.id.play_btn);
            mTrailerNameTv.setText(mTrailer.getName());
            mPlayBtn.setOnClickListener(this);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if(mTrailer.getSite().equals("YouTube"))
            launchYoutubeVideo();
    }

    private void launchYoutubeVideo() {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + mTrailer.getKey()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + mTrailer.getKey()));
        try {
            getActivity().startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            getActivity().startActivity(webIntent);
        }
    }
}