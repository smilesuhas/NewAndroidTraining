package com.codepath.apps.basictwitter;

import org.json.JSONObject;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;
import android.view.Window;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "ueE7e9wACqs40kOwpvaztiXEW";       // Change this
    public static final String REST_CONSUMER_SECRET = "jfJbWWWAWx2wtLcAwX4jYw0kM8vRnZnLeepRNnf6nkcRmzYaXu"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change this (here and in manifest)
    public static Integer loadMoreTweetId =1;
     public Long amaxTweetId= null ;// Long.parseLong("1");
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getHomeTimeline( int refresh1, AsyncHttpResponseHandler handler) {  ////********
    	String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        
        //****This would reset variables on pulltorefresh refresh1 is set to 0 **********
        if(Integer.valueOf(refresh1)==0){
        	Log.d("debug" , "checking refresh");
        	loadMoreTweetId=(Integer)1;
        	amaxTweetId=(long) 1;
        	params.put("max_id", "null");
        }
        else{
        	amaxTweetId=Tweet.maxTweetId;
        }
        params.put("count", "10");
        // ***if we are not on first page , only then pass the maxid 
        if(Integer.valueOf(loadMoreTweetId)>=10){
        params.put("max_id", amaxTweetId.toString() ); ////************ 
        }
        params.put("since_id", loadMoreTweetId.toString());	
          
         Log.d("debug test",Tweet.maxTweetId+" max");
//         Log.d("debug",amaxTweetId.toString()+" max var id");
         client.get(apiUrl, params, handler);// or pass null for no params
       //        if (loadMoreTweetId>10) {
         loadMoreTweetId=loadMoreTweetId+10;
        // }

         Log.d("debug test",loadMoreTweetId+" ");
         Log.d("debug test",Tweet.maxTweetId+" after");
      }

    
    public void postNewTweet(String myTweet, AsyncHttpResponseHandler handler) {  ////*************
    	String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
         params.put("status", myTweet);	        
        Log.d("status",myTweet);
         client.post(apiUrl, params, handler);// or pass null for no params
       // Log.d("debug test",apiUrl);
     }
// 	private Tweet getApplicationContext() {
//   		return null;
// 	}
    
    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
  /*  public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("format", "json");
        client.get(apiUrl, params, handler);
    }
    */
    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}