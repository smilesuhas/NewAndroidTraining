package codepath.apps.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;
 
public class ToDoActivity extends Activity {
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	EditText etNewItem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		lvItems = (ListView) findViewById(R.id.lvItems);
		readItems();
		
		//items = new ArrayList<String>();
		itemsAdapter=new ArrayAdapter<String> (getBaseContext(),android.R.layout.simple_list_item_1,items);
		//itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
		
		lvItems.setAdapter(itemsAdapter);
		//items.add("First Item");
		//items.add("Second Item");
		setupListViewListener();
		//setupListViewClickListener();
 	}
	private final int REQUEST_CODE = 20;

	private void setupListViewListener() {
		// TODO Auto-generated method stub
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){
			public boolean onItemLongClick(AdapterView<?> parent,View view,int position,long rowId){
				items.remove(position);
				itemsAdapter. notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
		lvItems.setOnItemClickListener(new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent,View view,int position,long rowId){
				  Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
				  i.putExtra("item",items.get(position)); 
				  i.putExtra("itemPosition", position); 

				  i.putExtra("mode", 2); // pass arbitrary data to launched activity
				  startActivityForResult(i, REQUEST_CODE); // brings up the second activity

			}
	});

	
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  // REQUEST_CODE is defined above
		  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
		     // Extract name value from result extras
		     String itemReturn = data.getExtras().getString("itemReturn");
		      final int positionReturn = data.getExtras().getInt("positionReturn");
		     // Toast the name to display temporarily on screen
		     Toast.makeText(this, itemReturn, Toast.LENGTH_SHORT).show();
		     
				items.remove(positionReturn);
				items.add(positionReturn, itemReturn);
				//itemsAdapter.add(itemReturn);
				itemsAdapter.notifyDataSetChanged();
				saveItems();

		  }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do, menu);
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

	public void addTodoItem(View v) {
		EditText etNewItem=(EditText)findViewById(R.id.etNewItem);
		itemsAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");
		saveItems();	//write to file
	}
	private void readItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir,"todo.txt");
		try{
			items=new ArrayList<String> (FileUtils.readLines(todoFile));
		} catch (IOException e){
			items=new ArrayList<String>();
			e.printStackTrace();
		}
	}
	private void saveItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir,"todo.txt");
		try{
			FileUtils.writeLines(todoFile,items);
		} catch (IOException e){
			e.printStackTrace();
		}
	}

}
