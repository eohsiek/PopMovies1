package com.example.android.popmovies1.data;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        }

        public void bind(Trailer trailer) {
            binding.setTrailer(trailer);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Trailer trailer = trailers[adapterPosition];
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
        Log.d("selectedtrailer", String.valueOf(trailer));
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
