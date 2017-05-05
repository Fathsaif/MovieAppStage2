package com.saif.movieapp.utilities;

import android.net.Uri;

import com.saif.movieapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Saif on 25/03/2017.
 */

public class NetworkUtilis {
    public static final String TMDB_BASE_URL = "https://api.themoviedb.org/";
    private static final String API_KEY = "febf361b8850bffd17e944202d512e89";


    public static URL urlbuilder(String sort) {

        Uri builtUri = Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendPath(sort)
                .appendQueryParameter("api_key", API_KEY)
                .build();
        URL url =null;
        try {
            url= new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResonseFromHttp(URL url) throws IOException{
        HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();
        urlConnection.connect();
        try {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\n");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }

        finally {
            urlConnection.disconnect();
        }

        }

    }

