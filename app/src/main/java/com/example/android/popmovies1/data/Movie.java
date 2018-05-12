package com.example.android.popmovies1.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private float id;
    private String title;
    private float popularity;
    private String poster_path;
    private String original_language;
    private String original_title;
    private String backdrop_path;
    private boolean adult;
    private String overview;
    private String release_date;
    private boolean video;
    private float vote_average;
    private float vote_count;

    public Movie (){

    }


    private Movie(Parcel parcel){
        id = parcel.readFloat();
        title = parcel.readString();
        popularity = parcel.readFloat();
        poster_path = parcel.readString();
        original_language = parcel.readString();
        original_title = parcel.readString();
        backdrop_path = parcel.readString();
        adult = (Boolean) parcel.readValue(getClass().getClassLoader());
        overview = parcel.readString();
        release_date = parcel.readString();
        video = (Boolean) parcel.readValue(getClass().getClassLoader());
        vote_average = parcel.readFloat();
        vote_count = parcel.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(id);
        dest.writeString(title);
        dest.writeFloat(popularity);
        dest.writeString(poster_path);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(backdrop_path);
        dest.writeValue(adult);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeValue(video);
        dest.writeFloat(vote_average);
        dest.writeFloat(vote_count);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[0];
        }
    };

    // Getter Methods

    public String getPosterURI(){
        return "http://image.tmdb.org/t/p/w185" + this.poster_path;
    }

    public String getBackdropURI(){
        return "http://image.tmdb.org/t/p/w185" + this.backdrop_path;
    }

    public String getVote() {
        return String.valueOf(vote_average) + "/10" ;
    }

    public String getYear() {
        String str[] = this.release_date.split("-");
        return (str[0]);
    }

    public float getVote_count() {
        return vote_count;
    }

    public float getId() {
        return id;
    }

    public boolean getVideo() {
        return video;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public boolean getAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    // Setter Methods

    public void setVote_count( float vote_count ) {
        this.vote_count = vote_count;
    }

    public void setId( float id ) {
        this.id = id;
    }

    public void setVideo( boolean video ) {
        this.video = video;
    }

    public void setVote_average( float vote_average ) {
        this.vote_average = vote_average;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public void setPopularity( float popularity ) {
        this.popularity = popularity;
    }

    public void setPoster_path( String poster_path ) {
        this.poster_path = poster_path;
    }

    public void setOriginal_language( String original_language ) {
        this.original_language = original_language;
    }

    public void setOriginal_title( String original_title ) {
        this.original_title = original_title;
    }

    public void setBackdrop_path( String backdrop_path ) {
        this.backdrop_path = backdrop_path;
    }

    public void setAdult( boolean adult ) {
        this.adult = adult;
    }

    public void setOverview( String overview ) {
        this.overview = overview;
    }

    public void setRelease_date( String release_date ) {
        this.release_date = release_date;
    }


}