package com.example.android.popmovies1.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.android.popmovies1.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final private static String MOVIEDB_API_BASE_URI =
            "http://api.themoviedb.org/3/movie/popular?sort_by=popularity.desc&api_key=2dc2aabd3659e1e1e1c0ebdcd2013082";
    final private static String PARAM_API_KEY = "api_key";
    final private static String API_KEY = BuildConfig.MovieDBAPIKey;
    final private static String PARAM_SORT = "sort_by";
    final private static String SORT_BY = "popularity.desc";

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(MOVIEDB_API_BASE_URI).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_SORT, SORT_BY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d("myTag", "Got the URL");

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {

        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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
