package com.example.android.popmovies1;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popmovies1.data.Favorite;
import com.example.android.popmovies1.data.FavoriteDatabase;
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

public class MovieDetail extends AppCompatActivity  {

    private RecyclerView recyclerViewTrailers;
    private RecyclerView recyclerViewReviews;
    private TextView errorMessageReview;
    private TextView errorMessageTrailer;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;
    private Button favoritesButton;
    private ImageView hearticon;
    private Trailer[] trailers;
    private Review[] reviews;
    private Context mContext;
    private static final String DATABASE_NAME = "favoritesdb";
    private FavoriteDatabase favoriteDatabase;
    private String movieId;
    private Movie movie;

    public static final String EXTRA_FAVORITE_ID = "extraFavoriteId";
    public static final String INSTANCE_FAVORITE_ID = "instanceFavoriteId";


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

        favoritesButton = findViewById(R.id.button_favorite);
        hearticon = findViewById(R.id.imageview_favorite);

        //favoriteDatabase = Room.databaseBuilder(getApplicationContext(), FavoriteDatabase.class, DATABASE_NAME).build();
        favoriteDatabase = FavoriteDatabase.getDatabase(getApplicationContext());

        Intent intent = getIntent();
        movie = intent.getParcelableExtra("movie");
        movieId = movie.getId();
        binding.setMovie(movie);

        checkFavorite();

        Picasso.with(this).load(movie.getPosterURI()).placeholder(R.drawable.placeholder).error(R.drawable.notfound).into(binding.imageviewMoviePoster);
        Picasso.with(this).load(movie.getBackdropURI()).placeholder(R.drawable.placeholder).error(R.drawable.notfound).into(binding.imageviewBackdrop);

        getTrailers(String.valueOf(movie.getId()));
        getReviews(String.valueOf(movie.getId()));

        favoritesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button)v;
                String buttonText = b.getText().toString();
                buttonText = buttonText.replaceAll("\\n","");
                buttonText = buttonText.toUpperCase();
                if(buttonText.equals("ADD TO FAVORITES")) {
                    setToRemoveFavorite();
                    addFavorite();
                }
                else {
                    setToAddFavorite();
                    removeFavorite();
                }
            }
        });
    }

    private void addFavorite() {
        final Favorite favorite = new Favorite();
        favorite.setMovieId(movie.getId());
        favorite.setTitle(movie.getTitle());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                favoriteDatabase.daoAccess().insert(favorite);
            }
        });
    }

    private void removeFavorite() {
        final Favorite favorite = new Favorite();
        favorite.setMovieId(movie.getId());
        favorite.setTitle(movie.getTitle());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                favoriteDatabase.daoAccess().deleteFavorite(favorite);
            }
        });
    }

    private void checkFavorite() {
        new getFavorite().execute(movieId);
    }

    private void setToAddFavorite() {
        favoritesButton.setText(R.string.add_to_favorites);
        hearticon.setVisibility(View.INVISIBLE);
    }

    private void setToRemoveFavorite() {
        favoritesButton.setText(R.string.remove_from_favorites);
        hearticon.setVisibility(View.VISIBLE);
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
/*
    public class insertFavorite extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String movieId = params[0];
            Favorite favorite =new Favorite();
            favorite.setMovieId(movieId);
            favoriteDatabase.daoAccess().insert(favorite);
            return null;
        }
    }

    public class deleteFavorite extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String movieId = params[0];
            Favorite favorite =new Favorite();
            favorite.setMovieId(movieId);
            favoriteDatabase.daoAccess () . deleteFavorite (favorite);
            return null;
        }
    }
*/
    public class getFavorite extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String movieId = params[0];
            Favorite favoritefromDB = favoriteDatabase.daoAccess () . fetchFavoritesbyMovieId (movieId);
            if(favoritefromDB == null){
                setToAddFavorite();
            }
            else {
                setToRemoveFavorite();
            }
            return null;
        }
    }
}
