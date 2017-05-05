package com.saif.movieapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.saif.movieapp.R;
import com.saif.movieapp.model.Trailer;

import java.util.ArrayList;

/**
 * Created by Saif on 30/04/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder>{

    private ArrayList<Trailer> mTrailers;
    private final Context mContext;
    private final MovieTrailerOnClickHandler mTrailerOnClickHandler;

    public TrailerAdapter(ArrayList<Trailer> mTrailers, Context mContext, MovieTrailerOnClickHandler mTrailerOnClickHandler) {
        this.mTrailers = mTrailers;
        this.mContext = mContext;
        this.mTrailerOnClickHandler = mTrailerOnClickHandler;
    }
    public interface MovieTrailerOnClickHandler{
        void watch(String key);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_trailers,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Trailer trailer = mTrailers.get(position);

        String trailerUrl = "http://img.youtube.com/vi/" + trailer.getKey() + "/0.jpg";
        Glide.with(mContext).load(trailerUrl).into(holder.trailerImage);
            }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView trailerImage;
        public ViewHolder(View itemView) {
            super(itemView);
            trailerImage = (ImageView) itemView.findViewById(R.id.trailer_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           String key = mTrailers.get(getAdapterPosition()).getKey();
            mTrailerOnClickHandler.watch(key);
        }
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }
    public ArrayList<Trailer> getTrailers() {
        return mTrailers;
    }

    public  void setData(ArrayList<Trailer> trailers){
        mTrailers=trailers;
        //mTrailers.addAll(trailers);
        notifyDataSetChanged();

    }

}
