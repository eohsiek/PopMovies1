package com.example.android.popmovies1.utilities;

import android.util.Log;

import com.example.android.popmovies1.data.Movie;
import com.example.android.popmovies1.data.MoviesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    private static Movie movieJsontoObject(String json) {
        try {
            Movie movie = new Movie();
            JSONObject movieJson = new JSONObject(json);
            movie.setTitle(movieJson.optString("title"));
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
