package com.codepath.apps.myTwatterApp;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.util.Log;
import com.codepath.apps.myTwatterApp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class ProfileActivity extends FragmentActivity {

	TextView tvProfileUserName;
	TextView tvProfileScreenName;
	ImageView ivProfileProfileImage;
	TextView tvProfileNumTweets;
	TextView tvProfileNumFollowers;
	TextView tvProfileNumFollowing;
	RelativeLayout rlProfileHeader;
	User u;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		rlProfileHeader = (RelativeLayout) findViewById(R.id.rlProfileHeader);
		tvProfileUserName = (TextView) findViewById(R.id.tvProfileUserName);
		tvProfileScreenName = (TextView) findViewById(R.id.tvProfileScreenName);
		ivProfileProfileImage = (ImageView) findViewById(R.id.ivProfileProfileImage);
		tvProfileNumTweets = (TextView) findViewById(R.id.tvProfileNumTweets);
		tvProfileNumFollowers = (TextView) findViewById(R.id.tvProfileNumFollowers);
		tvProfileNumFollowing = (TextView) findViewById(R.id.tvProfileNumFollowing);
		
		TwitterClient client = MyTwatterApp.getRestClient();
		u = (User) getIntent().getParcelableExtra("user");
		
		client.getUserDetails(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonObject) {
				u.updateUser(jsonObject);
				
				ImageLoader imgLoader = ImageLoader.getInstance();
				imgLoader.displayImage(u.getProfileImgUrl(), ivProfileProfileImage);
				tvProfileUserName.setText(u.getName());
				tvProfileScreenName.setText("@" + u.getScreenName());
				tvProfileNumTweets.setText(String.valueOf(u.getNumTweets()));
				tvProfileNumFollowers.setText(String.valueOf(u.getNumFollowers()));
				tvProfileNumFollowing.setText(String.valueOf(u.getNumFollowing()));
				
				imgLoader.loadImage(u.getProfileBackgroundImgUrl(), new ImageLoadingListener() {
					
					@Override
					public void onLoadingStarted(String arg0, View arg1) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
						// TODO Auto-generated method stub
						
					}
					
					@SuppressLint("NewApi")
					@Override
					public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
						Drawable bm = new BitmapDrawable(getResources(), arg2);
						rlProfileHeader.setBackground(bm);
						
					}
					
					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
						// TODO Auto-generated method stub
						
					}
				});
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("DEBUG", e.toString());
				Log.d("DEBUG", s);
			}
		}, new TwitterParamBuilder().userId(u.getId()).buildParams());
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		UserTweetsFragment tf = UserTweetsFragment.newInstance(u);
		ft.replace(R.id.flProfileTweets, tf);
		ft.commit();
		
	}
}
