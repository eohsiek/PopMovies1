package com.example.android.popmovies1.data;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popmovies1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private Movie[] movies;
    private Context mContext;

    public MoviesAdapter(Context mContext, Movie[] movies) {
        this.mContext = mContext;
        this.movies = movies;
    }

    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder {

        public final ImageView moviePoster;

        public MoviesAdapterViewHolder(View view) {
            super(view);
            moviePoster = (ImageView) view.findViewById(R.id.imageview_movie_poster);
        }
    }

    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.moviehomelayout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder moviesAdapterViewHolder, int position) {
        Movie movie = movies[position];
        Picasso.with(mContext).load(movie.getPosterURI()).into(moviesAdapterViewHolder.moviePoster);
    }

    @Override
    public int getItemCount() {
        if (null == movies) return 0;
        return movies.length;
    }


    @Override
    public long getItemId(int position) {
        return (long) movies[position].getId();
    }

}
