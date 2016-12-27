package com.liezh.onerecord;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class CloudActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cloud);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cloud, menu);
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
	
	
	
	// 左边菜单的各个监听事件
	
	public void getAllRecord(View view) {
		Intent intent = new Intent(CloudActivity.this, MainActivity.class);
		startActivity(intent);
	}
	
	public void getAllNoteBook(View view) {
		Intent intent = new Intent(CloudActivity.this, NoteBookActivity.class);
		startActivity(intent);
	}
	
	public void getAllRecovery(View view) {
		Intent intent = new Intent(CloudActivity.this, RecoveryActivity.class);
		startActivity(intent);
	}
	
	public void uploadToCloud(View view) {
		onRestart();
	}
	public void getAllTheme(View view) {
		Intent intent = new Intent(CloudActivity.this, ThemeActivity.class);
		startActivity(intent);

	}
}
