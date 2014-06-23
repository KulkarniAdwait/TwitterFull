package com.codepath.apps.myTwatterApp.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {
	private String body;
	private Long id;
	private String createdAt;
	private User user;
	public static Long since_id = 0l;
	public static Long max_id = 0l;
	public static Long top_max_id = 0l;
	
	public static Tweet fromJson(JSONObject jsonObj) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = jsonObj.getString("text");
			tweet.id = jsonObj.getLong("id");
			//for the first time set of min id
			if(Tweet.since_id == 0) {
				Tweet.since_id = tweet.id;
			}
			
			if(tweet.id < Tweet.since_id) {
				Tweet.since_id = tweet.id;
			}
			
			if (tweet.id > Tweet.max_id) {
				Tweet.max_id = tweet.id;
				Tweet.top_max_id = tweet.id;
			}
			tweet.createdAt = jsonObj.getString("created_at");
			tweet.user = User.fromJson(jsonObj.getJSONObject("user"));
		} catch (JSONException je) {
			je.printStackTrace();
			return null;
		}
		return tweet;
	}

	public String getBody() {
		return body;
	}

	public long getId() {
		return id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}

	public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		for(int i=0; i< jsonArray.length(); i+=1) {
			JSONObject jsonObj = null;
			try {
				jsonObj = jsonArray.getJSONObject(i);
			}
			catch (JSONException je) {
				je.printStackTrace();
				continue;
			}
			
			Tweet tweet = Tweet.fromJson(jsonObj);
			if(tweet != null) {
				tweets.add(tweet);
			}
		}
		return tweets;
	}
	
	@Override
	public String toString() {
		return this.body + " " + this.user;
	}
}
