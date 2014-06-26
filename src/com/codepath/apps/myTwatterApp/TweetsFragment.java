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
import com.codepath.apps.myTwatterApp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TweetsFragment extends Fragment {
	
	public enum TweetFeedType {
		USER, HOME;
	}
	
	private ListView lvTweets;
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter aTweets;
	private User user;
	private TweetFeedType type;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweets, container, false);
		
		user = getArguments().getParcelable("user");
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
		
		populateTimeline();
		
		lvTweets = (ListView) v.findViewById(R.id.lvFragTweets);
		lvTweets.setAdapter(aTweets);
		return v;
	}
	
	public static TweetsFragment newUserInstance(User user) {
		TweetsFragment tweetsFragment = new TweetsFragment();
		tweetsFragment.type = TweetFeedType.USER;
		Bundle args = new Bundle();
		args.putParcelable("user", user);
		tweetsFragment.setArguments(args);
		return tweetsFragment;
	}
	
	public static TweetsFragment newUserInstance() {
		TweetsFragment tweetsFragment = new TweetsFragment();
		tweetsFragment.type = TweetFeedType.HOME;
		return tweetsFragment;
	}
	
	private void populateTimeline() {
		TwitterClient client = MyTwatterApp.getRestClient();
		if(this.type == TweetFeedType.USER) {
			client.getUserTimeline(new JsonHttpResponseHandler(){
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
			}, user.getId());
		}
		else //default to home time line
		{
			client.getUserTimeline(new JsonHttpResponseHandler(){
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
			}, Tweet.max_id);
		}
		//Toast.makeText(this, "min:" + String.valueOf(Tweet.since_id) + " max:" + String.valueOf(Tweet.max_id), Toast.LENGTH_LONG).show();
	}
}
