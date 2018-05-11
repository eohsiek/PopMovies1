package com.example.android.popmovies1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class MainActivity extends AppCompatActivity   implements MoviesAdapter.MoviesAdapterOnClickHandler {

    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private TextView errorMessage;
    private ProgressBar loadingIndicator;
    private Movie[] movies;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycleViewMovies);
        moviesAdapter = new MoviesAdapter(this);
        recyclerView.setAdapter(moviesAdapter);
        GridAutofitLayoutManager gridLayoutManager = new GridAutofitLayoutManager(this, 500);
        recyclerView.setLayoutManager(gridLayoutManager);


        /* This TextView is used to display errors and will be hidden if there are no errors */
        errorMessage = (TextView) findViewById(R.id.errorMessage);

       getMovies("popular");
    }

    private void getMovies(String sort) {
        URL movieURL = NetworkUtils.buildUrl(sort);
        new GetPopularMoviesTask().execute(movieURL);
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetail.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
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
                moviesAdapter.setMovieData(mContext, movies);
                showJsonDataView(movies);

            } else {
                // COMPLETED (16) Call showErrorMessage if the result is null in onPostExecute
                showErrorMessage();
            }
        }

    }

    private  void showJsonDataView(final Movie[] movies) {
        // First, make sure the error is invisible
        errorMessage.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        // Then, make sure the JSON data is visible
        //mResponseView.setVisibility(View.VISIBLE);

    }

    private  void showErrorMessage() {
        // First, hide the currently visible data
        errorMessage.setVisibility(View.INVISIBLE);
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
