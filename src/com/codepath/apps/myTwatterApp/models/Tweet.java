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
	//public static Long since_id = 0l;
	//public static Long max_id = 0l;
	//public static Long top_max_id = 0l;
	
	public static Tweet fromJson(JSONObject jsonObj) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = jsonObj.getString("text");
			tweet.id = jsonObj.getLong("id");
			//for the first time set of min id
//			if(Tweet.since_id == 0) {
//				Tweet.since_id = tweet.id;
//			}
//			
//			if(tweet.id < Tweet.since_id) {
//				Tweet.since_id = tweet.id;
//			}
//			
//			if (tweet.id > Tweet.max_id) {
//				Tweet.max_id = tweet.id;
//				Tweet.setTop_max_id(tweet.id);
//			}
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
        body = in.readString();
        id = in.readByte() == 0x00 ? null : in.readLong();
        createdAt = in.readString();
        user = (User) in.readValue(User.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(id);
        }
        dest.writeString(createdAt);
        dest.writeValue(user);
    }

//    public static Long getTop_max_id() {
//		//return top_max_id;
//    	return 0l;
//	}

//	public static void setTop_max_id(Long top_max_id) {
////		Tweet.top_max_id = top_max_id;
//	}

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