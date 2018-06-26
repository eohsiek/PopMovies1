package com.example.android.popmovies1.data;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popmovies1.R;
import com.example.android.popmovies1.data.Review;
import com.example.android.popmovies1.databinding.ReviewLayoutBinding;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsAdapterViewHolder> {

    private Review[] reviews;
    private Context mContext;

    public interface ReviewsAdapterOnClickHandler {
        void onClick(Review review);
    }

    public ReviewsAdapter() {
    }

    public class ReviewsAdapterViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        private ReviewLayoutBinding binding;

        public ReviewsAdapterViewHolder(ReviewLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Review review) {
            binding.setReview(review);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Review review = reviews[adapterPosition];
        }
    }

    @Override
    public ReviewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater  = LayoutInflater.from(context);
        ReviewLayoutBinding trailerLayoutBinding =
                ReviewLayoutBinding.inflate(layoutInflater, viewGroup, false);

        return new ReviewsAdapterViewHolder(trailerLayoutBinding);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapterViewHolder trailersAdapterViewHolder, int position) {
        Review review = reviews[position];
        Log.d("ReviewObject", String.valueOf(review));
        trailersAdapterViewHolder.bind(review);
    }

    @Override
    public int getItemCount() {
        if (null == reviews) return 0;
        return reviews.length;
    }


    public void setReviewsData(Context context, Review[] reviews) {
        this.reviews = reviews;
        this.mContext = context;
        notifyDataSetChanged();
    }

}