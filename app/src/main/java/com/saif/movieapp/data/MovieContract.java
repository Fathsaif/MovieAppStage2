package com.saif.movieapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Saif on 27/04/2017.
 */

public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.saif.movieapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String MOVIE_PATH = "movies";


    public static class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
        .appendPath(MOVIE_PATH).build();
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String TABLE_NAME = "movies";
        public static final String MOVIE_TITLE_COLOUMN = "title";
        public static final String COLUMN_MOVIE_POSTER_PATH = "poster_path";
        public static final String COLUMN_MOVIE_OVERVIEW = "overview";
        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "release_date";
        public static final String COLUMN_MOVIE_BACKDROP_PATH = "backdrop_path";

    }

}
