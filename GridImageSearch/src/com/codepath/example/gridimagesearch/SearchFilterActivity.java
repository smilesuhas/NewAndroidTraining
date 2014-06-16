package com.codepath.example.gridimagesearch;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SearchFilterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_filter);
	}
}


//
//	TextView tvBar=(TextView) findViewById(R.id.tvBar);
//	TextView tvBaz=(TextView) findViewById(R.id.tvBaz);
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_activity_second);
//		Foo foo=(Foo)getIntent().getSerializableExtra("Foo");
//		
//		
//		tvBar.setText(foo.bar);
//		tvBaz.setText(foo.baz);
//		
//	}
//	public void onSubmit (View v){
//		Intent i=new Intent();
//		i.putExtra("value", "banana");
//		i.putExtra("foo", "foo");
//		setResult(RESULT_OK, i);
//		finish();
//	}
//}
