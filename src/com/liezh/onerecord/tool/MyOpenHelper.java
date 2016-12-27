package com.liezh.onerecord.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.liezh.onerecord.entity.NoteBook;
import com.liezh.onerecord.entity.Record;

public class MyOpenHelper extends SQLiteOpenHelper {

	// +"id integer primary key autoincrement,"
	// +"name,pwd");
	private Context myContext;

	public MyOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		myContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 自动生成的方法存根
		
		db.execSQL("create table if not exists "+ Record.TABLE_NAME + "("
				+ Record.ID +" integer primary key," 
				+ Record.TITLE +" varchar,"
				+ Record.CONTENT + " varchar,"
				+ Record.CREATE_DATE + " varchar,"
				+ Record.STAR_DATE + " varchar,"
				+ Record.CATEGORY +" int,"
				+ Record.STATE + " int)");
		db.execSQL("create table if not exists "+ NoteBook.TABLE_NAME + "("
				+ NoteBook.NID +" integer primary key," 
				+ NoteBook.BOOK_NAME + " varchar)");
		System.out.println("创建数据库完成！！");
		Log.e("DB","创建数据库完成！！");
//		Toast.makeText(myContext, "数据库创建成功！", Toast.LENGTH_LONG).show();
	}

	/**
	 * 当数据表升级时把原表删除
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists "+ Record.TABLE_NAME);
		onCreate(db);
		Log.e("DB","升级数据库完成！！");
	}

}
