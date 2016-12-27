package com.liezh.onerecord.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.liezh.onerecord.entity.Record;
import com.liezh.onerecord.tool.MyOpenHelper;

/**
 * 访问数据的Dao类，帮助实现增删改查
 * 
 * @author Administrator
 * 
 */
public class RecordDao {

	MyOpenHelper myHelper;
	SQLiteDatabase db;

	public RecordDao(Context context, String db_name, int db_vresion) {
		myHelper = new MyOpenHelper(context, db_name, null, db_vresion);
		db = myHelper.getWritableDatabase();
	}

	public void close() {
		db.close();
		myHelper.close();
	}

	/**
	 * 获取指定数据表所有的所有数据。
	 */
	public List<Record> getAllRecord() {
		List<Record> list = new ArrayList<Record>();
		Cursor cursor = db.query(Record.TABLE_NAME, null, null, null, null,
				null, Record.CREATE_DATE + " DESC");
		cursor.moveToFirst();

		if (cursor.getCount() != 0)
			for (int i = 0; i < cursor.getCount(); i++) {
				Record r = new Record();
				r.setId(Integer.valueOf(cursor.getString(0)));
				r.setTitle(cursor.getString(1));
				r.setContent(cursor.getString(2));
				r.setCreate_date(cursor.getString(3));
				r.setStar_date(cursor.getString(4));
				r.setCategory(Integer.valueOf(cursor.getString(5)));
				r.setState(Integer.valueOf(cursor.getString(6)));
				// Log.e("record", r.toString());
				list.add(r);
				cursor.moveToNext();
			}

		// System.out.println("dao count " + list.size() +
		// "cur "+cursor.getCount());
		return list;
	}

	public List<Record> getAllState1Record() {
		List<Record> list = new ArrayList<Record>();
		Cursor cursor = db.query(Record.TABLE_NAME, null, Record.STATE
				+ "=?", new String[] { String.valueOf(1) }, null,
				null, Record.CREATE_DATE + " DESC");
		cursor.moveToFirst();

		if (cursor.getCount() != 0)
			for (int i = 0; i < cursor.getCount(); i++) {
				Record r = new Record();
				r.setId(Integer.valueOf(cursor.getString(0)));
				r.setTitle(cursor.getString(1));
				r.setContent(cursor.getString(2));
				r.setCreate_date(cursor.getString(3));
				r.setStar_date(cursor.getString(4));
				r.setCategory(Integer.valueOf(cursor.getString(5)));
				r.setState(Integer.valueOf(cursor.getString(6)));
				// Log.e("record", r.toString());
				list.add(r);
				cursor.moveToNext();
			}

		// System.out.println("dao count " + list.size() +
		// "cur "+cursor.getCount());
		return list;
	}

	
	public List<Record> getAllRecordByNid(int nid) {
		List<Record> list = new ArrayList<Record>();
		Cursor cursor = db.query(Record.TABLE_NAME, null, Record.CATEGORY
				+ "=?", new String[] { String.valueOf(nid) }, null, null,
				Record.CREATE_DATE + " DESC");
		cursor.moveToFirst();
		if (cursor.getCount() != 0)
			for (int i = 0; i < cursor.getCount(); i++) {
				Record r = new Record();
				r.setId(Integer.valueOf(cursor.getString(0)));
				r.setTitle(cursor.getString(1));
				r.setContent(cursor.getString(2));
				r.setCreate_date(cursor.getString(3));
				r.setStar_date(cursor.getString(4));
				r.setCategory(Integer.valueOf(cursor.getString(5)));
				r.setState(Integer.valueOf(cursor.getString(6)));
				// Log.e("record", r.toString());
				list.add(r);
				cursor.moveToNext();
			}
		return list;
	}

	public int setStateTo0ByRid(int rid) {
		ContentValues values = new ContentValues();
		values.put(Record.STATE, 0);
		int id = db.update(Record.TABLE_NAME, values, Record.ID + "=?",
				new String[] { String.valueOf(rid) });
		return id;
	}
	
	public List<Record> getAllState1RecordByNid(int nid) {
		List<Record> list = new ArrayList<Record>();
		Cursor cursor = db.query(Record.TABLE_NAME, null, Record.STATE
				+ "=? AND " + Record.CATEGORY + "=?",
				new String[] { String.valueOf(1), String.valueOf(nid) }, null,
				null, Record.CREATE_DATE + " DESC");
		cursor.moveToFirst();
		if (cursor.getCount() != 0)
			for (int i = 0; i < cursor.getCount(); i++) {
				Record r = new Record();
				r.setId(Integer.valueOf(cursor.getString(0)));
				r.setTitle(cursor.getString(1));
				r.setContent(cursor.getString(2));
				r.setCreate_date(cursor.getString(3));
				r.setStar_date(cursor.getString(4));
				r.setCategory(Integer.valueOf(cursor.getString(5)));
				r.setState(Integer.valueOf(cursor.getString(6)));
				// Log.e("record", r.toString());
				list.add(r);
				cursor.moveToNext();
			}
		return list;
	}

	public List<Record> getAllStateNot1Record() {
		List<Record> list = new ArrayList<Record>();
		Cursor cursor = db.query(Record.TABLE_NAME, null,
				Record.STATE + "<>? ", new String[] { String.valueOf(1) },
				null, null, Record.CREATE_DATE + " DESC");
		cursor.moveToFirst();
		if (cursor.getCount() != 0)
			for (int i = 0; i < cursor.getCount(); i++) {
				Record r = new Record();
				r.setId(Integer.valueOf(cursor.getString(0)));
				r.setTitle(cursor.getString(1));
				r.setContent(cursor.getString(2));
				r.setCreate_date(cursor.getString(3));
				r.setStar_date(cursor.getString(4));
				r.setCategory(Integer.valueOf(cursor.getString(5)));
				r.setState(Integer.valueOf(cursor.getString(6)));
				// Log.e("record", r.toString());
				list.add(r);
				cursor.moveToNext();
			}
		return list;
	}

	public int getCountByCategory(int nid) {
		Cursor cursor = db.query(Record.TABLE_NAME, null, Record.CATEGORY
				+ "=?", new String[] { String.valueOf(nid) }, null, null,
				Record.CREATE_DATE + " DESC");
		return cursor.getCount();
	}

	public Long save(Record record) {
		ContentValues values = new ContentValues();
		values.put(Record.TITLE, record.getTitle());
		values.put(Record.CONTENT, record.getContent());
		values.put(Record.CREATE_DATE, record.getCreate_date());
		values.put(Record.STAR_DATE, record.getStar_date());
		values.put(Record.CATEGORY, record.getCategory());
		values.put(Record.STATE, record.getState());
		Long id = db.insert(Record.TABLE_NAME, null, values);
		Log.e("save", "record save!!!");
		return id;
	}

	public int update(Record record) {
		ContentValues values = new ContentValues();
		values.put(Record.TITLE, record.getTitle());
		values.put(Record.CONTENT, record.getContent());
		values.put(Record.CREATE_DATE, record.getCreate_date());
		values.put(Record.STAR_DATE, record.getStar_date());
		values.put(Record.CATEGORY, record.getCategory());
		values.put(Record.STATE, record.getState());
		int id = db.update(Record.TABLE_NAME, values, Record.ID + "=?",
				new String[] { String.valueOf(record.getId()) });
		return id;
	}

	public int delete(int rid) {
		int id = db.delete(Record.TABLE_NAME, Record.ID + "=?",
				new String[] { String.valueOf(rid) });
		return id;
	}

}
