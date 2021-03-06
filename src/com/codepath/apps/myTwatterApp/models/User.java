package com.codepath.apps.myTwatterApp.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

	private String name;
	private String screenName;
	private String profileImgUrl;
	private String profileBannerUrl;
	private long uid;
	private long numFollowers;
	private long numFollowing;
	private long numTweets;
	private String description;
	
	public static User appUser; 
	
	public User(Parcel in) {
		String[] data = new String[5];
		in.readStringArray(data);
		
		this.name = data[0];
		this.screenName = data[1];
		this.profileImgUrl = data[2];
		this.profileBannerUrl = data[3];
		this.description = data[4];
		
		long[] longs = new long[4];
		in.readLongArray(longs);
		
		this.uid = longs[0];
		this.numFollowers = longs[1];
		this.numFollowing = longs[2];
		this.numTweets = longs[3];
	}

	public String profileBannerUrl() {
		return profileBannerUrl;
	}

	public User() {
		this.numFollowers = 0;
		this.numFollowing = 0;
		this.numTweets = 0;
	}

	public long getUid() {
		return uid;
	}

	public long getNumFollowers() {
		return numFollowers;
	}

	public long getNumFollowing() {
		return numFollowing;
	}

	public long getNumTweets() {
		return numTweets;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}
	
	public String getProfileImgUrl() {
		return profileImgUrl;
	}

	public String getProfileBannerUrl() {
		return profileBannerUrl;
	}
	
	public String getDescription() {
		return description;
	}
	

	public static User fromJson(JSONObject jsonObject) {
		User user = new User();
		try {
			user.name = jsonObject.getString("name");
			user.uid = jsonObject.getLong("id");
			user.profileImgUrl = jsonObject.getString("profile_image_url");
			user.screenName = jsonObject.getString("screen_name");
		} catch (JSONException je) {
			je.printStackTrace();
			return null;
		}
		return user;
	}
	
	//These bits could've been separated out but I am putting them here just to use parcelable
	public void updateUser(JSONObject jsonObject) {
		try {
			this.numFollowers = jsonObject.getLong("followers_count");
			this.numFollowing = jsonObject.getLong("friends_count");
			this.numTweets = jsonObject.getLong("statuses_count");
			this.description = jsonObject.getString("description");
		} catch (JSONException je) {
			je.printStackTrace();
		}
	}
	
	public void updateProfileBanner(JSONObject jsonObject) {
		try {
			JSONObject banner = jsonObject.getJSONObject("sizes").getJSONObject("mobile_retina");
			this.profileBannerUrl = banner.getString("url");
		} catch (JSONException je) {
			je.printStackTrace();
		}
	}

	public static final Parcelable.Creator<User> CREATOR
    = new Parcelable.Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
		    return new User(in);
		}
		
		@Override
		public User[] newArray(int size) {
		    return new User[size];
		}
	};
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[] 
				{
					this.name,
					this.screenName,
					this.profileImgUrl,
					this.profileBannerUrl,
					this.description
				});
		dest.writeLongArray(new long[]
				{
					this.uid,
					this.numFollowers,
					this.numFollowing,
					this.numTweets
				});
	}
}
