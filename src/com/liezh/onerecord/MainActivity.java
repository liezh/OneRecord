package com.liezh.onerecord;

import java.util.List;

import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
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
import android.widget.ListView;
import android.widget.Toast;

import com.liezh.onerecord.dao.RecordDao;
import com.liezh.onerecord.entity.Record;
import com.liezh.onerecord.tool.RecordListAdapter;
import com.liezh.onerecord.tool.RecordListAdapter.ViewHolder;
import com.liezh.onerecord.view.ArcMenu;
import com.liezh.onerecord.view.ArcMenu.OnMenuItemClickListener;
import com.nineoldandroids.view.ViewHelper;

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
//				int tag = (ViewHolder)view.
//				System.out.println("tag---"+tag);
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
					// ����
					Intent intent = new Intent(MainActivity.this,AddRecord.class);
					intent.putExtra("action", 1);
					startActivity(intent);
				} 
				else if(pos == 2)
				{
					// ����
					Intent intent = new Intent(MainActivity.this,AddRecord.class);
					intent.putExtra("action", 2);
					startActivity(intent);
				}
				else if(pos == 3)
				{
					// ¼��
					Intent intent = new Intent(MainActivity.this,AddRecord.class);
					intent.putExtra("action", 3);
					startActivity(intent);
					
				}
				else if(pos == 4)
				{
					// ����
					Intent intent = new Intent(MainActivity.this,AddRecord.class);
					intent.putExtra("action", 4);
					startActivity(intent);
					
				}
				else
				{
					// �ļ�
					Intent intent = new Intent(MainActivity.this,AddRecord.class);
					intent.putExtra("action", 5);
					startActivity(intent);
					
				}
			}
		});
	}
	
	
	/**
	 * ʵ�������ݿ���첽����
	 * @author Administrator
	 *
	 */
	class RecordAsyncTask extends AsyncTask<String, Void, List<Record>> {

		@Override
		protected List<Record> doInBackground(String... params) {
			RecordDao dao = new RecordDao(MainActivity.this, "MyRecord.db", 1);
			//���ձ��������������ݣ�����list����
			List<Record> list = dao.getAllRecord();
			dao.close();
//			System.out.println("count--" + list.size());
			return list;
		}
		
		
		@Override
		protected void onPostExecute(List<Record> result) {
			// TODO �Զ����ɵķ������
			super.onPostExecute(result);
			RecordListAdapter adapter = new RecordListAdapter(MainActivity.this, result);
			mListView.setAdapter(adapter);
		}

	}
	
	// activity ����listView������
	@Override
	protected void onRestart() {
		super.onRestart();
		asyncTask = new RecordAsyncTask();
		asyncTask.execute("record");
	}
	
	
	
}
