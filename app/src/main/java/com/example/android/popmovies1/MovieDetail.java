package com.example.android.popmovies1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popmovies1.data.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("movie");

        TextView titleTextView = findViewById(R.id.textview_movie_title);
        TextView voteAverageTextView = findViewById(R.id.textview_vote_average);
        TextView overviewTextView = findViewById(R.id.textview_overview);
        TextView releaseDateTextView = findViewById(R.id.textview_release_date);
        ImageView posterImageView = findViewById(R.id.imageview_movie_poster);

        titleTextView.setText(movie.getTitle());
        voteAverageTextView.setText(movie.getVote());
        releaseDateTextView.setText(movie.getRelease_date());
        overviewTextView.setText(movie.getOverview());
        Picasso.with(this).load(movie.getPosterURI()).into(posterImageView);
    }
}
