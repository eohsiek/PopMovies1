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

import com.example.android.popmovies1.data.Movie;

import com.example.android.popmovies1.data.Trailer;
import com.example.android.popmovies1.data.TrailersAdapter;
import com.example.android.popmovies1.databinding.ActivityMovieDetailBinding;
import com.example.android.popmovies1.utilities.JsonUtils;
import com.example.android.popmovies1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

public class MovieDetail extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TrailersAdapter trailersAdapter;
    private Trailer[] trailers;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        ActivityMovieDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewTrailers);
        trailersAdapter = new TrailersAdapter();
        recyclerView.setAdapter(trailersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("movie");
        binding.setMovie(movie);
        Log.d("MovieID", String.valueOf(movie.getId()));

        Picasso.with(this).load(movie.getPosterURI()).placeholder(R.drawable.placeholder).error(R.drawable.notfound).into(binding.imageviewMoviePoster);
        Picasso.with(this).load(movie.getBackdropURI()).placeholder(R.drawable.placeholder).error(R.drawable.notfound).into(binding.imageviewBackdrop);

        getTrailers(String.valueOf(movie.getId()));
        //getReviews(String.valueOf(movie.getId()));

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
            return jsonstring;
        }

        @Override
        protected void onPostExecute(String jsonstring) {

            if (jsonstring != null && !jsonstring.equals("")) {
                trailers = JsonUtils.parseTrailerJson(jsonstring);
                trailersAdapter.setTrailerData(mContext, trailers);
                //showTrailers(trailers);

            } else {
               // showTrailerErrorMessage();

            }
        }
    }



}
