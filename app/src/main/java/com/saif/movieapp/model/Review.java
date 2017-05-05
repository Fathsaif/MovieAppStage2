package com.saif.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Saif on 30/04/2017.
 */

public class Review implements Parcelable{
    protected Review(Parcel in) {
        id = in.readString();
        url = in.readString();
        author = in.readString();
        content = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            Review review = new Review();
            review.id = in.readString();
            review.author = in.readString();
            review.content = in.readString();
            review.url = in.readString();
            return review;
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public Review() {

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @SerializedName("id")
    private String id;
    @SerializedName("url")
    private String url;
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(url);
        dest.writeString(author);
        dest.writeString(content);
    }

}
