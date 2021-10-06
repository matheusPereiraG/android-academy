package com.itsector.android.popularmovies.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itsector.android.popularmovies.R;
import com.itsector.android.popularmovies.model.Trailer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrailerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrailerFragment extends Fragment {

    private Trailer mTrailer;
    private TextView mTrailerNameTv;

    public TrailerFragment() {
        // Required empty public constructor
    }

    /*public static TrailerFragment newInstance(String param1, String param2) {
        TrailerFragment fragment = new TrailerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

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
        mTrailerNameTv = getView().findViewById(R.id.trailer_name_tv);
        mTrailerNameTv.setText(mTrailer.getName());
    }
}