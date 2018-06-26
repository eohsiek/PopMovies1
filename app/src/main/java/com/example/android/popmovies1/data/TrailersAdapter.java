package com.example.android.popmovies1.data;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popmovies1.R;
import com.example.android.popmovies1.data.Trailer;
import com.example.android.popmovies1.databinding.TrailerLayoutBinding;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersAdapterViewHolder> {

    private Trailer[] trailers;
    private Context mContext;



    public interface TrailersAdapterOnClickHandler {
        void onClick(Trailer trailer);
    }

    public TrailersAdapter() {
    }

    public class TrailersAdapterViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        private TrailerLayoutBinding binding;

        public TrailersAdapterViewHolder(TrailerLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            view.setOnClickListener(this);
        }

        public void bind(Trailer trailer) {
            binding.setTrailer(trailer);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Trailer trailer = trailers[adapterPosition];
            Uri url = Uri.parse(trailer.getYouTubeURL()); // get your url from list item or your code.
            Intent intent = new Intent(Intent.ACTION_VIEW, url);
            mContext.startActivity(intent);
        }
    }

    @Override
    public TrailersAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater  = LayoutInflater.from(context);
        TrailerLayoutBinding trailerLayoutBinding =
                TrailerLayoutBinding.inflate(layoutInflater, viewGroup, false);

        return new TrailersAdapterViewHolder(trailerLayoutBinding);
    }

    @Override
    public void onBindViewHolder(TrailersAdapterViewHolder trailersAdapterViewHolder, int position) {
        Trailer trailer = trailers[position];
        trailersAdapterViewHolder.bind(trailer);
    }

    @Override
    public int getItemCount() {
        if (null == trailers) return 0;
        return trailers.length;
    }


    public void setTrailerData(Context context, Trailer[] trailers) {
        this.trailers = trailers;
        this.mContext = context;
        notifyDataSetChanged();
    }

}
