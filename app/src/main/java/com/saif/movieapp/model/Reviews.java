package com.saif.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mosaad on 03/05/2017.
 */

public class Reviews {
    @SerializedName("results")
    private List<Review> reviews = new ArrayList<>();

    public List<Review> getReviews() {
        return reviews;
    }
}

