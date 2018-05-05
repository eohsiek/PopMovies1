package com.example.android.popmovies1.data;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.popmovies1.R;

public class MoviesAdapter extends BaseAdapter {
    private final Context mContext;
    private final Movie[] movies;

    public MoviesAdapter(Context context, Movie[] movies) {
        this.mContext = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.length;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
        final Movie movie = movies[position];

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.moviehomelayout, null);
        }

        // 3

        final TextView nameTextView = convertView.findViewById(R.id.textview_movie_title);


        // 4
        nameTextView.setText(movie.getTitle());

        return convertView;
    }
}
