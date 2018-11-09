package com.example.adryel.newsapp;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/*
 * API KEY = 3ee2d08fb1d245ef91a71d5ec7ea374b
 */

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static String BASE_URL = "https://newsapi.org/v1/articles";
    private static String API_PARAM = "apiKey";
    private static String API_KEY = "3ee2d08fb1d245ef91a71d5ec7ea374b";
    private static String SOURCE_PARAM = "source";
    private static String SOURCE_LOCATION = "the-next-web";
    private static String SORT_BY_PARAM = "sortBy";
    private static String SORT_STRATEGY = "latest";

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(API_PARAM, API_KEY)
                .appendQueryParameter(SOURCE_PARAM, SOURCE_LOCATION)
                .appendQueryParameter(SORT_BY_PARAM, SORT_STRATEGY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput)
                return scanner.next();
            else
                return null;
        } finally {
            urlConnection.disconnect();
        }
    }
}
