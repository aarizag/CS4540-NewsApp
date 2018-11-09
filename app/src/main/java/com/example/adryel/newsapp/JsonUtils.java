package com.example.adryel.newsapp;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {

    public static ArrayList<NewsItem> parseNews(String JSONString){
        try {
            final String NEWS_AUTHOR = "author";
            final String NEWS_TITLE = "title";
            final String NEWS_DESCRIPTION = "description";
            final String NEWS_URL = "url";
            final String NEWS_URL_TO_IMAGE = "urlToImage";
            final String NEWS_PUBLISHED_AT = "publishedAt";

            final String JSON_ARTICLES = "articles";

            ArrayList<NewsItem> parsedNewsData  = new ArrayList<>();
            JSONObject newsJson = new JSONObject(JSONString);

            JSONArray newsArray = newsJson.getJSONArray(JSON_ARTICLES);

            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject singleArticle = newsArray.getJSONObject(i);
                String author = singleArticle.getString(NEWS_AUTHOR);
                String title = singleArticle.getString(NEWS_TITLE);
                String descript = singleArticle.getString(NEWS_DESCRIPTION);
                String url = singleArticle.getString(NEWS_URL);
                String toImage = singleArticle.getString(NEWS_URL_TO_IMAGE);
                String publisher = singleArticle.getString(NEWS_PUBLISHED_AT);

                Log.d("NEW ARTICLE", author + ", " + title);
                parsedNewsData.add(new NewsItem(author, title, descript, url, toImage, publisher));
            }

            return parsedNewsData;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
