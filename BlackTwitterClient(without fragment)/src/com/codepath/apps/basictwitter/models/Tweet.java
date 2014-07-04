package com.codepath.apps.basictwitter.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {
	public static Long maxTweetId=(long) 1;
	private String body;
	public Long uid;
	private String createdAt;
	private User user;

	public String getBody() {
		return body;
	}

	public Long getUid() {
		return uid;
	}
//	
//	public void setUid(Long uid) {
//		this.uid =  uid;
//	}
	public String getCreatedAt() {
		return createdAt;
	}



	public User getUser() {
		return user;
	}

	public static Tweet fromJSON ( JSONObject jsonObject){  ////**************************************************************
		Tweet tweet = new Tweet();
		//Extract values from the json to populate the member variable
		try{
			tweet.body=jsonObject.getString("text");
			maxTweetId = tweet.uid =jsonObject.getLong("id");  //TODO
			maxTweetId=maxTweetId-1;
			tweet.createdAt=jsonObject.getString("created_at");

			tweet.user= User.fromJSON(jsonObject.getJSONObject("user"));
			// maxTweetId1=tweet.uid;	 ////**************************************************************
		} catch (JSONException e){
			e.printStackTrace();
			return null;
		}
		return tweet;
	}

	public static ArrayList< Tweet> fromJSONArray( JSONArray jsonArray) {  ////**************************************************************
		ArrayList< Tweet> tweets = new ArrayList< Tweet>(jsonArray.length());
 	
  		for(int i=0;i<jsonArray.length();i++)		{
			JSONObject tweetJson = null;
 		try{
			tweetJson=jsonArray.getJSONObject(i);
		} catch (JSONException e){
			e.printStackTrace();
			continue;
		}
		Tweet tweet =Tweet.fromJSON(  tweetJson); //getHomeTimeline  ////**************************************************************
		if(tweet!= null){
			tweets.add(tweet);
		}
		}
		return tweets;

	}
	@Override
	public String toString() {
 		return getBody()+" - " + getUser().getScreenName();
	}
}
