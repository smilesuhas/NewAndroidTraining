package codepath.apps.simpletodo;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class EditItemActivity extends Activity {

	EditText etEditCurrentItem;
	public int itemPosition;
	public String item;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
 		    item = getIntent().getStringExtra("item").toString();
		    itemPosition = getIntent().getIntExtra("itemPosition",0);

		    etEditCurrentItem = (EditText)findViewById(R.id.etEditCurrentItem);
		    etEditCurrentItem.setText(item);
		    etEditCurrentItem.setSelectAllOnFocus(true);

 			Toast.makeText(this,etEditCurrentItem.getText(), Toast.LENGTH_SHORT).show();

 	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	public void saveEditItem(View v) {
		onSubmit(v);
	}
	
	public void onSubmit(View v) {
		  EditText etEditCurrentItem = (EditText) findViewById(R.id.etEditCurrentItem);

		  // Prepare data intent 
		  Intent data = new Intent();
		  // Pass relevant data back as a result
		  data.putExtra("itemReturn", etEditCurrentItem.getText().toString());
		  data.putExtra("positionReturn", itemPosition);

		  // Activity finished ok, return the data
		  setResult(RESULT_OK, data); // set result code and bundle data for response

		  // closes the activity and returns to first screen
		  this.finish(); 
		}

}
