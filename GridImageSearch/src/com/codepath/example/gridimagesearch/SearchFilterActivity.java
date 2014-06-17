package com.codepath.example.gridimagesearch;

import java.util.Arrays;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchFilterActivity extends Activity {
	  public Spinner spnSize, spnColor,spnType;
	  public EditText etSite;
	  public Button btnSave;
	  String sizeImage="any",colorImage="any",siteImage= "any";
	  String   typeSplitter="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_filter);
		spnSize = (Spinner) findViewById(R.id.spnSize);
		spnColor = (Spinner) findViewById(R.id.spnColor);
		spnType = (Spinner) findViewById(R.id.spnType);
		etSite = (EditText) findViewById(R.id.etSite);

		Pass_On pass_on=(Pass_On)getIntent().getSerializableExtra("Pass_On");
		
		// TO SET BACK THE FILTER SELECTED VALUES IF ONE WANTS TO CHANGE THE FILTERS AGAIN
		spnSize.setSelection(getIndex(spnSize, Pass_On.ImageSize));
		spnColor.setSelection(getIndex(spnColor, Pass_On.ImageColor));
		spnType.setSelection(getIndex(spnType, Pass_On.ImageType));
		etSite.setText(Pass_On.ImageSite); 

 		
		}

// GET THE SELECTION SET IN THE SPINNERS BASED ON SAVED FILTER VALUES
 private int getIndex(Spinner spinner, String myString) {
	  int index = 0;

	  for (int i=0;i<spinner.getCount();i++){
	   if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
	    index = i;
	    i=spinner.getCount();//will stop the loop, kind of break, by making condition false
	   }
	  }
	  return index;

 	}


public void OnSave(View v) {

	 sizeImage= spnSize.getSelectedItem().toString().toLowerCase(Locale.US);
	 colorImage= spnColor.getSelectedItem().toString().toLowerCase(Locale.US) ;
	 typeSplitter = spnType.getSelectedItem().toString().toLowerCase(Locale.US);
	 siteImage=etSite.getText().toString().trim().toLowerCase(Locale.US); 

	sendBackFilters();

	}

public void sendBackFilters ( ){
	Intent i=new Intent();
	i.putExtra("sizeImage", sizeImage);
	i.putExtra("colorImage", colorImage);
	i.putExtra("typeImage", typeSplitter );
	i.putExtra("siteImage", siteImage);
	setResult(RESULT_OK, i);
	finish();
}

}