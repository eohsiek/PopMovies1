package com.example.android.popmovies1.utilities;

import android.util.Log;

import com.example.android.popmovies1.data.Movie;
import com.example.android.popmovies1.data.Review;
import com.example.android.popmovies1.data.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    private static Movie movieJsontoObject(String json) {
        try {
            Movie movie = new Movie();
            JSONObject movieJson = new JSONObject(json);
            movie.setId(movieJson.optString("id"));
            movie.setTitle(movieJson.optString("title"));
            movie.setPoster_path(movieJson.optString("poster_path"));
            movie.setBackdrop_path(movieJson.optString("backdrop_path"));
            movie.setOverview(movieJson.optString("overview"));
            movie.setRelease_date(movieJson.optString("release_date"));
            movie.setVote_average(movieJson.optInt("vote_average"));
            return movie;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static Movie[] parseMovieJson(String json) {
        try {
            JSONObject movieJson = new JSONObject(json);
            JSONArray results = movieJson.optJSONArray("results");
            Movie[] movieArray = new Movie[results.length()];
            for (int i = 0; i < results.length(); i++) {
                movieArray[i] = movieJsontoObject(results.optString(i));
            }
            return movieArray;
        }  catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static Trailer trailerJsontoObject(String json) {
        try {
            Trailer trailer = new Trailer();
            JSONObject trailerJson = new JSONObject(json);
            trailer.setSource(trailerJson.optString("source"));
            trailer.setName(trailerJson.optString("name"));
            trailer.setSize(trailerJson.optString("size"));
            trailer.setType(trailerJson.optString("type"));
            return trailer;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static Trailer[] parseTrailerJson(String json) {
        try {
            JSONObject trailerJson = new JSONObject(json);
            JSONArray results = trailerJson.optJSONArray("youtube");
            Trailer[] trailerArray = new Trailer[results.length()];
            for (int i = 0; i < results.length(); i++) {
                trailerArray[i] = trailerJsontoObject(results.optString(i));
            }
            return trailerArray;
        }  catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static Review reviewJsontoObject(String json) {
        try {
            Review review = new Review();
            JSONObject trailerJson = new JSONObject(json);
            review.setId(trailerJson.optString("id"));
            review.setAuthor(trailerJson.optString("author"));
            review.setContent(trailerJson.optString("content"));
            review.setUrl(trailerJson.optString("url"));
            return review;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static Review[] parseReviewJson(String json) {
        try {
            JSONObject reviewJson = new JSONObject(json);
            JSONArray results = reviewJson.optJSONArray("results");
            Review[] reviewArray = new Review[results.length()];
            for (int i = 0; i < results.length(); i++) {
                reviewArray[i] = reviewJsontoObject(results.optString(i));
            }
            return reviewArray;
        }  catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
