package com.codepath.apps.myTwatterApp;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.activeandroid.util.Log;
import com.codepath.apps.myTwatterApp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TweetsFragment extends Fragment {
		
	protected ListView lvTweets;
	protected ArrayList<Tweet> tweets;
	protected TweetArrayAdapter aTweets;
	protected TwitterClient client;
	
	protected long max_id;
	protected long since_id;
	protected long top_max_id;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		max_id = 0l;
		since_id = 0l;
		
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
		
		client = MyTwatterApp.getRestClient();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweets, container, false);
		
		lvTweets = (ListView) v.findViewById(R.id.lvFragTweets);
		lvTweets.setAdapter(aTweets);
		
		populateTimeline();
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
		    	max_id = since_id - 1;
		    	populateTimeline();
	    	}
        });
		
		return v;
	}
		
	public static TweetsFragment newInstance() {
		TweetsFragment tweetsFragment = new TweetsFragment();
		return tweetsFragment;
	}
	
	public void refreshFeed() {
		aTweets.clear();
		max_id = 0;
		populateTimeline();
	}
	
	protected void populateTimeline() {
		client.getHomeTimeline(jsonHttpResponseHandler(), new TwitterParamBuilder().maxId(max_id).buildParams());
	}
	
	protected JsonHttpResponseHandler jsonHttpResponseHandler() {
		JsonHttpResponseHandler j = new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJsonArray(json));
				aTweets.notifyDataSetChanged();
				//update page variables
				max_id = tweets.get(0).getId();
				since_id = tweets.get(tweets.size() - 1).getId();
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		};
		return j;
	}
}
