package com.saif.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mosaad on 02/05/2017.
 */

public class Trailers {
    @SerializedName("results")
    private List<Trailer> trailer = new ArrayList<>();

    public List<Trailer> getTrailers() {
        return trailer;
    }
}
