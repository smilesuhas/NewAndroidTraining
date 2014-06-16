package com.codepath.example.gridimagesearch;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	Integer imageStart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		imageAdapter=new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		
		gvResults.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i= new Intent(getApplicationContext(),ImageDisplayActivity.class);
				ImageResult	imageResult =imageResults.get(position);
//				i.putExtra("url", imageResult.getFullUrl());
				i.putExtra("result", imageResult);

				startActivity(i);
				}
			});
		gvResults.setOnScrollListener(new EndlessScrollListener() {
	    @Override
	    public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
			getImages();
//			Toast.makeText(getApplicationContext(), "Searching for... " + totalItemsCount+"  "+ page, Toast.LENGTH_SHORT).show();

	        customLoadMoreDataFromApi(page); 
                // or customLoadMoreDataFromApi(totalItemsCount); 
	    }

        });
	}

	public void customLoadMoreDataFromApi(int page) {
		
	}
	
	public void setupViews() {
		// TODO Auto-generated method stub
		etQuery=(EditText) findViewById(R.id.etQuery);
		btnSearch=(Button) findViewById(R.id.btnSearch);
		gvResults=(GridView) findViewById(R.id.gvResults);

	}
	public void onSearch(View v){
		 String query=etQuery.getText().toString();
		 imageStart=0;

		Toast.makeText(getApplicationContext(), "Searching for... " + query, Toast.LENGTH_SHORT).show();
		

 		getImages();

 
	}
		public void getImages(){
			 String query=etQuery.getText().toString();

			AsyncHttpClient client = new AsyncHttpClient(); //access the internet asynchronously
			//https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android
	 		client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" + "start=" + imageStart+
					"&v=1.0&q="  + Uri.encode(query),
					new JsonHttpResponseHandler(){
				
						public void onSuccess (JSONObject response) {
							JSONArray imageJsonResults=null;
							try{
								imageJsonResults=response.getJSONObject("responseData").getJSONArray("results");
								if(imageStart==0) 
									{imageResults.clear();}
								
								imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
								Log.d("DEBUG",imageResults.toString());
							} catch (JSONException e ) {
								e.printStackTrace();
							}
						}
			});
	 		imageStart=imageStart+8;
		}

		public void onFilter (MenuItem mi){
			Toast.makeText(this, "Clicked!", Toast.LENGTH_SHORT).show();
			Intent i= new Intent (getBaseContext(),SearchFilterActivity.class);
			i.putExtra("text",etQuery.getText().toString());
			startActivityForResult (i,50); //execute
		}

	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.menu_simple, menu);
	        return true;
	    }

 }
