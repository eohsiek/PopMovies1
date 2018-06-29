package com.example.android.popmovies1.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.android.popmovies1.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    final private static String MOVIEDB_API_BASE_URI =
            "http://api.themoviedb.org/3/movie/";
    final private static String PARAM_API_KEY = "api_key";
    final private static String API_KEY = BuildConfig.MovieDBAPIKey;
    private static String SUBDIRECTORY  = "popular";

    //http://api.themoviedb.org/3/movie/383498/videos?api_key=blah
    //http://api.themoviedb.org/3/movie/383498/reviews?api_key=blah

    public static URL buildUrl(String sort) {
        SUBDIRECTORY = sort;
        Uri builtUri = Uri.parse(MOVIEDB_API_BASE_URI).buildUpon()
                .appendPath(SUBDIRECTORY)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildMovieUrl(String type, String movieid) {
        Uri builtUri = Uri.parse(MOVIEDB_API_BASE_URI).buildUpon()
                .appendPath(movieid)
                .appendPath(type)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {

        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(10000);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }
}
