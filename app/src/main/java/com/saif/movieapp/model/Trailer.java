package com.saif.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Saif on 30/04/2017.
 */

public class Trailer implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("key")
    private String key;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @SerializedName("site")
    private String site;

    protected Trailer(Parcel in) {
        id = in.readString();
        name = in.readString();
        key = in.readString();
        site = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            Trailer trailers =new Trailer();
            trailers.id = in.readString();
            trailers.name = in.readString();
            trailers.key = in.readString();
            trailers.site = in.readString();
            return trailers;
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public Trailer() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

        public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String url) {
        this.key = url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(key);
        dest.writeString(site);
    }
}
