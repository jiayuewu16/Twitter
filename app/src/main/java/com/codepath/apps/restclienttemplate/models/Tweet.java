package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Parcel
public class Tweet {

    public String body;
    public String createdAt;
    public User user;
    public String photoUrl;

    // Empty constructor needed by the Parcels library.
    public Tweet() {}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        try {
            JSONArray results = jsonObject.getJSONObject("entities").getJSONArray("media");
            tweet.photoUrl = results.getJSONObject(0).getString("media_url_https");
        }
        catch (NullPointerException | JSONException e) {
            tweet.photoUrl = null;
        }
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets= new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public String getRelativeTime() {
        final int SECOND_MILLIS = 1000;
        final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        final int DAY_MILLIS = 24 * HOUR_MILLIS;

        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String ret = "";

        try {
            long time = sf.parse(createdAt).getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                ret =  "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                ret =  "1m";
            } else if (diff < 50 * MINUTE_MILLIS) {
                ret = diff / MINUTE_MILLIS + "m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                ret = "1h";
            } else if (diff < 24 * HOUR_MILLIS) {
                ret = diff / HOUR_MILLIS + "h";
            } else if (diff < 48 * HOUR_MILLIS) {
                ret = "yesterday";
            } else {
                ret = diff / DAY_MILLIS + "d";
            }
        } catch (ParseException e) {
            Log.i("Tweet", "getRelativeTime failed");
            e.printStackTrace();
        }

        return ret;
    }

}
