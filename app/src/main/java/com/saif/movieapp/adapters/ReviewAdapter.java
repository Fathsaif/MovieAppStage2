package com.saif.movieapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saif.movieapp.R;
import com.saif.movieapp.model.Review;

import java.util.List;

/**
 * Created by Saif on 30/04/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHoldr>{
    private List<Review> mReviews;
      private Context mContext;

    public ReviewAdapter(List<Review> mReviews, Context mContext) {
        this.mReviews = mReviews;
        this.mContext = mContext;
    }
    @Override
    public ViewHoldr onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_review,parent,false);
        return new ViewHoldr(view);
    }

    @Override
    public void onBindViewHolder(ViewHoldr holder, int position) {
        Review review = mReviews.get(position);
        String authorName = review.getAuthor();
        String reviewContent = review.getContent();
        holder.mAuthor.setText(authorName);
        holder.mContent.setText(reviewContent);
            }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ViewHoldr extends RecyclerView.ViewHolder{
        TextView mAuthor;
        TextView mContent;
        public ViewHoldr(View itemView) {
            super(itemView);
            mAuthor = (TextView) itemView.findViewById(R.id.Author_tv);
            mContent = (TextView) itemView.findViewById(R.id.review_tv);
        }
    }
    public void setData (List<Review> reviews){
        mReviews = reviews;
        notifyDataSetChanged();
    }
public List<Review> getData (){
    return mReviews;
}
}
