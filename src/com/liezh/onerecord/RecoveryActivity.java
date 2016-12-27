package com.liezh.onerecord;

import java.util.List;

import com.liezh.onerecord.NoteBookCatalogActivity.RecordAsyncTask;
import com.liezh.onerecord.dao.RecordDao;
import com.liezh.onerecord.entity.Record;
import com.liezh.onerecord.tool.RecordListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class RecoveryActivity extends Activity {

	private ListView mListView;
	private RecordAsyncTask asyncTask;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recovery);
		mListView = (ListView) findViewById(R.id.recovery_list_syn);
		asyncTask = new RecordAsyncTask();
		asyncTask.execute();
		initEvent();
	}

	
	@Override
	protected void onRestart() {
		// TODO 自动生成的方法存根
		super.onRestart();
		asyncTask = new RecordAsyncTask();
		asyncTask.execute();
	}

	
	
	
	private void initEvent() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Record record = (Record) mListView.getItemAtPosition(position);
				System.out.println("item --- " + record.toString());
				Intent intent = new Intent(RecoveryActivity.this,EditRecord.class);
				Bundle extras = new Bundle();
				extras.putInt(Record.ID, record.getId());
				extras.putString(Record.TITLE, record.getTitle());
				extras.putString(Record.CONTENT, record.getContent());
				extras.putString(Record.CREATE_DATE, record.getCreate_date());
				extras.putString(Record.STAR_DATE, record.getStar_date());
				extras.putInt(Record.CATEGORY, record.getCategory());
				extras.putInt(Record.STATE, record.getState());
				intent.putExtras(extras);
				startActivity(intent);
				
			}
		});
		
		
		
	}
	

	/**
	 * 实现了数据库的异步访问
	 * @author Administrator
	 *
	 */
	class RecordAsyncTask extends AsyncTask<Integer, Void, List<Record>> {

		@Override
		protected List<Record> doInBackground(Integer... params) {
			RecordDao dao = new RecordDao(RecoveryActivity.this, "MyRecord.db", 1);
			//按照表明查找所有数据，并以list返回
			List<Record> list = dao.getAllStateNot1Record();
			dao.close();
			System.out.println("count--" + list.size() + "--" );
			return list;
		}
		
		
		@Override
		protected void onPostExecute(List<Record> result) {
			// TODO 自动生成的方法存根
			super.onPostExecute(result);
			RecordListAdapter adapter = new RecordListAdapter(RecoveryActivity.this, result);
			mListView.setAdapter(adapter);
		}

	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recovery, menu);
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
		Intent intent = new Intent(RecoveryActivity.this, MainActivity.class);
		startActivity(intent);
	}
	
	public void getAllNoteBook(View view) {
		
		
		Intent intent = new Intent(RecoveryActivity.this, NoteBookActivity.class);
		startActivity(intent);
	}
	
	public void getAllRecovery(View view) {
		onRestart();
	}
	
	public void uploadToCloud(View view) {
		Intent intent = new Intent(RecoveryActivity.this, CloudActivity.class);
		startActivity(intent);
	}
	
	public void getAllTheme(View view) {
		Intent intent = new Intent(RecoveryActivity.this, ThemeActivity.class);
		startActivity(intent);

	}
}
