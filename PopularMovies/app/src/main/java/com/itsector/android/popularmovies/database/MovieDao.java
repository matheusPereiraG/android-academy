package com.itsector.android.popularmovies.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.itsector.android.popularmovies.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert
    void addFavorite(Movie movie);

    @Delete
    void deleteFavorite(Movie movie);

    @Query("SELECT EXISTS (SELECT * FROM movie WHERE :movieId = id)")
    Boolean findMovie(int movieId);

    @Query("SELECT * FROM movie")
    List<Movie> getFavorites();
}
