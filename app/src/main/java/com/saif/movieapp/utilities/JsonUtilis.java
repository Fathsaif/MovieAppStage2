package com.saif.movieapp.utilities;

import com.saif.movieapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Saif on 25/03/2017.
 */

public class JsonUtilis {
    public static Movie[] getMoviesDataFromJson(String moviesJsonStr) throws JSONException {
        // JSON tags
        final String TAG_RESULTS = "results";
        final String TAG_ORIGINAL_TITLE = "original_title";
        final String TAG_POSTER_PATH = "poster_path";
        final String TAG_BACKDROP_PATH = "backdrop_path";
        final String TAG_OVERVIEW = "overview";
        final String TAG_VOTE_AVERAGE = "vote_average";
        final String TAG_RELEASE_DATE = "release_date";

        // Get the array containing hte movies found
        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray resultsArray = moviesJson.getJSONArray(TAG_RESULTS);

        // Create array of Movie objects that stores data from the JSON string
        Movie[] movies = new Movie[resultsArray.length()];

        // Traverse through movies one by one and get data
        for (int i = 0; i < resultsArray.length(); i++) {
            // Initialize each object before it can be used
            movies[i] = new Movie();

            // Object contains all tags we're looking for
            JSONObject movieInfo = resultsArray.getJSONObject(i);

            // Store data in movie object
            movies[i].setTitle(movieInfo.getString(TAG_ORIGINAL_TITLE));
            movies[i].setImage(movieInfo.getString(TAG_POSTER_PATH));
            movies[i].setImage2(movieInfo.getString(TAG_BACKDROP_PATH));
            movies[i].setOverview(movieInfo.getString(TAG_OVERVIEW));
            movies[i].setRating(movieInfo.getString(TAG_VOTE_AVERAGE));
            movies[i].setDate(movieInfo.getString(TAG_RELEASE_DATE));
        }

        return movies;
    }
}
