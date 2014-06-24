package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private  PullToRefreshListView lvTweets;
 	private EditText etCompose;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
 
		client=TwitterApplication.getRestClient(); // will give u instance of twitter client that logged in
   		
		//populateTimeline();

		lvTweets=(PullToRefreshListView) findViewById (R.id.lvTweets);
		etCompose=(EditText) findViewById(R.id.etCompose);
		
		tweets=new ArrayList<Tweet>();
		//aTweets= new ArrayAdapter<Tweet>(this, android.R.layout.simple_list_item_1, tweets);
		aTweets= new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
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
			//if (<50)
			populateTimeline();
		}
        });
 
		etCompose.setOnFocusChangeListener(new OnFocusChangeListener(){
		    public void onFocusChange(View v, boolean hasFocus){
		    	onCompose(v);
		    	}
		});

		lvTweets.setOnRefreshListener(new OnRefreshListener() {
			
            @Override
            public void onRefresh() {
                // Your code to refresh the list contents
                // Make sure you call listView.onRefreshComplete()
                // once the loading is done. This can be done from here or any
                // place such as when the network request has completed successfully.
        		//lvTweets.clearChoices();
            	aTweets.clear();
            	aTweets.notifyDataSetChanged() ;
  				Log.d("debug","inside on refresh");

                fetchTimelineAsync(0);
            }
        });

	}

	//****** On Scroll ************
	public void populateTimeline(){

 		client.getHomeTimeline(   1, new JsonHttpResponseHandler(){  //as defined in lib  ////**************************************************************TODO
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
 				lvTweets.onRefreshComplete();

 				aTweets.addAll(Tweet.fromJSONArray(  json));  ////*******
  				}
			@Override
			public void onFailure(Throwable e,String s) {
				Log.d("debug",e.toString());
				Log.d("debug",s.toString());

			}
		});
	} 
	
	//  ********* On Pull to Refresh ******************
	public void fetchTimelineAsync(int page) {
        client.getHomeTimeline(page, new JsonHttpResponseHandler() {
            public void onSuccess(JSONArray json) {
                // ...the data has come back, finish populating listview...
                // Now we call onRefreshComplete to signify refresh has finished
				Log.d("debug","inside on success");
  				aTweets.addAll(Tweet.fromJSONArray(  json));  ////***********
 				lvTweets.onRefreshComplete();
  				Log.d("debug","refresh complete");
             }
             public void onFailure(Throwable e) {
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
                lvTweets.onRefreshComplete();
            }
        });
    }
	public void onCompose(View v){
		Toast.makeText(this, "Lets Tweet!", Toast.LENGTH_SHORT).show();
 		Intent i= new Intent (getBaseContext(),TweetComposeActivity.class);
		i.putExtra("text","This is a test bar");
 		startActivityForResult (i,50); //execute
 		
	}
	
	//**** On sending new composed tweet , come back and mimic pulltorefresh ********
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK){
			if(requestCode==50){
				String result=data.getStringExtra("value");
				//Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
				tweets.clear();
              	aTweets.clear();
            	aTweets.notifyDataSetChanged() ;
  				Log.d("debug","inside on refresh");

                fetchTimelineAsync(0);

 				//fetchTimelineAsync(0);
			}
		}
 //		super.onActivityResult(requestCode, resultCode, data);
	}


 }