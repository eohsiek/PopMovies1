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

    private final MoviesAdapterOnClickHandler mClickHandler;

    public interface MoviesAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MoviesAdapter(MoviesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        public final ImageView moviePoster;

        public MoviesAdapterViewHolder(View view) {
            super(view);
            moviePoster = (ImageView) view.findViewById(R.id.imageview_movie_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = movies[adapterPosition];
            mClickHandler.onClick(movie);
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

    public void setMovieData(Context context, Movie[] movies) {
        this.movies = movies;
        this.mContext = context;
        notifyDataSetChanged();
    }

}
