package com.liezh.onerecord.entity;

/**
 * 笔记本的实体类
 * 
 * @author Administrator
 * 
 */
public class NoteBook {

	public static final String NID = "nid";
	public static final String BOOK_NAME = "book_name";
	public static final String TABLE_NAME = "note_book";

	private int nid;
	private String book_name;
	private int count;

	public NoteBook() {
	}

	public NoteBook(String book_name) {
		this.book_name = book_name;
	}
	
	public NoteBook(String book_name, int count) {
		this.book_name = book_name;
		this.count = count;
	}

	public int getNid() {
		return nid;
	}

	public void setNid(int nid) {
		this.nid = nid;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	
}
