package com.saif.movieapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.saif.movieapp.data.MovieContract;
import com.saif.movieapp.data.MovieDbHelper;
import com.saif.movieapp.model.Movie;
import com.saif.movieapp.model.Movies;
import com.saif.movieapp.tasks.MoviesServices;
import com.saif.movieapp.utilities.JsonUtilis;
import com.saif.movieapp.utilities.NetworkUtilis;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.saif.movieapp.utilities.NetworkUtilis.getResonseFromHttp;
import static com.saif.movieapp.utilities.NetworkUtilis.urlbuilder;

public class MainActivity extends AppCompatActivity  {
    GridView mMoviesGrid;
    List<Movie> movie = new ArrayList<>();
    GridAdapter mGridAdapter;
    MovieDbHelper movieDbHelper;
    MovieListTask task;
    private static final String POPULARITY_DESC = "popular";
    private static final String RATING_DESC = "top_rated";
    public final static String FAVORITES = "favorites";
    private String mSortBy = POPULARITY_DESC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMoviesGrid = (GridView) findViewById(R.id.movies_grid);
        mMoviesGrid.setOnItemClickListener(onItemClickListener);
        task =new MovieListTask();
        mGridAdapter = new GridAdapter(this,movie);
        mMoviesGrid.setAdapter(mGridAdapter);
        new MovieListTask().execute(mSortBy);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_menu,menu);
        MenuItem action_sort_by_popularity = menu.findItem(R.id.popula);
        MenuItem action_sort_by_rating = menu.findItem(R.id.rated);

        if (mSortBy.contentEquals(POPULARITY_DESC)) {
            if (!action_sort_by_popularity.isChecked()) {
                action_sort_by_popularity.setChecked(true);
            }
        } else if (mSortBy.contentEquals(RATING_DESC)) {
            if (!action_sort_by_rating.isChecked()) {
                action_sort_by_rating.setChecked(true);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.popula:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                mSortBy = POPULARITY_DESC;
                updateMovies(mSortBy);
                return true;
            case R.id.rated:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                mSortBy = RATING_DESC;
                updateMovies(mSortBy);
                return true;
            case R.id.fav:
                if (item.isChecked()){
                    item.setChecked(false);
                }
                else {
                    item.setChecked(true);
                }
                mSortBy =FAVORITES;
                updateMovies(mSortBy);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    GridView.OnItemClickListener onItemClickListener = new GridView.OnItemClickListener(){

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<Movie> movies =mGridAdapter.getMovies();
        Movie movie = movies.get(position);
        Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);
        intent.putExtra("details",movie);
               startActivity(intent);

    }
};
    public class MovieListTask extends AsyncTask<String,Void,List<Movie>> {
        String JsonResult = "";
        String api_key = "febf361b8850bffd17e944202d512e89";
        @Override
        protected List<Movie> doInBackground(String... params) {

            String mSort = params[0];
            if (mSort.equals(FAVORITES)){
                Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,null,null,null,null);
                List<Movie> movies = new ArrayList<>();
                Movie movieObject = new Movie();
               // int totalMovies = cursor.getCount();
                if (cursor.moveToFirst()){
                        movieObject.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                        movieObject.setDate(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE)));
                        movieObject.setImage(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH)));
                        movieObject.setImage2(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_BACKDROP_PATH)));
                        movieObject.setOverview(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW)));
                    movies.add(movieObject);
                        cursor.moveToFirst();
                }
                return movies;
            }
            else {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(NetworkUtilis.TMDB_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create()).build();
                MoviesServices moviesServices = retrofit.create(MoviesServices.class);
                Call<Movies> call = moviesServices.discoverMovies(mSort, api_key);
                try {
                    Response<Movies> response = call.execute();
                    Movies movies = response.body();
                    return movies.getMovies();
                } catch (IOException e) {
                }
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);

            mGridAdapter.setmMoviesData(movies);
            movie.addAll(movies);
            //mtext.setText(JsonResult);
        }
    }


    private void updateMovies(String sort_by) {
            new MovieListTask().execute(sort_by);

    }

}
