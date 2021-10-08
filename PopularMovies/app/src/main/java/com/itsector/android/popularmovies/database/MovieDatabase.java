package com.itsector.android.popularmovies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import com.itsector.android.popularmovies.model.Movie;

@Database(entities = Movie.class, exportSchema = false, version = 1)
public abstract class MovieDatabase extends androidx.room.RoomDatabase {
    private static final String DB_NAME = "movie_db";
    private static MovieDatabase instance;

    public static synchronized MovieDatabase getInstance(Context context){
        if(instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        return instance;
    }

    public abstract MovieDao movieDao();

}
