package com.itsector.android.popularmovies.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;

import com.itsector.android.popularmovies.model.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends androidx.room.RoomDatabase {
    private static final String DB_NAME = "movie_db";
    private static final Object LOCK = new Object();
    private static final String LOG_TAG = MovieDatabase.class.getSimpleName();
    private static MovieDatabase instance;

    public static MovieDatabase getInstance(Context context){
        if(instance == null)
            synchronized (LOCK){
                Log.d(LOG_TAG, "Creating new database instance");
                instance = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }

        Log.d(LOG_TAG, "Getting the database instance");
        return instance;
    }

    public abstract MovieDao movieDao();

}
