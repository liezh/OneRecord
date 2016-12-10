package com.liezh.onerecord;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.liezh.onerecord.view.ArcMenu;
import com.liezh.onerecord.view.ArcMenu.OnMenuItemClickListener;

public class MainActivity extends Activity {

	private ActionBar actionBar ;
	private ArcMenu mArcMenu;
	private ListView list_syn;
	private List<String> mDatas;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		list_syn = (ListView) findViewById(R.id.list_syn);
		mArcMenu = (ArcMenu) findViewById(R.id.arc_menu);
		initData();
		list_syn.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mDatas));
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
		list_syn.setOnScrollListener(new OnScrollListener()
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
		
		mArcMenu.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			@Override
			public void onClick(View view, int pos)
			{
				Toast.makeText(MainActivity.this, pos+":"+view.getTag(), Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void initData()
	{
		mDatas = new ArrayList<String>();

		for (int i = 'A'; i < 'Z'; i++)
		{
			mDatas.add((char) i + "");
		}

	}
	
}
