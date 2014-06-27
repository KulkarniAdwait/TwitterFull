package com.codepath.apps.myTwatterApp;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
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
	private final int COMPOSE_CODE = 111;
	
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
		
		//user is passed by the click handler on the profile image of other users
		//use the app User if we are looking at the signed in users profile
		//else view the other user profile
		if(getIntent().getBooleanExtra("appUser", false)) {
			u = User.appUser;
		}
		else {
			u = (User) getIntent().getParcelableExtra("user");
		}
		
		client.getUserDetails(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonObject) {
				u.updateUser(jsonObject);
				
				
				tvProfileUserName.setText(u.getName());
				tvProfileScreenName.setText("@" + u.getScreenName());
				tvProfileNumTweets.setText(String.valueOf(u.getNumTweets()));
				tvProfileNumFollowers.setText(String.valueOf(u.getNumFollowers()));
				tvProfileNumFollowing.setText(String.valueOf(u.getNumFollowing()));
				ImageLoader imgLoader = ImageLoader.getInstance();
				imgLoader.displayImage(u.getProfileImgUrl(), ivProfileProfileImage);
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("DEBUG", e.toString());
				Log.d("DEBUG", s);
			}
		}, new TwitterParamBuilder().userId(u.getId()).buildParams());
		
		client.getUserProfileBanner(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonObject) {
				u.updateProfileBanner(jsonObject);
				ImageLoader imgLoader = ImageLoader.getInstance();
				imgLoader.loadImage(u.getProfileBannerUrl(), new ImageLoadingListener() {
					
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
		}, new TwitterParamBuilder().userId(u.getId()).buildParams());
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		UserTweetsFragment tf = UserTweetsFragment.newInstance(u);
		ft.replace(R.id.flProfileTweets, tf);
		ft.commit();
		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
	
	public void onCompose(MenuItem mi) {
		Intent i = new Intent(ProfileActivity.this, ComposeActivity.class);
		startActivityForResult(i, COMPOSE_CODE);
	}
	
	public void onProfile(MenuItem mi) {
		Intent i = new Intent(ProfileActivity.this, ProfileActivity.class);
		i.putExtra("appUser", true);
		startActivity(i);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK && requestCode == COMPOSE_CODE) {
			  Intent i = new Intent(ProfileActivity.this, TimelineActivity.class);
			  startActivity(i);
		}
	}
}
