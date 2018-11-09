package com.example.adryel.newsapp;


/*
Create a NewsItem model class to store information about each news story.
You need to include fields (make them of type String) for all of the information in each item
(see JSON for what those items are (they will include the article's title and description,
and url, and other things), you need to figure out what the information is from the JSON).
 */

public class NewsItem {
    String author;
    String title;
    String description;
    String url;
    String urlToImage;
    String publishedAt;

    public NewsItem(String author, String title, String description, String url, String urlToImage, String publishedAt) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {return url;}

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getCurrentNews() {
        return "Title: "+ this.title +
                "\nDescription: " + this.description +
                "\nDate: " + this.publishedAt;
    }
}
