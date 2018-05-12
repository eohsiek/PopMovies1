package com.example.android.popmovies1.utilities;

import com.example.android.popmovies1.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    private static Movie movieJsontoObject(String json) {
        try {
            Movie movie = new Movie();
            JSONObject movieJson = new JSONObject(json);
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
}
