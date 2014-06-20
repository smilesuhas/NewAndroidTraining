package com.codepath.apps.basictwitter;

 import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;


public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

 	public TweetArrayAdapter(Context context, List <Tweet> tweets) {
		super(context,0,tweets );
 	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
 		//return super.getView(position, convertView, parent);
		
		//get data item for position
		Tweet tweet =getItem(position);
		//find or inflate template
		View v;
		if(convertView==null){
			LayoutInflater inflater= LayoutInflater.from(getContext());
			v= inflater.inflate(R.layout.tweet_item,parent,false);
		} else {
			v=convertView;
		}
		//find the views within the template
		ImageView ivProfileImage=(ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvUserName=(TextView) v.findViewById(R.id.tvUserName);
		TextView tvBody=(TextView) v.findViewById(R.id.tvBody);
		TextView tvUserId=(TextView) v.findViewById(R.id.tvUserId);
		TextView tvCreatedAt=(TextView) v.findViewById(R.id.tvCreatedAt);

		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader=ImageLoader.getInstance();
		
		//populate views with tweet data
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(),ivProfileImage);
		tvUserId.setText("@"+tweet.getUser().getScreenName());
		tvBody.setText(tweet.getBody());
		tvUserName.setText(tweet.getUser().getName());
		String createdAt =tweet.getCreatedAt();
		tvCreatedAt.setText(tweet.getCreatedAt());
		//	Log.d("debug",tweet.getBody().toString());
		tvCreatedAt.setText(getTweetFormatDate(createdAt));
		return v;
	}
	
	
	
	
 	private String  getTweetFormatDate(String createdAt) {
 		//createdAt="Thu Jun 19 22:34:36 +0000 2014";
  	//get current date parameters		
		

		 	//get TWEET date parameters				
 			SimpleDateFormat  tweetDateFormat = new SimpleDateFormat("EEE MMMM dd k:mm:ss ZZ yyy"); //("yyyy-MM-dd'T'HH:mm:ss");  
  			Date c1 =null; 

 			    try { 
			    	   c1 = tweetDateFormat.parse(createdAt);
			    	  } catch (ParseException e) {
 			    	   e.printStackTrace();
			    	  }  
 			    Calendar thatDay = Calendar.getInstance();

			    thatDay.setTime(c1); 
			    thatDay.setTimeZone(TimeZone.getTimeZone("UTC"));

 			    Integer tweetHour= thatDay.get(Calendar.HOUR_OF_DAY);
			    Integer tweetMinute= thatDay.get(Calendar.MINUTE);
			    Integer tweetSecond= thatDay.get(Calendar.SECOND);
			    Integer tweetMonth= thatDay.get(Calendar.MONTH);
			    Integer tweetMonthDate= thatDay.get(Calendar.DAY_OF_MONTH);

			    Integer tweetDay= thatDay.get(Calendar.DAY_OF_YEAR) ;
			    Integer tweetYear= thatDay.get(Calendar.YEAR);
 			    Log.d("debug", "TW" + tweetYear+ " "+tweetMonth +" " +tweetDay + " " +tweetHour.toString()+" " 
 			    		+tweetMinute+" " +tweetSecond);
   
 			    
 			   Calendar c = Calendar.getInstance(); 
 				c.setTimeZone(TimeZone.getTimeZone("UTC"));
 				Integer currentYear = c.get(Calendar.YEAR) ;
 				Integer currentMonth = c.get(Calendar.MONDAY);

 				Integer currentYearDay = c.get(Calendar.DAY_OF_YEAR);
 				Integer currentHour =c.get(Calendar.HOUR_OF_DAY);
 				Integer currentMinute = c.get(Calendar.MINUTE);
 				Integer currentSecond = c.get(Calendar.SECOND);
 		   
 				 	Log.d("debug",  "CR"+currentYear.toString()+" "+currentMonth+" "+currentYearDay.toString()+" " +currentHour.toString()+" "
 				 	+currentMinute.toString() +" "+currentSecond.toString());
 	//Render the time in twitter format
		String easyTweetTime = null;
		
		Integer TweetTotalDays =0;
		if(currentYear>tweetYear){ 
			int leap=0;
			if(tweetYear % 4==0) { 
				leap =1;
			}  
		  TweetTotalDays = (365-tweetDay+leap+currentYearDay)+365*(currentYear-tweetYear-1)  ;
		} else{
			TweetTotalDays = (currentYearDay-tweetDay);
		} // 9 4   8 42  0 days 
		
		 Integer TweetTotalHours =0;
//		if(tweetDay>currentYearDay){ 
//			
//			TweetTotalHours = (24-tweetHour+currentHour)  ;
//		} else{
//			TweetTotalHours = (currentHour-tweetHour);
//		}  //0 hours
		
		 Integer TweetTotalMinutes=0 ;
		if(tweetHour>currentHour){ 
			TweetTotalHours = (24-tweetHour+currentHour);
			//TweetTotalMinutes = (60-tweetMinute+currentMinute)  ;
			TweetTotalDays=TweetTotalDays-1;
		}else{
			TweetTotalHours = (currentHour-tweetHour);
					//TweetTotalMinutes = (currentMinute-tweetMinute)  ;
		} //4 min

		 Integer TweetTotalSeconds =0;
		if(tweetMinute>currentMinute){ 
			
			TweetTotalMinutes = (60-tweetMinute+currentMinute)  ;
			TweetTotalHours=TweetTotalHours-1;
		} else{
			TweetTotalMinutes = (currentMinute-tweetMinute);
			}
		
		if (tweetSecond>currentSecond){
		
			TweetTotalSeconds = (60+currentSecond-tweetSecond)  ;
			TweetTotalMinutes=TweetTotalMinutes-1;
		} else{ 
			TweetTotalSeconds=currentSecond-tweetSecond;
		}

				//(currentYearDay -tweetDay)*24*60*60 +( currentHour-tweetHour)*60*60+(currentMinute-tweetMinute)*60+ (currentSecond-tweetSecond);
 		if ((currentYear>tweetYear) &&(TweetTotalDays>=30) ) {
		//if ((CurrenTotalSeconds>=30*24*60*60) && (currentYear>tweetYear)) { //more than 7 days
			easyTweetTime=tweetMonthDate.toString()+" "+createdAt.substring(4, 7)+" " +tweetYear.toString().substring(2, 4); // 21 Dec 2013
 		} else if (TweetTotalDays >=7) {  //equality wont work directly :( because it compares the ids of the java object and not values, so using intValue
		//} else if (CurrenTotalSeconds>=7*24*60*60){
			easyTweetTime=tweetMonthDate.toString()+" "+createdAt.substring(4, 7); // 17 Jun
		} else if ( TweetTotalDays >=1)  { 
		//} else if (CurrenTotalSeconds>=24*60*60){
			easyTweetTime=(currentYearDay - tweetDay)  + "d";	//5d	
		} else if (TweetTotalHours>=1) {
	//	} else if ((currentHour - tweetHour)<24 && (currentHour - tweetHour)>=1) {
		//} else if (CurrenTotalSeconds>=60*60){
			easyTweetTime=(TweetTotalHours)  + "h";	//10h
		//}else if ((currentMinute -tweetMinute)<60 && (currentMinute -tweetMinute)>=1) {
		} else if (TweetTotalMinutes>=1){
			easyTweetTime=(TweetTotalMinutes)  + "m";	//10m
		}else   {
			easyTweetTime=(TweetTotalSeconds)  + "s";	//10s
		}
			return easyTweetTime;
	} 
 

}
