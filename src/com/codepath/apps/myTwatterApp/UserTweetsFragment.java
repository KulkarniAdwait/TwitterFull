package com.codepath.apps.myTwatterApp;

import com.codepath.apps.myTwatterApp.models.User;

import android.os.Bundle;

public class UserTweetsFragment extends TweetsFragment {
	private User user;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		user = getArguments().getParcelable("user");
	}
	
	public static UserTweetsFragment newInstance(User user) {
		UserTweetsFragment tweetsFragment = new UserTweetsFragment();
		Bundle args = new Bundle();
		args.putParcelable("user", user);
		tweetsFragment.setArguments(args);
		return tweetsFragment;
	}
	
	@Override
	protected void populateTimeline() {
		client.getUserTimeline(jsonHttpResponseHandler()
				, new TwitterParamBuilder()
					.userId(user.getId())
					.buildParams());
	}
}
