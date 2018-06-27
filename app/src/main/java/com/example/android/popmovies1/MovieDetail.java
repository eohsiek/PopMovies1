package com.example.android.popmovies1;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.popmovies1.data.Movie;

import com.example.android.popmovies1.data.Review;
import com.example.android.popmovies1.data.ReviewsAdapter;
import com.example.android.popmovies1.data.Trailer;
import com.example.android.popmovies1.data.TrailersAdapter;
import com.example.android.popmovies1.databinding.ActivityMovieDetailBinding;
import com.example.android.popmovies1.utilities.JsonUtils;
import com.example.android.popmovies1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

public class MovieDetail extends AppCompatActivity {

    private RecyclerView recyclerViewTrailers;
    private RecyclerView recyclerViewReviews;
    private TextView errorMessageReview;
    private TextView errorMessageTrailer;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;
    private Trailer[] trailers;
    private Review[] reviews;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        ActivityMovieDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        recyclerViewTrailers = findViewById(R.id.recycleViewTrailers);
        trailersAdapter = new TrailersAdapter();
        recyclerViewTrailers.setAdapter(trailersAdapter);
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(mContext));

        errorMessageTrailer = findViewById(R.id.trailererror);

        recyclerViewReviews =  findViewById(R.id.recycleViewReviews);
        reviewsAdapter = new ReviewsAdapter();
        recyclerViewReviews.setAdapter(reviewsAdapter);
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(mContext));

        errorMessageReview = findViewById(R.id.reviewerror);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("movie");
        binding.setMovie(movie);
        Log.d("MovieID", String.valueOf(movie.getId()));

        Picasso.with(this).load(movie.getPosterURI()).placeholder(R.drawable.placeholder).error(R.drawable.notfound).into(binding.imageviewMoviePoster);
        Picasso.with(this).load(movie.getBackdropURI()).placeholder(R.drawable.placeholder).error(R.drawable.notfound).into(binding.imageviewBackdrop);

        getTrailers(String.valueOf(movie.getId()));
        getReviews(String.valueOf(movie.getId()));

    }

    private void getTrailers(String movieid) {
        URL trailerURL = NetworkUtils.buildMovieUrl("trailers", movieid);
        new getExtraMovieDataTask().execute(trailerURL);
    }

    private void getReviews(String movieid) {
        URL reviewsURL = NetworkUtils.buildMovieUrl("reviews", movieid);
        new getExtraMovieDataTask().execute(reviewsURL);
    }

    public class getExtraMovieDataTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String jsonstring = null;
            try {
                jsonstring = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("MovieID", String.valueOf(jsonstring));
            Log.d("MovieURL", String.valueOf(searchUrl));
            return jsonstring;
        }

        @Override
        protected void onPostExecute(String jsonstring) {

            if (jsonstring != null && !jsonstring.equals("")) {
                if(jsonstring.contains("youtube")) {
                    trailers = JsonUtils.parseTrailerJson(jsonstring);
                    trailersAdapter.setTrailerData(mContext, trailers);
                    if(trailers.length == 0) {
                        errorMessageTrailer.setVisibility(View.VISIBLE);
                    }

                }
                else {
                    reviews = JsonUtils.parseReviewJson(jsonstring);
                    reviewsAdapter.setReviewsData(mContext, reviews);
                    if(reviews.length == 0) {
                        errorMessageReview.setVisibility(View.VISIBLE);
                    }
                }

            } else {
                errorMessageReview.setVisibility(View.VISIBLE);
            }
        }
    }
}
