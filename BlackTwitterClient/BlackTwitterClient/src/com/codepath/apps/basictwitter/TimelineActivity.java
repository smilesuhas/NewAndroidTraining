package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.From;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class TimelineActivity extends Activity {
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
 	
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
 
		client=TwitterApplication.getRestClient(); // will give u instance of twitter client that logged in
   		
		populateTimeline();

		lvTweets=(ListView) findViewById (R.id.lvTweets);

		tweets=new ArrayList<Tweet>();
		//aTweets= new ArrayAdapter<Tweet>(this, android.R.layout.simple_list_item_1, tweets);
		aTweets= new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.clearChoices();

		lvTweets.setOnScrollListener(new EndlessScrollListener() {
	    @Override
	    public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
	        customLoadMoreDataFromApi(page); 
                // or customLoadMoreDataFromApi(totalItemsCount); 
	    }

		private void customLoadMoreDataFromApi(int offset) {
		      // This method probably sends out a network request and appends new data items to your adapter. 
		      // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
		      // Deserialize API response and then construct new objects to append to the adapter
			populateTimeline();
		}
        });

	}

	public void populateTimeline(){
 		client.getHomeTimeline(   new JsonHttpResponseHandler(){  //as defined in lib  ////**************************************************************TODO
			@Override
			public void onSuccess(JSONArray json) {
				//just to output all log jsons
//		        for(int i=0; i<json.toString().length(); i+=1024)
//		        {
//		            if(i+1024<json.toString().length())
//		                Log.d("debug", json.toString().substring(i, i+1024));
//		            else
//		                Log.d("debug", json.toString().substring(i, json.toString().length()));
//		        }

				//Log.d("debug",json.toString());
				aTweets.addAll(Tweet.fromJSONArray(  json));  ////**************************************************************
  			}
			@Override
			public void onFailure(Throwable e,String s) {
				Log.d("debug",e.toString());
				Log.d("debug",s.toString());

			}
		});
	}

 }