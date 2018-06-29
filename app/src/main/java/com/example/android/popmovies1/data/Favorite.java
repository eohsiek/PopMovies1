package com.example.android.popmovies1.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Favorite {
    @NonNull
    @PrimaryKey
    private String movieId;
    private String title;
    private String poster_path;

    public Favorite() {
    }

    public String getMovieId() { return movieId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPoster_path() {
        return poster_path;
    }
    public void setPoster_path( String poster_path ) {
        this.poster_path = poster_path;
    }

    public Movie convertToMovieObject() {
        Movie movie = new Movie();
        movie.setPoster_path(this.poster_path);
        movie.setId(this.movieId);
        movie.setTitle(this.title);
        return movie;
    }
}
