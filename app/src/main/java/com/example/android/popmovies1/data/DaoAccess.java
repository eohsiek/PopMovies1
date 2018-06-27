package com.example.android.popmovies1.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface DaoAccess {

    @Insert
    void insert(Favorite favorite);

    @Query("SELECT * FROM Favorite WHERE movieId = :movieId")
    Favorite fetchFavoritesbyMovieId (int movieId);

    @Delete
    void deleteFavorite (Favorite movies);
}
