package com.liezh.onerecord;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.liezh.onerecord.dao.NoteBookDao;
import com.liezh.onerecord.dao.RecordDao;
import com.liezh.onerecord.entity.NoteBook;
import com.liezh.onerecord.entity.Record;
import com.liezh.onerecord.tool.RecordListAdapter;
import com.liezh.onerecord.view.ArcMenu;
import com.liezh.onerecord.view.ArcMenu.OnMenuItemClickListener;

public class MainActivity extends Activity {

	private ActionBar actionBar ;
	private ArcMenu mArcMenu;
	private ListView mListView;
	private List<String> mDatas;
	private RecordAsyncTask asyncTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.list_syn);
		mArcMenu = (ArcMenu) findViewById(R.id.arc_menu);
		asyncTask = new RecordAsyncTask();
		asyncTask.execute("record");
		initEvent();
//		initData();
	}
	
	private void initData() {
		NoteBook notebook = new NoteBook("第一个记事本");
		NoteBookDao dao = new NoteBookDao(MainActivity.this, "MyRecord.db", 1);
		dao.save(notebook);
		List<NoteBook> list = dao.getAllNoteBook();
		System.out.println("notebook --- " + list.get(0).getBook_name());
		dao.close();
		System.out.println("初始数据成功！！！");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	private void initEvent()
	{
		mListView.setOnScrollListener(new OnScrollListener()
		{

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{

			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount)
			{
				if (mArcMenu.isOpen())
					mArcMenu.toggleMenu(600);
			}
		});
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Record record = (Record) mListView.getItemAtPosition(position);
				System.out.println("item --- " + record.toString());
				Intent intent = new Intent(MainActivity.this,EditRecord.class);
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
		
		// 长按item就弹出对话框提醒删除
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Record record = (Record) mListView.getItemAtPosition(position);
				dialog(record.getId());
				return true;
			}
		});
		
		mArcMenu.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			@Override
			public void onClick(View view, int pos)
			{
				Toast.makeText(MainActivity.this, pos+":"+view.getTag(), Toast.LENGTH_SHORT).show();
				if(pos == 1)
				{
					// 文字
					Intent intent = new Intent(MainActivity.this,AddRecord.class);
					intent.putExtra("action", 1);
					startActivity(intent);
				} 
				else if(pos == 2)
				{
					// 拍照
					Intent intent = new Intent(MainActivity.this,AddRecord.class);
					intent.putExtra("action", 2);
					startActivity(intent);
				}
				else if(pos == 3)
				{
					// 录音
					Intent intent = new Intent(MainActivity.this,AddRecord.class);
					intent.putExtra("action", 3);
					startActivity(intent);
					
				}
				else if(pos == 4)
				{
					// 提醒
					Intent intent = new Intent(MainActivity.this,AddRecord.class);
					intent.putExtra("action", 4);
					startActivity(intent);
					
				}
				else
				{
					// 文件
					Intent intent = new Intent(MainActivity.this,AddRecord.class);
					intent.putExtra("action", 5);
					startActivity(intent);
					
				}
			}
		});
	}
	
	protected void dialog(final int rid) {
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setMessage("确定要删除吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						RecordDao dao = new RecordDao(MainActivity.this, "MyRecord.db", 1);
						dao.setStateTo0ByRid(rid);
						onRestart();
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}
	
	/**
	 * 实现了数据库的异步访问
	 * @author Administrator
	 *
	 */
	class RecordAsyncTask extends AsyncTask<String, Void, List<Record>> {

		@Override
		protected List<Record> doInBackground(String... params) {
			RecordDao dao = new RecordDao(MainActivity.this, "MyRecord.db", 1);
			//按照表明查找所有数据，并以list返回
			List<Record> list = dao.getAllState1Record();
			dao.close();
//			System.out.println("count--" + list.size());
			return list;
		}
		
		
		@Override
		protected void onPostExecute(List<Record> result) {
			// TODO 自动生成的方法存根
			super.onPostExecute(result);
			RecordListAdapter adapter = new RecordListAdapter(MainActivity.this, result);
			mListView.setAdapter(adapter);
		}

	}
	
	// activity 更新listView的数据
	@Override
	protected void onRestart() {
		super.onRestart();
		asyncTask = new RecordAsyncTask();
		asyncTask.execute("record");
	}
	
	// 左边菜单的各个监听事件
	
	public void getAllRecord(View view) {
		onRestart();
	}
	
	public void getAllNoteBook(View view) {
		Intent intent = new Intent(MainActivity.this, NoteBookActivity.class);
		startActivity(intent);
	}
	
	public void getAllRecovery(View view) {
		Intent intent = new Intent(MainActivity.this, RecoveryActivity.class);
		startActivity(intent);
	}
	
	public void uploadToCloud(View view) {
		Intent intent = new Intent(MainActivity.this, CloudActivity.class);
		startActivity(intent);
	}
	public void getAllTheme(View view) {
		Intent intent = new Intent(MainActivity.this, ThemeActivity.class);
		startActivity(intent);

	}
	
	
}
