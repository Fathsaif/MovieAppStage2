package com.saif.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saif on 30/04/2017.
 */

public class Movies {
    @SerializedName("results")
    public List<Movie> movies = new ArrayList<>();

    public List<Movie> getMovies() {
        return movies;
    }

}
