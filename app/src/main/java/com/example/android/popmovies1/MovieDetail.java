package com.example.android.popmovies1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MovieDetail extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
    }
}
