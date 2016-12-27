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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.liezh.onerecord.dao.NoteBookDao;
import com.liezh.onerecord.entity.NoteBook;
import com.liezh.onerecord.tool.NoteBookListAdapter;
import com.liezh.onerecord.view.ArcMenu;
import com.liezh.onerecord.view.ArcMenu.OnMenuItemClickListener;

public class NoteBookActivity extends Activity {

	private ActionBar actionBar;
	private ArcMenu mArcMenu;
	private ListView mListView;
	private List<String> mDatas;
	private NoteBookAsyncTask asyncTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_book);
		mListView = (ListView) findViewById(R.id.notebook_list_syn);
		mArcMenu = (ArcMenu) findViewById(R.id.arc_menu);
		asyncTask = new NoteBookAsyncTask();
		asyncTask.execute("note_book");
		initEvent();
	}
	
	@Override
	protected void onRestart() {
		// TODO �Զ����ɵķ������
		super.onRestart();
		asyncTask = new NoteBookAsyncTask();
		asyncTask.execute("note_book");
	}
	

	private void initEvent() {
		mListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (mArcMenu.isOpen())
					mArcMenu.toggleMenu(600);
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				NoteBook noteBook = (NoteBook) mListView
						.getItemAtPosition(position);
				Intent intent = new Intent(NoteBookActivity.this,
						NoteBookCatalogActivity.class);
				Bundle extras = new Bundle();
				extras.putInt(NoteBook.NID, noteBook.getNid());
				extras.putString(NoteBook.BOOK_NAME, noteBook.getBook_name());
				intent.putExtras(extras);
				startActivity(intent);
//				createNoteBook();

			}
		});

		// ����item�͵����Ի�������ɾ��
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				NoteBook noteBook = (NoteBook) mListView.getItemAtPosition(position);
				dialog(noteBook.getNid());
				return true;
			}
		});
		
		mArcMenu.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			@Override
			public void onClick(View view, int pos)
			{
				Toast.makeText(NoteBookActivity.this, pos+":"+view.getTag(), Toast.LENGTH_SHORT).show();
				if(pos == 1)
				{
					// ����
					Intent intent = new Intent(NoteBookActivity.this,AddRecord.class);
					intent.putExtra("action", 1);
					startActivity(intent);
				} 
				else if(pos == 2)
				{
					// ����
					Intent intent = new Intent(NoteBookActivity.this,AddRecord.class);
					intent.putExtra("action", 2);
					startActivity(intent);
				}
				else if(pos == 3)
				{
					// ¼��
					Intent intent = new Intent(NoteBookActivity.this,AddRecord.class);
					intent.putExtra("action", 3);
					startActivity(intent);
				}
				else if(pos == 4)
				{
					// ����
					Intent intent = new Intent(NoteBookActivity.this,AddRecord.class);
					intent.putExtra("action", 4);
					startActivity(intent);
				}
				else
				{
					// ��ӱʼǱ�
					createNoteBook();
				}
			}
		});
		
	}
	
	protected void createNoteBook() {
		AlertDialog.Builder builder = new Builder(NoteBookActivity.this);
		builder.setTitle("�½��ʼǱ�");
		final EditText myView = new EditText(this);
		builder.setView(myView);
		builder.setPositiveButton("ȷ��",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						NoteBookDao dao = new NoteBookDao(NoteBookActivity.this,
								"MyRecord.db", 1);
						String name = myView.getText().toString();
						NoteBook noteBook = new NoteBook(name);
						dao.save(noteBook);
						dao.close();
						onRestart();
					}
				});
		builder.setNegativeButton("ȡ��",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	protected void dialog(final int nid) {
		AlertDialog.Builder builder = new Builder(NoteBookActivity.this);
		builder.setMessage("ȷ��Ҫɾ����?");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						NoteBookDao dao = new NoteBookDao(NoteBookActivity.this,
								"MyRecord.db", 1);
						dao.delete(nid);
						onRestart();
					}
				});
		builder.setNegativeButton("ȡ��",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.note_book, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * ʵ�������ݿ���첽����
	 * 
	 * @author Administrator
	 * 
	 */
	class NoteBookAsyncTask extends AsyncTask<String, Void, List<NoteBook>> {

		@Override
		protected List<NoteBook> doInBackground(String... params) {
			NoteBookDao dao = new NoteBookDao(NoteBookActivity.this,
					"MyRecord.db", 1);
			// ���ձ��������������ݣ�����list����
			List<NoteBook> list = dao.getAllNoteBook();
			dao.close();
			return list;
		}

		@Override
		protected void onPostExecute(List<NoteBook> result) {
			// TODO �Զ����ɵķ������
			super.onPostExecute(result);
			NoteBookListAdapter adapter = new NoteBookListAdapter(
					NoteBookActivity.this, result);
			mListView.setAdapter(adapter);
		}

	}

	// ��߲˵��ĸ��������¼�

	public void getAllRecord(View view) {
		Intent intent = new Intent(NoteBookActivity.this, MainActivity.class);
		startActivity(intent);
	}

	public void getAllNoteBook(View view) {

		onRestart();
	}

	public void getAllRecovery(View view) {
		Intent intent = new Intent(NoteBookActivity.this,
				RecoveryActivity.class);
		startActivity(intent);
	}

	public void uploadToCloud(View view) {
		Intent intent = new Intent(NoteBookActivity.this, CloudActivity.class);
		startActivity(intent);
	}

	public void getAllTheme(View view) {
		Intent intent = new Intent(NoteBookActivity.this, ThemeActivity.class);
		startActivity(intent);

	}

}
