package com.itsector.android.popularmovies.network;

import android.net.Uri;
import android.util.Log;

import com.bumptech.glide.module.AppGlideModule;
import com.itsector.android.popularmovies.BuildConfig;

public class GlideModule extends AppGlideModule {




    public static Uri buildUri(String posterPath){
        Log.v("HEYHEY", posterPath);
        return Uri.parse(MovieClient.BASE_URL)
                .buildUpon()
                .appendPath(MovieClient.API_VERSION)
                .appendPath(posterPath)
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .build();
    }
}
