package com.codepath.apps.myTwatterApp;

import org.json.JSONObject;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.myTwatterApp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity {

	private User u;
	private TweetsFragment tweetsFragment;
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
	
	public void onCompose(MenuItem mi) {
		Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
//		i.putExtra("userName", u.getName());
//		i.putExtra("screenName", u.getScreenName());
//		i.putExtra("imgUrl", u.getProfileImgUrl());
		i.putExtra("user", u);
		startActivityForResult(i, 111);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		populateActionBar();
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		tweetsFragment = TweetsFragment.newInstance();
		ft.replace(R.id.flTimelineTweets, tweetsFragment);
		ft.commit();		
	}
	
	private void populateActionBar() {
		TwitterClient client = MyTwatterApp.getRestClient();
		client.getAccount(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject accoutDetails) {
				ActionBar actionBar = getActionBar();
				u = User.fromJson(accoutDetails);
				actionBar.setTitle("@" + u.getScreenName());
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});	
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == 111) {
		  tweetsFragment.refreshFeed();
	  }
	} 
}
