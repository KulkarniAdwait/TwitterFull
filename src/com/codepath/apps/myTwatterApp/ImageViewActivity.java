package com.codepath.apps.myTwatterApp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_view);
		
		getActionBar().hide();
		
		ImageView ivViewImage = (ImageView) findViewById(R.id.ivViewImage);
		ImageLoader imgLoader = ImageLoader.getInstance();
		
		imgLoader.displayImage(getIntent().getStringExtra("imgUrl"), ivViewImage);
	}
}
