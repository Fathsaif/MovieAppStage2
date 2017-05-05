package com.saif.movieapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.saif.movieapp.adapters.ReviewAdapter;
import com.saif.movieapp.adapters.TrailerAdapter;
import com.saif.movieapp.data.MovieContract;
import com.saif.movieapp.data.MovieDbHelper;
import com.saif.movieapp.model.Movie;
import com.saif.movieapp.model.Review;
import com.saif.movieapp.model.Reviews;
import com.saif.movieapp.model.Trailer;
import com.saif.movieapp.model.Trailers;
import com.saif.movieapp.tasks.MoviesServices;
import com.saif.movieapp.utilities.NetworkUtilis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

/**
 * Created by Saif on 26/03/2017.
 */

public class DetailsActivity extends AppCompatActivity implements TrailerAdapter.MovieTrailerOnClickHandler{
    String api_key = "febf361b8850bffd17e944202d512e89";
    RecyclerView rRecycleView;
    Movie movies;
    RecyclerView tRecyclerView;
    ReviewAdapter mReviewAdapter;
    TrailerAdapter mTrailAdapter;
    ArrayList<Trailer> trailers = new ArrayList<>();
    List<Review> reviews = new ArrayList<>();
     @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        tRecyclerView = (RecyclerView) findViewById(R.id.trailers_list);
        rRecycleView = (RecyclerView) findViewById(R.id.review_list);
        LinearLayoutManager vLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rRecycleView.setLayoutManager(vLayoutManager);
        LinearLayoutManager hLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        tRecyclerView.setLayoutManager(hLayoutManager);
        TextView tvOriginalTitle = (TextView) findViewById(R.id.movie_name_tv);
        ImageView ivPoster = (ImageView) findViewById(R.id.movie_poster_iv);
        TextView tvOverView = (TextView) findViewById(R.id.movie_overview);
        TextView tvReleaseDate = (TextView) findViewById(R.id.movie_date_released);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        TextView rateValue = (TextView) findViewById(R.id.rate_tv);

        Intent intent =getIntent();
         movies = intent.getParcelableExtra("details");
        mReviewAdapter = new ReviewAdapter(reviews,this);
         rRecycleView.setAdapter(mReviewAdapter);
         rRecycleView.setHasFixedSize(true);
        mTrailAdapter = new TrailerAdapter(trailers,this,this);
        tRecyclerView.setAdapter(mTrailAdapter);
        tRecyclerView.setHasFixedSize(true);
        Long id = Long.valueOf(movies.getId());
         new MovieReviewTask().execute(id);
        MovieTrailersTask task = new MovieTrailersTask();
        task.execute(id);

       // tvOriginalTitle.setText(intent.getStringExtra("title"));
        if (movies!= null) {
            String title = movies.getTitle();
            String image = movies.getImage2();
            String overview = movies.getOverview();
            String date = movies.getDate();
             final String rate = movies.getRating();
            tvOriginalTitle.setText(title);
            String image_url = "http://image.tmdb.org/t/p/w185" + image;
            Glide.with(this).load(image_url).centerCrop().into(ivPoster);
            tvOverView.setText(overview);
            tvReleaseDate.setText(date);
            rateValue.setText(rate);
            ratingBar.setRating(Float.parseFloat(rate));
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    double ratingValue = Double.parseDouble(rate);
                    Toast.makeText(getApplicationContext(),rate,Toast.LENGTH_LONG).show();
                    ratingBar.setRating((float) ratingValue);
                }
            });

            //ArrayList<Trailer> trailers = mTrailAdapter.getTrailers();
        }
    }

    public void saveFavorite (View view){
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry._ID,
                movies.getId());
        values.put(MovieContract.MovieEntry.MOVIE_TITLE_COLOUMN,
                movies.getTitle());
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH,
                movies.getImage());
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW,
                movies.getOverview());
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE,
                movies.getRating());
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE,
                movies.getDate());
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_BACKDROP_PATH,
                movies.getImage2());
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        contentResolver.insert(MovieContract.MovieEntry.CONTENT_URI,values);
    }

    public void watchTrailer (View view){
        trailers = mTrailAdapter.getTrailers();
        String key = trailers.get(0).getKey();
        String trailerUrl = "http://www.youtube.com/watch?v="+key;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
        startActivity(intent);
    }

    @Override
    public void watch(String key) {
        String trailerUrl = "http://www.youtube.com/watch?v="+key;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
        startActivity(intent);
    }

    public class MovieReviewTask extends AsyncTask<Long,Void,List<Review>>{

        @Override
        protected List<Review> doInBackground(Long... params) {
            if (params == null)
                return null;
            Long id = params[0];
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(NetworkUtilis.TMDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            MoviesServices services = retrofit.create(MoviesServices.class);
            Call<Reviews> reviewsCall = services.findReviewsById(id,api_key);
            try{
                Response<Reviews> response =reviewsCall.execute();
                Reviews reviews = response.body();
                return reviews.getReviews();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Review> reviews) {
            super.onPostExecute(reviews);
            if (reviews != null){
            Toast.makeText(getApplicationContext(),"size = " +reviews.size(),Toast.LENGTH_LONG);
            mReviewAdapter.setData(reviews);}
        }
    }

    public class MovieTrailersTask extends AsyncTask<Long,Void,List<Trailer>> {

               @Override
        protected List<Trailer> doInBackground(Long... params) {
            if (params==null){
                return null;
            }
            Long movieId = params[0];
            Retrofit retrofit =new Retrofit.Builder()
                    .baseUrl(NetworkUtilis.TMDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            MoviesServices services = retrofit.create(MoviesServices.class);
            Call<Trailers> call = services.findTrailersById(movieId, api_key);
            try {
                Response<Trailers> response = call.execute();
                Trailers trailers = response.body();
                return trailers.getTrailers();
            }
            catch (IOException e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Trailer> trailers) {
            super.onPostExecute(trailers);
            if (trailers.size()>0) {
                mTrailAdapter.setData((ArrayList<Trailer>) trailers);
                Toast.makeText(getApplicationContext(),"size = " +trailers.size(),Toast.LENGTH_LONG);

                //mLister.onFetchFinished(trailers);
            }
            else {
                Toast.makeText(getApplicationContext(),"no trailers found",Toast.LENGTH_LONG);
            }
        }
    }
}
