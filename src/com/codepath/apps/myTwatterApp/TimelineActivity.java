package com.codepath.apps.myTwatterApp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.myTwatterApp.models.Tweet;
import com.codepath.apps.myTwatterApp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter aTweets;
	private ListView lvTweets;
	private User u;
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
	
	public void onCompose(MenuItem mi) {
		Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
		i.putExtra("userName", u.getName());
		i.putExtra("screenName", u.getScreenName());
		i.putExtra("imgUrl", u.getProfileImgUrl());
		startActivityForResult(i, 111);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		
		client = MyTwatterApp.getRestClient();
		populateTimeline();
		populateActionBar();
		
	}
	
	private void populateActionBar() {
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

	private void populateTimeline() {
		client.getHomeTimeline(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJsonArray(json));
				aTweets.notifyDataSetChanged();
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
		  aTweets.clear();
	     populateTimeline();
	  }
	} 
}
