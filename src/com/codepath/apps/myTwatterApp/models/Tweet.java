package com.codepath.apps.myTwatterApp.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Tweet implements Parcelable {
	private String body;
	private Long id;
	private String createdAt;
	private User user;
	private long numFavorites;
	private long numReTweets;
	private boolean favorited;
	private boolean retweeted;
	private String mediaUrl;
	
	
	public static Tweet fromJson(JSONObject jsonObj) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = jsonObj.getString("text");
			tweet.id = jsonObj.getLong("id");
			tweet.createdAt = jsonObj.getString("created_at");
			try {
				tweet.numFavorites = jsonObj.getLong("favorite_count");
			} catch (JSONException j) {
				tweet.numFavorites = 0l;
			}
			try {
				tweet.numReTweets = jsonObj.getLong("retweet_count");
			} catch (JSONException j) {
				tweet.numReTweets = 0l;
			}
			tweet.user = User.fromJson(jsonObj.getJSONObject("user"));
			
			try {
				tweet.favorited = jsonObj.getBoolean("favorited");
			}
			catch (JSONException j) {
				tweet.favorited = false;
			}
			
			try {
				tweet.retweeted = jsonObj.getBoolean("retweeted");
			}
			catch (JSONException j) {
				tweet.retweeted = false;
			}
			
			tweet.mediaUrl = "";
			try {
				JSONArray ja = jsonObj.getJSONObject("entities").getJSONArray("media");
				for (int i = 0; i < ja.length(); i+=1) {
					if("photo".equals(ja.getJSONObject(i).getString("type"))) {
						tweet.mediaUrl = ja.getJSONObject(i).getString("media_url");
						break;
					}
				}
			}
			catch (JSONException j) {
				tweet.mediaUrl = "";
			}
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
	
	public long getNumFavorites() {
		return numFavorites;
	}
	
	public long getNumReTweets() {
		return numReTweets;
	}
	
	public Tweet() {
		
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

    protected Tweet(Parcel in) {
    	String[] data = new String[3];
		in.readStringArray(data);
		this.body = data[0];
		this.createdAt = data[1];
		this.mediaUrl = data[2];
		
		long[] longs = new long[3];
		in.readLongArray(longs);
		this.id = longs[0];
		this.numFavorites = longs[1];
		this.numReTweets = longs[2];
		
		user = (User) in.readParcelable(User.class.getClassLoader());
		
		boolean[] bools = new boolean[2];
		in.readBooleanArray(bools);
		this.favorited = bools[0];
		this.retweeted = bools[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    	dest.writeStringArray(new String[] {
				this.body,
				this.createdAt,
				this.mediaUrl
		});
        
        dest.writeLongArray(new long[]{
        		this.id,
        		this.numFavorites,
        		this.numReTweets
        });
        
        dest.writeParcelable(user, 10);
        
        dest.writeBooleanArray(new boolean[] {
        		this.favorited,
        		this.retweeted
        });
    }


	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}


	public boolean isRetweeted() {
		return retweeted;
	}

	public void setRetweeted(boolean retweeted) {
		this.retweeted = retweeted;
	}


	public String getMediaUrl() {
		return mediaUrl;
	}

	public static final Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel in) {
            return new Tweet(in);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };
}