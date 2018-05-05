package com.example.android.popmovies1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popmovies1.data.Movie;
import com.example.android.popmovies1.data.MoviesAdapter;
import com.example.android.popmovies1.utilities.JsonUtils;
import com.example.android.popmovies1.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mLoadingIndicator;
    private TextView mResponseView;
    private TextView mErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // mLoadingIndicator = (ProgressBar) findViewById(R.id.progressBar);
       // mResponseView = (TextView) findViewById(R.id.responseView);
       // mErrorMessage = (TextView) findViewById(R.id.errorMessage);
       Log.d("myTag", "ApplicationStarted");
       getMovies();


    }

    private void getMovies() {
        URL movieURL = NetworkUtils.buildUrl();
        new GetPopularMoviesTask().execute(movieURL);
    }

    public class GetPopularMoviesTask extends AsyncTask<URL, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
           // mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String movieResults = null;
            try {
                movieResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieResults;
        }

        @Override
        protected void onPostExecute(String movieResults) {
            // COMPLETED (27) As soon as the loading is complete, hide the loading indicator
            //mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieResults != null && !movieResults.equals("")) {
                // COMPLETED (17) Call showJsonDataView if we have valid, non-null results
                Movie[] movies = JsonUtils.parseMovieJson(movieResults);
                showJsonDataView(movies);

            } else {
                // COMPLETED (16) Call showErrorMessage if the result is null in onPostExecute
                showErrorMessage();
            }
        }

    }

    private  void showJsonDataView(Movie[] movies) {
        // First, make sure the error is invisible
       // mErrorMessage.setVisibility(View.INVISIBLE);
        // Then, make sure the JSON data is visible
        //mResponseView.setVisibility(View.VISIBLE);
        GridView gridView = findViewById(R.id.gridview);
        MoviesAdapter moviesAdapter = new MoviesAdapter(this, movies);
        gridView.setAdapter(moviesAdapter);
    }

    private  void showErrorMessage() {
        // First, hide the currently visible data
       // mErrorMessage.setVisibility(View.INVISIBLE);
        // Then, show the error
        //mResponseView.setVisibility(View.VISIBLE);
    }
}
