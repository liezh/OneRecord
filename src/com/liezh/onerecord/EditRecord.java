package com.liezh.onerecord;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.liezh.onerecord.dao.NoteBookDao;
import com.liezh.onerecord.dao.RecordDao;
import com.liezh.onerecord.entity.NoteBook;
import com.liezh.onerecord.entity.Record;
import com.liezh.onerecord.tool.BasiceTool;

public class EditRecord extends Activity {

	private Record mRecord;
	private static final int RES_CAMERA = 1;
	private static final int RES_PICTURE = 2;

	// 添加页面的控件
	private EditText et_title, et_content;
	private TextView tv_class, tv_date;
	private String myPath = null;
	private ImageView iv_edit,iv_picture,iv_camera;
	private String dp_date;
	private String tp_time;
	
	private int categ = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_activity);
		mRecord = new Record();
		initAllView();
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		initData(bundle);

	}

	private void initData(Bundle bundle) {
		System.out.println("ID---" + bundle.getInt(Record.ID));
		mRecord.setId(bundle.getInt(Record.ID));
		mRecord.setTitle(bundle.getString(Record.TITLE));
		mRecord.setContent(bundle.getString(Record.CONTENT));
		mRecord.setCreate_date(bundle.getString(Record.CREATE_DATE));
		mRecord.setStar_date(bundle.getString(Record.STAR_DATE));
		mRecord.setCategory(bundle.getInt(Record.CATEGORY));
		mRecord.setState(bundle.getInt(Record.STATE));

		et_title.setText(mRecord.getTitle());
		et_content.setText(BasiceTool.setPhotoToHtml(mRecord.getContent()));
		
		NoteBookDao dao = new NoteBookDao(EditRecord.this, "MyRecord.db", 1);
		String s = dao.getBookNameByCategory(mRecord.getCategory());
		dao.close();
		tv_class.setText(s);
		tv_date.setText(mRecord.getStar_date());

	}

	private void initAllView() {
		et_title = (EditText) findViewById(R.id.edit_et_title);
		et_content = (EditText) findViewById(R.id.edit_et_content);
		tv_class = (TextView) findViewById(R.id.edit_tv_class);
		tv_date = (TextView) findViewById(R.id.edit_tv_date);
		iv_edit = (ImageView) findViewById(R.id.iv_edit);
		iv_picture = (ImageView) findViewById(R.id.edit_iv_picture);
		iv_camera = (ImageView) findViewById(R.id.edit_iv_camera);
		et_content.setSingleLine(false);
		et_content.setHorizontallyScrolling(false);
		et_title.setEnabled(false);
		et_content.setEnabled(false);
		tv_class.setClickable(false);
		tv_date.setClickable(false);
		iv_picture.setClickable(false);
		iv_camera.setClickable(false);
	}

	public void editButton(View view) {
		et_title.setEnabled(true);
		et_content.setEnabled(true);
		tv_class.setClickable(true);
		tv_date.setClickable(true);
		iv_picture.setClickable(true);
		iv_camera.setClickable(true);
		iv_edit.startAnimation(scaleBigAnim(300));
		
	}
	
	/**
	 * 单击了的子按钮将变大，并消失
	 * 
	 * @param duration
	 * @return
	 */
	private Animation scaleBigAnim(int duration)
	{
		AnimationSet animationSet = new AnimationSet(true);
		// 变大动画设置，变大4倍，持续时间为duration
		ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// 透明度动画，从有到无
		AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);

		animationSet.addAnimation(scaleAnim);
		animationSet.addAnimation(alphaAnim);

		animationSet.setDuration(duration);
		animationSet.setFillAfter(true);
		return animationSet;

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
	@SuppressLint("SimpleDateFormat")
	public void updata(View view) {
		Editable editable = et_content.getText();
		System.out.println("Editable----" + editable + "--------"
				+ editable.length() + "+++++++" + editable.toString() + "\n"
				+ Html.toHtml(editable));
		Record record = new Record();
		record.setId(mRecord.getId());
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
		RecordDao dao = new RecordDao(EditRecord.this, "MyRecord.db", 1);
		dao.update(record);
		dao.close();
		// System.out.println("date-----"+df.format(date));
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 ) {
			if (et_content.getText().toString().equals("")
					&& et_title.getText().toString().equals("")) {
				Toast.makeText(EditRecord.this, "记录为空，不与保存！", Toast.LENGTH_LONG)
						.show();
				EditRecord.this.finish();
			} else {
				dialog();
			}
		}
		return true;
	}
	
	protected void dialog() {
		AlertDialog.Builder builder = new Builder(EditRecord.this);
		builder.setMessage("确定要保存吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						updata(new View(EditRecord.this));
						EditRecord.this.finish();
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						EditRecord.this.finish();
					}
				});
		builder.create().show();
	}
	
	@SuppressWarnings("null")
	public void dialogClass(View view) {
		AlertDialog.Builder builder = new Builder(EditRecord.this);
		builder.setTitle("选择笔记本");
		NoteBookDao dao = new NoteBookDao(EditRecord.this, "MyRecord.db", 1);
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
	
	public void dialogDateAndTime(View view) {
		AlertDialog.Builder builder = new Builder(EditRecord.this);
		LayoutInflater factory = LayoutInflater.from(EditRecord.this);
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
}
