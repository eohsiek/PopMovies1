package com.example.android.popmovies1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popmovies1.data.Movie;
import com.example.android.popmovies1.data.MoviesAdapter;
import com.example.android.popmovies1.utilities.JsonUtils;
import com.example.android.popmovies1.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
//https://www.iconfinder.com/icons/1055062/film_film_reel_movie_reel_icon#size=128
//https://www.raywenderlich.com/127544/android-gridview-getting-started

public class MainActivity extends AppCompatActivity {

    private ProgressBar mLoadingIndicator;
    private TextView mResponseView;
    private TextView mErrorMessage;
    public Movie[] movies;
    public GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.gridview);
       // mLoadingIndicator = (ProgressBar) findViewById(R.id.progressBar);
       // mResponseView = (TextView) findViewById(R.id.responseView);
       // mErrorMessage = (TextView) findViewById(R.id.errorMessage);
       Log.d("myTag", "ApplicationStarted");
       getMovies("popular");
    }

    private void getMovies(String sort) {
        URL movieURL = NetworkUtils.buildUrl(sort);
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
                movies = JsonUtils.parseMovieJson(movieResults);
                showJsonDataView(movies);

            } else {
                // COMPLETED (16) Call showErrorMessage if the result is null in onPostExecute
                showErrorMessage();
            }
        }

    }

    private  void showJsonDataView(final Movie[] movies) {
        // First, make sure the error is invisible
       // mErrorMessage.setVisibility(View.INVISIBLE);
        // Then, make sure the JSON data is visible
        //mResponseView.setVisibility(View.VISIBLE);

        final MoviesAdapter moviesAdapter = new MoviesAdapter(this, movies);
        gridView.setAdapter(moviesAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Movie movie = (Movie) moviesAdapter.getItem(position);
                launchDetailActivity(position, movie);
            }
        });
    }

    private void launchDetailActivity(int position, Movie movie) {
        Intent intent = new Intent(this, MovieDetail.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    private  void showErrorMessage() {
        // First, hide the currently visible data
       // mErrorMessage.setVisibility(View.INVISIBLE);
        // Then, show the error
        //mResponseView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.sort, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    // COMPLETED (7) Override onOptionsItemSelected to handle clicks on the refresh button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sortpopular) {
            getMovies("popular");
            return true;
        }
        if (id == R.id.action_sortrating) {
            getMovies("top_rated");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
