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
    private String backdrop_path;
    private String overview;
    private String release_date;
    private float vote_average;

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
    public String getBackdrop_path() {
        return backdrop_path;
    }
    public void setBackdrop_path( String backdrop_path ) {
        this.backdrop_path = backdrop_path;
    }
    public String getOverview() {
        return overview;
    }
    public void setOverview( String overview ) {
        this.overview = overview;
    }
    public String getRelease_date() {
        return release_date;
    }
    public void setRelease_date( String release_date ) {
        this.release_date = release_date;
    }
    public float getVote_average() { return vote_average; }
    public void setVote_average( float vote_average ) {
        this.vote_average = vote_average;
    }

    public Movie convertToMovieObject() {
        Movie movie = new Movie();
        movie.setPoster_path(this.poster_path);
        movie.setId(this.movieId);
        movie.setTitle(this.title);
        movie.setBackdrop_path(this.backdrop_path);
        movie.setOverview(this.overview);
        movie.setRelease_date(this.release_date);
        movie.setVote_average(this.vote_average);
        return movie;
    }
}
