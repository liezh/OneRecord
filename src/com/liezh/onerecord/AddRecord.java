package com.liezh.onerecord;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.liezh.onerecord.dao.NoteBookDao;
import com.liezh.onerecord.dao.RecordDao;
import com.liezh.onerecord.entity.NoteBook;
import com.liezh.onerecord.entity.Record;
import com.liezh.onerecord.tool.BasiceTool;
import com.liezh.onerecord.tool.MyOpenHelper;

@SuppressLint("NewApi")
public class AddRecord extends Activity {

	private static final int RES_CAMERA = 1;
	private static final int RES_PICTURE = 2;

	// 添加页面的控件
	private EditText et_title, et_content;
	private TextView tv_class, tv_date, tv_top;
	private String myPath = null;
	private String dp_date;
	private String tp_time;
	private int categ;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_activity);
		initAllView();
		// initData();
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		int action = bundle.getInt("action");
		initAction(action);

	}

	private void initAction(int action) {
		switch (action) {
		case 1:

			break;
		case 2:
			startCamera(new View(AddRecord.this));
			break;
		case 3:
			// startCamera(new View(AddRecord.this));
			break;

		default:
			break;
		}

	}

	private void initData() {
		Record record = new Record("my second record title",
				"my second record content", "2014-12-23", "2014-12-24", 2, 2);
		RecordDao dao = new RecordDao(AddRecord.this, "MyRecord.db", 1);
		dao.save(record);
		dao.close();
		System.out.println("初始数据成功！！！");
	}

	private void initAllView() {
		et_title = (EditText) findViewById(R.id.et_title);
		et_content = (EditText) findViewById(R.id.et_content);
		tv_class = (TextView) findViewById(R.id.tv_class);
		tv_date = (TextView) findViewById(R.id.tv_date);
		tv_top = (TextView) findViewById(R.id.tv_top);
		
		et_content.setSingleLine(false);
		et_content.setHorizontallyScrolling(false);
	}

	// 调用系统相册
	public void startAlbume(View view) {
		Intent picture = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(picture, RES_PICTURE);
	}

	// 调用系统相机
	public void startCamera(View view) {

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		myPath = BasiceTool.getSDPath() + File.separator + UUID.randomUUID()
				+ ".jpg";
		// System.out.println(myPath);
		Uri photoUri = Uri.fromFile(new File(myPath));

		intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
		startActivityForResult(intent, RES_CAMERA);
	}

	/**
	 * 调用系统应用后就判断是那个监听发出的，并执行相应逻辑
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// System.out.println("返回图片啦" + requestCode);

		if (resultCode == RESULT_OK) {
			if (requestCode == RES_CAMERA) {
				// 封装图片路径为 html <img> 标签
				// myContent = myContent + BasiceTool.imgTagBuilder(myPath);
				et_content.append(BasiceTool.setPhotoToHtml(BasiceTool
						.imgTagBuilder(myPath)));
			} else if (requestCode == RES_PICTURE) {
				Uri uri = data.getData();
				String[] proj = { MediaStore.Images.Media.DATA };
				Cursor actualimagecursor = managedQuery(uri, proj, null, null,
						null);
				int actual_image_column_index = actualimagecursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				actualimagecursor.moveToFirst();
				String img_path = actualimagecursor
						.getString(actual_image_column_index);
				// System.out.println("图片真实路径：" + img_path);
				et_content.append(BasiceTool.setPhotoToHtml(BasiceTool
						.imgTagBuilder(img_path)));

			}
		}

	}

	// 从添加页面中保存，所有的信息到数据库
	public void save(View view) {
		Editable editable = et_content.getText();
		System.out.println("Editable----" + editable + "--------"
				+ editable.length() + "+++++++" + editable.toString() + "\n"
				+ Html.toHtml(editable));
		Record record = new Record();
		record.setTitle(et_title.getText().toString());
		System.out.println("html--" + Html.toHtml(editable).toString());
		record.setContent(Html.toHtml(editable).toString());
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		record.setCreate_date(df.format(date));
		if(dp_date == null)
			dp_date = df.format(date);
		if(tp_time == null)
			tp_time = "";
		record.setStar_date(dp_date + " " + tp_time);
		record.setState(1);
		record.setCategory(categ);

		RecordDao dao = new RecordDao(AddRecord.this, "MyRecord.db", 1);
		dao.save(record);
		dao.close();
		// System.out.println("date-----"+df.format(date));
	}

	public void dialogDateAndTime(View view) {
		AlertDialog.Builder builder = new Builder(AddRecord.this);
		LayoutInflater factory = LayoutInflater.from(AddRecord.this);
		final View myView = factory.inflate(R.layout.date_choice, null);
		builder.setView(myView);
		builder.setTitle("选择提醒日期和时间！");
		builder.setPositiveButton("确定",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						DatePicker datePicker;
						TimePicker timePicker;
						
						datePicker = (DatePicker) myView.findViewById(R.id.dp_choice);
						timePicker = (TimePicker) myView.findViewById(R.id.tp_choice);
						int year = datePicker.getYear();
						int month = datePicker.getMonth() + 1;
						int day = datePicker.getDayOfMonth();
						dp_date = year + "-" + month + "-" + day;
						tv_date.setText(dp_date);
						int hour = timePicker.getCurrentHour();
						int min = timePicker.getCurrentMinute();
						tp_time = hour + ":" + min;
						tv_date.append(" " + tp_time);
						dialog.dismiss();
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (et_content.getText().toString().equals("")
					&& et_title.getText().toString().equals("")) {
				Toast.makeText(AddRecord.this, "记录为空，不与保存！", Toast.LENGTH_LONG)
						.show();
				AddRecord.this.finish();
			} else {
				dialog();
			}
		}
		return true;
	}

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(AddRecord.this);
		builder.setMessage("确定要保存吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						save(tv_top);
						AddRecord.this.finish();
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
	
	@SuppressWarnings("null")
	public void dialogClass(View view) {
		AlertDialog.Builder builder = new Builder(AddRecord.this);
		builder.setTitle("选择笔记本");
		NoteBookDao dao = new NoteBookDao(AddRecord.this, "MyRecord.db", 1);
		final List<NoteBook> list = dao.getAllNoteBook();
		
		String[] items = new String[list.size()];
		for(int i=0 ; i<list.size() ; i++){
			System.out.println("list---"+ i +"---"+ list.get(i).getBook_name().toString());
			items[i] = list.get(i).getBook_name();
		}
		builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println(list.get(which).getNid());
				tv_class.setText(list.get(which).getBook_name());
				categ = list.get(which).getNid();
				dialog.dismiss();
			}
			
		});
		builder.create().show();
		dao.close();
	}
	
	

}
