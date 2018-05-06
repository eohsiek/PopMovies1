package com.example.android.popmovies1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MovieDetail extends AppCompatActivity {
    //public static String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        TextView titleTextView = findViewById(R.id.textview_movie_title);
        titleTextView.setText(title);
    }
}
