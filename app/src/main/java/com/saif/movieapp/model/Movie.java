package com.saif.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Saif on 23/03/2017.
 */

public class Movie implements Parcelable {
    @SerializedName("id")
    private Long id;
    @SerializedName("original_title")
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    @SerializedName("poster_path")
    private String image;
    @SerializedName("backdrop_path")
    private String image2;
    @SerializedName("overview")
    private String overview;
    @SerializedName("vote_average")
    private String rating;
    @SerializedName("release_date")
    private String date; // release_date

    public Movie(Long id, String title, String image, String image2, String overview, String rating, String date) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.image2 = image2;
        this.overview = overview;
        this.rating = rating;
        this.date = date;
    }

    public Movie(){

}
    protected Movie(Parcel in) {
        id = in.readLong();
        title = in.readString();
        image = in.readString();
        image2 = in.readString();
        overview = in.readString();
        rating = in.readString();
        date = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            Movie movie =new Movie();
            movie.id = in.readLong();
            movie.title =in.readString();
            movie.image= in.readString();
            movie.image2 = in.readString();
            movie.overview = in.readString();
            movie.rating = in.readString();
            movie.date = in.readString();
            return movie;
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(image2);
        dest.writeString(overview);
        dest.writeString(rating);
        dest.writeString(date);
    }
}
