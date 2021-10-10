package com.itsector.android.popularmovies.network;

import android.net.Uri;
import android.util.Log;

import com.bumptech.glide.module.AppGlideModule;
import com.itsector.android.popularmovies.BuildConfig;

public class GlideModule extends AppGlideModule {
    public final static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    public final static String IMAGE_SIZE = "w780";

    public static String buildUri(String posterPath){
        return IMAGE_BASE_URL + IMAGE_SIZE + posterPath;
    }
}
