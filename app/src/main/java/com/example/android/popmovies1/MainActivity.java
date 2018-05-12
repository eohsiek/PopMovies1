/*
App Icon from
https://www.iconfinder.com/icons/1055062/film_film_reel_movie_reel_icon#size=128
 */

package com.example.android.popmovies1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popmovies1.data.Movie;
import com.example.android.popmovies1.data.MoviesAdapter;
import com.example.android.popmovies1.utilities.JsonUtils;
import com.example.android.popmovies1.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;


public class MainActivity extends AppCompatActivity   implements MoviesAdapter.MoviesAdapterOnClickHandler {

    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private TextView errorMessage;
    private ProgressBar loadingIndicator;
    private Movie[] movies;
    private Context mContext;

    private static String SORT = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewMovies);
        moviesAdapter = new MoviesAdapter(this);
        recyclerView.setAdapter(moviesAdapter);
        GridAutofitLayoutManager gridLayoutManager = new GridAutofitLayoutManager(this, 500);
        recyclerView.setLayoutManager(gridLayoutManager);

        errorMessage = (TextView) findViewById(R.id.errorMessage);
        errorMessage.setText(getResources().getText(R.string.error_message));

        loadingIndicator = (ProgressBar) findViewById(R.id.loadingIndicator);

        if (isOnline()) {
            getMovies(SORT);
        } else {
            showErrorMessage();
        }
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
            loadingIndicator.setVisibility(View.VISIBLE);
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

            loadingIndicator.setVisibility(View.INVISIBLE);
            if (movieResults != null && !movieResults.equals("")) {
                movies = JsonUtils.parseMovieJson(movieResults);
                moviesAdapter.setMovieData(mContext, movies);
                showMovies(movies);

            } else {
                showErrorMessage();
            }
        }

    }

    private  void showMovies(final Movie[] movies) {
        errorMessage.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private  void showErrorMessage() {
        errorMessage.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sortpopular) {
            SORT = getResources().getString(R.string.sortvalue_popular);
            getMovies(SORT);
            return true;
        }
        if (id == R.id.action_sortrating) {
            SORT =  getResources().getString(R.string.sortvalue_rating);
            getMovies(SORT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("SORT", SORT);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        SORT = savedInstanceState.getString("SORT");
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo network = connectivityManager.getActiveNetworkInfo();
        return network != null && network.isConnectedOrConnecting();
    }

}
