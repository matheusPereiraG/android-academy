package com.itsector.android.popularmovies.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import com.itsector.android.popularmovies.model.Movie;

@Dao
public interface MovieDao {
    @Insert
    void addFavorite(Movie movie);

    @Delete
    void deleteFavorite(Movie movie);
}
