/*
Credits:

App Icon from
https://www.iconfinder.com/icons/1055062/film_film_reel_movie_reel_icon#size=128

Heart Icon from
http://www.iconarchive.com/show/free-valentine-heart-icons-by-designbolts/Heart-icon.html

AppExecutor from
https://github.com/udacity/ud851-Exercises/blob/student/Lesson09b project
 */

package com.example.android.popmovies1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popmovies1.data.Favorite;
import com.example.android.popmovies1.data.Movie;
import com.example.android.popmovies1.data.MoviesAdapter;
import com.example.android.popmovies1.utilities.JsonUtils;
import com.example.android.popmovies1.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class MainActivity extends AppCompatActivity   implements MoviesAdapter.MoviesAdapterOnClickHandler {

    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private TextView errorMessage;
    private ProgressBar loadingIndicator;
    private Movie[] movies;
    private Context mContext;
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    private Parcelable savedRecyclerLayoutState;

    private static String SORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(SORT == null) {
            SORT = getResources().getString(R.string.sortvalue_popular);
        }
        recyclerView = findViewById(R.id.recycleViewMovies);
        moviesAdapter = new MoviesAdapter(this);
        recyclerView.setAdapter(moviesAdapter);
        GridAutofitLayoutManager gridLayoutManager = new GridAutofitLayoutManager(this, 500);
        recyclerView.setLayoutManager(gridLayoutManager);

        errorMessage =  findViewById(R.id.errorMessage);
        errorMessage.setText(getResources().getText(R.string.error_message));

        loadingIndicator =  findViewById(R.id.loadingIndicator);
        getFavorites();
        Log.i("Sort", SORT);
        if (isOnline()) {
            if(SORT.equals("favorite")) {
                filterFavorites();
            }
            else {
                getMovies(SORT);
            }
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
            if(savedRecyclerLayoutState!=null){
                recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            }
            if (movieResults != null && !movieResults.equals("")) {
                movies = JsonUtils.parseMovieJson(movieResults);
                moviesAdapter.setMovieData(mContext, movies);
                showMovies();

            } else {
                showErrorMessage();
            }
        }

    }

    private void getFavorites() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavorites().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                moviesAdapter.setFavoritesData(favorites);
            }
        });
    }

    private void filterFavorites() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavorites().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                Movie[] movieArray = new Movie[favorites.size()];
                int i = 0;
                for (Favorite favorite : favorites) {
                    movieArray[i] = favorite.convertToMovieObject();
                    i++;
                }
                if(movieArray.length > 0) {
                    moviesAdapter.setMovieData(mContext, movieArray);
                    moviesAdapter.setFavoritesData(favorites);
                    showMovies();
                }
                else {
                    errorMessage.setText(getResources().getText(R.string.no_favorites_selected_yet));
                    showErrorMessage();
                }
            }
        });
    }

    private  void showMovies() {
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
        if (id == R.id.action_favorites) {
            //default to previous sort
            SORT =  getResources().getString(R.string.sortvalue_favorite);
            filterFavorites();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("SORT", SORT);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT,
                recyclerView.getLayoutManager().onSaveInstanceState());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
        }
        SORT = savedInstanceState.getString("SORT");
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo network = connectivityManager.getActiveNetworkInfo();
        return network != null && network.isConnectedOrConnecting();
    }

}
