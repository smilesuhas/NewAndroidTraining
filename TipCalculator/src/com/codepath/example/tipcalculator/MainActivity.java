package com.codepath.example.tipcalculator;

import java.text.NumberFormat;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;


public class MainActivity extends Activity { 
	float tipCalc;
	float tipPercent=0;
	float tipTotal;
	EditText etBillAmount;
	TextView tvFinalTip;
	TextView tvFinalTotal;
	float billAmount;
	ImageView ivCoins;
	Button btnTenPercent;
	Button btnFifteenPercent;
	Button btnTwentyPercent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
 		Toast.makeText(getApplicationContext(), "Hope you are having a good time here!",Toast.LENGTH_SHORT).show();
 		tvFinalTip = (TextView) findViewById(R.id.tvFinalTip);
 		tvFinalTotal = (TextView) findViewById(R.id.tvFinalTotal);
 		ivCoins = (ImageView) findViewById(R.id.ivCoins);
	// Customizing buttons
 		btnTenPercent = (Button) findViewById(R.id.btnTenPercent);
		btnFifteenPercent = (Button) findViewById(R.id.btnFifteenPercent);
		btnTwentyPercent = (Button) findViewById(R.id.btnTwentyPercent);
		
		 btnTenPercent.setBackgroundColor(Color.GRAY);  
		 btnFifteenPercent.setBackgroundColor(Color.GRAY);  
		 btnTwentyPercent.setBackgroundColor(Color.GRAY);  
	//
 		EditText etBillAmount = (EditText) findViewById(R.id.etBillAmount);

 	// Listener for DONE action on keyboard. Default calculation when DONE is clicked
 		etBillAmount.setOnEditorActionListener(new OnEditorActionListener() {
 	
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
 				 if (actionId == EditorInfo.IME_ACTION_DONE) {
                     if(tipPercent==0) {
                     Toast.makeText(MainActivity.this, "Tip % wasn't selected, defaulted to 10%", 
                    		 Toast.LENGTH_LONG).show();
            		 btnTenPercent.setBackgroundColor(Color.CYAN);  
            		 add10Percent(v);
            		 }
                // CODE BELOW IS TO HIDE THE KEYBOARD WHEN DONE IS PRESSED
            		 InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            		 imm.hideSoftInputFromWindow(v.getWindowToken(), 0);   
            	//
                     return true;
				 }

				return false;
			}
 		});
 	//
	}

   
	public void add10Percent(View v) {

		tipPercent=  (float) 0.10;

		//Changing the button colors to highlight the selected one
		 btnTenPercent.setBackgroundColor(Color.CYAN);  
		 btnFifteenPercent.setBackgroundColor(Color.GRAY);  
		 btnTwentyPercent.setBackgroundColor(Color.GRAY);  

		calculateTip();

		
		}
	public void add15Percent(View v) {
		tipPercent=  (float) 0.15;
		//Changing the button colors to highlight the selected one
		 btnTenPercent.setBackgroundColor(Color.GRAY);  
		 btnFifteenPercent.setBackgroundColor(Color.CYAN);  
		 btnTwentyPercent.setBackgroundColor(Color.GRAY);  

 		calculateTip();
		}
	public void add20Percent(View v) {
		tipPercent=  (float) 0.20;
		//Changing the button colors to highlight the selected one
		 btnTenPercent.setBackgroundColor(Color.GRAY);  
		 btnFifteenPercent.setBackgroundColor(Color.GRAY);  
		 btnTwentyPercent.setBackgroundColor(Color.CYAN);  

 		calculateTip();
		}
	
	public void calculateTip() {
 

 		EditText etBillAmount = (EditText) findViewById(R.id.etBillAmount);

		billAmount =Float.valueOf(etBillAmount.getText().toString());

		tipCalc=  (tipPercent*billAmount);
		tipTotal = tipCalc+billAmount;
		
		NumberFormat formatter = NumberFormat.getCurrencyInstance(); //using formatter to format to currency
		
		tvFinalTip.setText( formatter.format(tipCalc)); 			//using formatter to format to currency
		 tvFinalTotal.setText(formatter.format( tipTotal));			//using formatter to format to currency
		 
		  ivCoins.setVisibility(View.VISIBLE); //displaying a image icon
		 
		 // Keeping track of any further number changes
	 		etBillAmount.addTextChangedListener(new TextWatcher() {
	 		    public void onTextChanged(CharSequence s, int start, int before, int count) {
	 		        // TODO Auto-generated method stub
	 		         if(tipPercent>0) {
	 		        	calculateTip();
	 		         }
	 		         
	 		        
	 		    	}

	 			@Override
	 			public void beforeTextChanged(CharSequence s, int start, int count,
	 					int after) {
	 			}

	 			@Override
	 			public void afterTextChanged(Editable s) {
	 			}});
	 		//
 		}
 
// NOT SURE WHY I WAS NOT ABLE TO CREATE THIS LISTENER AS A SEAPARATE FUNCTION , THE WAY WE DID IN TODO APP
	
//	public void setupTextListener() {
//	etBillAmount.addTextChangedListener(new TextWatcher() {
//	    public void onTextChanged(CharSequence s, int start, int before, int count) {
//	        // TODO Auto-generated method stub
//	         if(tipPercent>0) {
//	        	calculateTip();
//	         }
//	         
//	        
//	    	}
//
//		@Override
//		public void beforeTextChanged(CharSequence s, int start, int count,
//				int after) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void afterTextChanged(Editable s) {
//			// TODO Auto-generated method stub
//			
//		}});
//	}
	
}
