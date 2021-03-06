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
	private TweetsFragment tweetsFragment;
	
	private final int COMPOSE_CODE = 111;
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
	
	public void onCompose(MenuItem mi) {
		Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
		startActivityForResult(i, COMPOSE_CODE);
	}
	
	public void onProfile(MenuItem mi) {
		Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
		i.putExtra("appUser", true);
		startActivity(i);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		getUserDetails();
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		tweetsFragment = TweetsFragment.newInstance();
		ft.replace(R.id.flTimelineTweets, tweetsFragment);
		ft.commit();		
	}
	
	private void getUserDetails() {
		if(User.appUser == null) {
			TwitterClient client = MyTwatterApp.getRestClient();
			client.getAccount(new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject accoutDetails) {
					User.appUser = User.fromJson(accoutDetails);
					populateActionBar();
				}
				
				@Override
				public void onFailure(Throwable e, String s) {
					Log.d("debug", e.toString());
					Log.d("debug", s.toString());
				}
			});	
		}
	}

	private void populateActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("@" + User.appUser.getScreenName());
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == COMPOSE_CODE) {
		  tweetsFragment.refreshFeed();
	  }
	} 
}
