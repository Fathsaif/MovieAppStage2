package com.saif.movieapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Saif on 27/04/2017.
 */

public class MovieProvider extends ContentProvider {

    public static final int CODE_MOVIE = 100;
    public static final int CODE_MOVIE_WITH_TITLE = 101;

    private static UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper mDbHelper;
    public static UriMatcher buildUriMatcher (){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(MovieContract.CONTENT_AUTHORITY,MovieContract.MOVIE_PATH,CODE_MOVIE);
        matcher.addURI(MovieContract.CONTENT_AUTHORITY,MovieContract.MOVIE_PATH+"/*",CODE_MOVIE_WITH_TITLE);
        return matcher;
    }
    @Override
    public boolean onCreate() {
        mDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case CODE_MOVIE:
             cursor  =  db.query(MovieContract.MovieEntry.TABLE_NAME,projection,
                     selection,
                     selectionArgs,
                     null,null,sortOrder);
                break;
            default:
                throw  new UnsupportedOperationException("invalid uri"+uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor ;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Uri mUri = null;
        switch (sUriMatcher.match(uri)){
            case CODE_MOVIE:
               long id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if (id>0) {
                    mUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI,id);
                }
                else {
                    throw new android.database.SQLException("failed to insert new row "+mUri);
                }
                break;
            default:
                throw new UnsupportedOperationException("invalid insert uri "+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return mUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int movieDeleted;
        switch (sUriMatcher.match(uri)){
            case CODE_MOVIE:
               movieDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("invalid uri"+uri);
        }
        if (movieDeleted!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return movieDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        return 0;
    }
}
