package com.codepath.apps.myTwatterApp;

import com.loopj.android.http.RequestParams;

public class TwitterParamBuilder {
	RequestParams params;
	
	public TwitterParamBuilder(){
		params = new RequestParams();
	}
	
	public RequestParams buildParams() {
		params.put("count", "20");
		return params;
	}
	
	public TwitterParamBuilder userId(long user_id) {
		this.params.put("user_id", String.valueOf(user_id));
		return this;
	}
	
	public TwitterParamBuilder sinceId(long since_id) {
		this.params.put("since_id", String.valueOf(since_id));
		return this;
	}
	
	public TwitterParamBuilder maxId(long max_id) {
		if(max_id != 0l) {
			this.params.put("max_id", String.valueOf(max_id));
		}
		return this;
	}
}
