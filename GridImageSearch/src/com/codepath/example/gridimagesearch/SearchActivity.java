package com.codepath.example.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
	String query;
	String FilterURL;
	
	String sizeImage="any";
	String colorImage="any";
	String typeImage="any";
	String siteImage="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		isNetworkAvailable();
 		imageAdapter=new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		
 		//For showing Image in full screen
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
		
		//for on scroll loading more images
		gvResults.setOnScrollListener(new EndlessScrollListener() {
	    @Override
	    public void onLoadMore(int page, int totalItemsCount) {
        // Triggered only when new data needs to be appended to the list
        // Add whatever code is needed to append new items to your AdapterView
			getImages();  //Load more images
		//	Toast.makeText(getApplicationContext(), "Searching for... " + totalItemsCount+"  "+ page, Toast.LENGTH_SHORT).show();
	
	        customLoadMoreDataFromApi(page); 
            // or customLoadMoreDataFromApi(totalItemsCount); 
	    }

        });
	}

	public void customLoadMoreDataFromApi(int page) {
		
	}
	private Boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    if ( activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
			//Toast.makeText(getApplicationContext(), "YES Network Available!!", Toast.LENGTH_LONG).show();

	        return true;
	    }
		Toast.makeText(getApplicationContext(), "No Network Available!!", Toast.LENGTH_LONG).show();

	    return false;
	}

//	public Boolean isNetworkAvailable() {
//	    try {
//	        Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
//	        int returnVal = p1.waitFor();
//	        boolean reachable = (returnVal==0);
//	        return reachable;
//	    } catch (Exception e) {
// 	        e.printStackTrace();
//	    }
//	    Toast.makeText(getApplicationContext(), "No Network Available!!", Toast.LENGTH_LONG).show();
//	    return false;
//	}

	public void setupViews() {
 		etQuery=(EditText) findViewById(R.id.etQuery);
		btnSearch=(Button) findViewById(R.id.btnSearch);
		gvResults=(GridView) findViewById(R.id.gvResults);

	}
	public void onSearch(View v){
		 
		 String query=etQuery.getText().toString();
		 imageStart=0;
		 FilterURL="&imgsz="+sizeImage+"&imgcolor="+colorImage+"&imgtype="+typeImage;
		
		 //TO handle he site filter so that we dont pass it when nnothing is filtered for site
		 if (siteImage.length() > 0){
			 FilterURL=FilterURL+"&as_sitesearch="+siteImage;
		}
		Toast.makeText(getApplicationContext(), "Searching for... " + query, Toast.LENGTH_SHORT).show();
		
		if(imageStart==0) 
		{imageResults.clear();}
		getImages();  //Load images
		  
	}
		public void getImages(){
			  query=etQuery.getText().toString();

			AsyncHttpClient client = new AsyncHttpClient(); //access the internet asynchronously


//	 		client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" + "start=" + imageStart+
//					"&v=1.0&q="  + Uri.encode(query)+"&imgsz=any"+"&imgcolor=any"+"&imgtype=any"+"&as_sitesearch=any",
	 		client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" + "start=" + imageStart+
			"&v=1.0&q="  + Uri.encode(query)+FilterURL,
					new JsonHttpResponseHandler(){
				
						public void onSuccess (JSONObject response) {
							JSONArray imageJsonResults=null;
							try{
								imageJsonResults=response.getJSONObject("responseData").getJSONArray("results");
								
								imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
								Log.d("DEBUG",imageResults.toString());
							} catch (JSONException e ) {
								e.printStackTrace();
							}
						}
			});
	 		imageStart=imageStart+8; // set the next image start counter
		}

		public void onFilter (MenuItem mi){
			//Toast.makeText(this, "Clicked!", Toast.LENGTH_SHORT).show();
			Intent p= new Intent (getBaseContext(),SearchFilterActivity.class);
 			Pass_On pass_on=new Pass_On(sizeImage,colorImage,typeImage,siteImage);
 			p.putExtra("pass_on",pass_on); //pass data
			startActivityForResult (p,50); //execute
		}
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if(resultCode==RESULT_OK){
				if(requestCode==50){
					 sizeImage=data.getStringExtra("sizeImage");
					 colorImage=data.getStringExtra("colorImage");
					 typeImage=data.getStringExtra("typeImage");
					 siteImage=data.getStringExtra("siteImage");
					 
//					Toast.makeText(this, sizeImage+colorImage+typeImage+siteImage, Toast.LENGTH_SHORT).show();
					onSearch(null );//reapply filters
				}
			}
	 //		super.onActivityResult(requestCode, resultCode, data);
		}
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.menu_simple, menu);
	        return true;
	    }

 	
 }
