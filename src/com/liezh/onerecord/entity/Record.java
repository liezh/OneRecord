package com.liezh.onerecord.entity;

public class Record {

	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String CREATE_DATE = "create_date";
	public static final String STAR_DATE = "star_date";
	public static final String CATEGORY = "Category";
	public static final String STATE = "state";
	public static final String TABLE_NAME = "record";

	public Record() {
	}

	public Record(String title, String content, String create_date,
			String star_date, int category, int state) {
		this.title = title;
		this.content = content;
		this.create_date = create_date;
		this.star_date = star_date;
		Category = category;
		this.state = state;
	}

	private int id;
	private String title;
	private String content;
	private String create_date;
	private String star_date;
	private int Category;
	private int state;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getStar_date() {
		return star_date;
	}

	public void setStar_date(String star_date) {
		this.star_date = star_date;
	}

	public int getCategory() {
		return Category;
	}

	public void setCategory(int category) {
		Category = category;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Record [id=" + id + ", title=" + title + ", content=" + content
				+ ", create_date=" + create_date + ", star_date=" + star_date
				+ ", Category=" + Category + ", state=" + state + "]";
	}

}
