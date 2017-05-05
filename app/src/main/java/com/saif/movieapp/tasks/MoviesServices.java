package com.saif.movieapp.tasks;

import com.saif.movieapp.model.Movies;
import com.saif.movieapp.model.Review;
import com.saif.movieapp.model.Reviews;
import com.saif.movieapp.model.Trailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Mosaad on 30/04/2017.
 */

public interface MoviesServices {
    @GET("3/movie/{sort_by}")
    Call<Movies> discoverMovies(@Path("sort_by") String sortBy, @Query("api_key") String apiKey);

    @GET("3/movie/{id}/videos")
    Call<Trailers> findTrailersById(@Path("id") long movieId, @Query("api_key") String apiKey);

    @GET("3/movie/{id}/reviews")
    Call<Reviews> findReviewsById(@Path("id") long movieId, @Query("api_key") String apiKey);
}
