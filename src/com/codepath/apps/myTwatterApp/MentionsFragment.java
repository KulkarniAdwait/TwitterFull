package com.codepath.apps.myTwatterApp;

import android.os.Bundle;

public class MentionsFragment extends TweetsFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public static MentionsFragment newInstance() {
		MentionsFragment tweetsFragment = new MentionsFragment();
		Bundle args = new Bundle();
		tweetsFragment.setArguments(args);
		return tweetsFragment;
	}
	
	@Override
	protected void populateTimeline() {
		client.getMentionsTimeline(jsonHttpResponseHandler()
				, new TwitterParamBuilder()
					.buildParams());
	}

}
