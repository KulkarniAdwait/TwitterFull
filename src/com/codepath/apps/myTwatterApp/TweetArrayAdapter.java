package com.codepath.apps.myTwatterApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.myTwatterApp.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	
	public TweetArrayAdapter(Context context, List<Tweet> objects) {
		super(context, 0, objects);
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);
		
		View v;
		if(convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.tweet_item, parent, false);
		}
		else {
			v = convertView;
		}
		
		ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		TextView tvTime = (TextView) v.findViewById(R.id.tvTime);
		TextView tvScreenName = (TextView) v.findViewById(R.id.tvScreenName);
		
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(tweet.getUser().getProfileImgUrl(), ivProfileImage);
		tvUserName.setText(tweet.getUser().getName());
		tvBody.setText(tweet.getBody());
		tvTime.setText(getRelativeTime(tweet.getCreatedAt()));
		tvScreenName.setText("@" + tweet.getUser().getScreenName());
		
		ivProfileImage.setOnClickListener(new OnClickListener() {
			Tweet t = getItem(position);
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), ProfileActivity.class);
				i.putExtra("user", t.getUser());
				getContext().startActivity(i);
			}
		});
			
		return v;
	}
	
	private String getRelativeTime(String time) {
		try {
			Date d = new SimpleDateFormat("EEE MMM dd kk:mm:ss ZZZZ yyyy", Locale.ENGLISH).parse(time);
			Date now = new Date();
			long diff = now.getTime() - d.getTime();
			//less than 1 minute
			if( diff <= 60000) {
				return "Now";
			}
			//less than 1 hour
			else if ( diff <= 3600000) {
				return String.valueOf( diff / 60000 ) + "m";
			}
			//less than 1 day
			else if ( diff <= 3600000 * 24 ) {
				return String.valueOf( diff / 3600000) + "h";
			}
			else {
				return String.valueOf( diff / (3600000 * 24)) + "d";
			}
		} catch (ParseException e) {
			return time;
		}
	}

}
