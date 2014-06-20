package com.codepath.apps.myTwatterApp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

	private String name;
	private long uid;
	private String screenName;
	private String profileImgUrl;
	
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



}
