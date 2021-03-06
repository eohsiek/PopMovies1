package com.example.android.popmovies1.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    void insert(Favorite favorite);

    @Query("SELECT * FROM Favorite WHERE movieId = :movieId")
    LiveData<Favorite> fetchFavoritesbyMovieId (String movieId);

    @Query("SELECT * FROM Favorite ")
    LiveData<List<Favorite>> fetchAllFavorites ();

    @Delete
    void deleteFavorite (Favorite favorite);
}
