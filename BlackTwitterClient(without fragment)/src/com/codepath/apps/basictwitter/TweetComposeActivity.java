package com.codepath.apps.basictwitter;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TweetComposeActivity extends Activity {
	EditText etComposeText;
	TextView tvCounter;	
	private TwitterClient client;
	String myCompoedTweet="";
	public int counter=140;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tweet_compose_activity);
		etComposeText=(EditText) findViewById(R.id.etComposeText);
		tvCounter=(TextView) findViewById(R.id.tvCounter);
		client=TwitterApplication.getRestClient(); // will give u instance of twitter client that logged in

		etComposeText.addTextChangedListener(new TextWatcher() {
 		    public void onTextChanged(CharSequence s, int start, int before, int count) {
 				 counter=etComposeText.length();
				counter=140-counter;
				//String counter1=String.valueOf(counter);
				tvCounter.setText(String.valueOf(counter));
				if (counter<0){
					tvCounter.setTextColor(Color.RED);
				} else
				{
					tvCounter.setTextColor(Color.GRAY);
				}
 		    	}

 			@Override
 			public void beforeTextChanged(CharSequence s, int start, int count,
 					int after) {
 			}

 			@Override
 			public void afterTextChanged(Editable s) {
 			}});

}
	
	public void onCompose (View v){
		if(counter<0)
		{
			Toast.makeText(this, "Letters more than 140,  unable to tweet. Please edit.",Toast.LENGTH_SHORT).show();
		}	
		else{
			myCompoedTweet=etComposeText.getText().toString() ;
//			Toast.makeText(this, myCompoedTweet, Toast.LENGTH_SHORT).show(); 
			postTweet();
			Toast.makeText(this, "tweet...tweet...tweet...", Toast.LENGTH_SHORT).show();
			Intent i=new Intent();
	 	//	i.putExtra("value", "tweeted: " + myCompoedTweet);
			setResult(RESULT_OK, i);
			finish();
		}
	}
	public void postTweet(){
	client.postNewTweet(    myCompoedTweet, new JsonHttpResponseHandler(){  //as defined in lib  ////**************************************************************TODO
		@Override
		public void onSuccess(JSONArray json) {
			Log.d("debug","tweet posted:"+myCompoedTweet);

		}
		@Override
		public void onFailure(Throwable e,String s) {
			Log.d("debug",e.toString());
			Log.d("debug",s.toString());

		}
	});
	}
}