package com.example.android.popmovies1;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popmovies1.data.Movie;

import com.example.android.popmovies1.databinding.ActivityMovieDetailBinding;
import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("movie");
        binding.setMovie(movie);

        Picasso.with(this).load(movie.getPosterURI()).into(binding.imageviewMoviePoster);
        Picasso.with(this).load(movie.getBackdropURI()).into(binding.imageviewBackdrop);
    }
}
