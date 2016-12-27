package com.liezh.onerecord.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.liezh.onerecord.entity.NoteBook;
import com.liezh.onerecord.entity.Record;
import com.liezh.onerecord.tool.MyOpenHelper;



public class NoteBookDao {

	private MyOpenHelper myHelper;
	private SQLiteDatabase db;
	
	private Context context;
	private String db_name;
	private int db_vresion;
	
	public NoteBookDao(Context context, String db_name, int db_vresion) {
		myHelper = new MyOpenHelper(context, db_name, null, db_vresion);
		db = myHelper.getWritableDatabase();
		this.context = context;
		this.db_name = db_name;
		this.db_vresion = db_vresion;
	}

	public void close() {
		db.close();
		myHelper.close();
	}

	/**
	 * 获取指定数据表所有的所有数据。
	 */
	public List<NoteBook> getAllNoteBook() {
		List<NoteBook> list = new ArrayList<NoteBook>();
		Cursor cursor = db.query(NoteBook.TABLE_NAME, null, null, null, null,
				null, null);
		cursor.moveToFirst();
		RecordDao rDao = new RecordDao(context, db_name, db_vresion);
		if (cursor.getCount() != 0)
			for(int i = 0 ; i<cursor.getCount() ; i++)
			{
				NoteBook n = new NoteBook();
				Integer nid = Integer.valueOf(cursor.getString(0));
				n.setNid(nid);
				n.setBook_name(cursor.getString(1));
				
				
				int count = rDao.getCountByCategory(nid);
				n.setCount(count);
				// Log.e("NoteBook", r.toString());
				list.add(n);
				cursor.moveToNext();
			}
		rDao.close();
//		System.out.println("dao count " + list.size() + "cur "+cursor.getCount());
		return list;
	}
	
	public String getBookNameByCategory(int category) {
		Cursor cursor = db.query(NoteBook.TABLE_NAME, null, NoteBook.NID
				+ "=?", new String[] { String.valueOf(category) }, null, null, null);
		cursor.moveToFirst();
		return cursor.getString(1);
	}
	

	public Long save(NoteBook noteBook) {
		ContentValues values = new ContentValues();
		values.put(NoteBook.BOOK_NAME, noteBook.getBook_name());
		Long nid = db.insert(NoteBook.TABLE_NAME, null, values);
		Log.e("save", "NoteBook save!!!");
		return nid;
	}

	public int update(NoteBook noteBook) {
		ContentValues values = new ContentValues();
		values.put(NoteBook.BOOK_NAME, noteBook.getBook_name());
		int nid = db.update(NoteBook.TABLE_NAME, values,
				NoteBook.NID + "=" + noteBook.getNid(), null);
		return nid;
	}

	public int delete(int id) {
		int nid = db.delete(NoteBook.TABLE_NAME, NoteBook.NID + "=?",
				new String[] { String.valueOf(id) });
		return nid;
	}
}
