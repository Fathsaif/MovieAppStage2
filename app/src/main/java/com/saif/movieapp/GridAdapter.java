package com.saif.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.saif.movieapp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Created by Saif on 25/03/2017.
 */

public class GridAdapter extends BaseAdapter {
    private final Context mContext;
    private List<Movie> movies;


    public GridAdapter(Context mContext,List<Movie> moviess) {
        this.mContext = mContext;
        this.movies = moviess;
    }

    @Override
    public int getCount() {

        if (movies == null ) {
            return -1;
        }

        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.single_movie_grid, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();
         Movie movie= movies.get(position);
        String i = movie.getImage();
        String image_url = "http://image.tmdb.org/t/p/w185" + i;
        Glide.with(mContext).load(image_url).centerCrop().into(viewHolder.imageView);
        //Picasso.with(mContext).load(image_url).into(viewHolder.imageView);
        //viewHolder.titleView.setText(movie.getTitle());

        return convertView; }
    public static class ViewHolder {
        public final ImageView imageView;
        public final TextView titleView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.movie_image);
            titleView = (TextView) view.findViewById(R.id.movie_title);
        }
    }
    public void setmMoviesData (List<Movie> mMoviesData){
        movies =mMoviesData;
        notifyDataSetChanged();
    }
    public ArrayList<Movie> getMovies() {
        return (ArrayList<Movie>) movies;
    }
}
