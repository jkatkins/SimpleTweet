package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.bumptech.glide.load.model.stream.QMediaStoreUriLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity(foreignKeys = @ForeignKey(entity=User.class, parentColumns="id", childColumns="userID"))
@Parcel
public class Tweet {

    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAt;

    @PrimaryKey
    @ColumnInfo
    public long id;

    @ColumnInfo
    public long userID;

    @Ignore
    public User user;

    @ColumnInfo
    public String mediaUrl;

    public Tweet () {}

    public String getMediaUrl() {
        return mediaUrl;
    }

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.id = jsonObject.getLong("id");
        tweet.userID = tweet.user.getId();
        JSONObject entities = jsonObject.getJSONObject("entities");
        if (entities.has("media")) {
            JSONArray media = entities.getJSONArray("media");
            tweet.mediaUrl = media.getJSONObject(0).getString("media_url_https");
        } else {
            tweet.mediaUrl = "";
        }
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<Tweet>();
        for (int i = 0; i < jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
            Log.d("Debug","making tweet form array");
        }
        return tweets;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public long getId() {
        return id;
    }
}
