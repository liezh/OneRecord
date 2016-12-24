package com.liezh.onerecord;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.liezh.onerecord.dao.RecordDao;
import com.liezh.onerecord.entity.Record;
import com.liezh.onerecord.tool.BasiceTool;
import com.liezh.onerecord.tool.MyOpenHelper;

@SuppressLint("NewApi")
public class AddRecord extends Activity {

	MyOpenHelper myHelper;
	SQLiteDatabase db;
	Map<integer, String> myMap;
	private static final int RES_CAMERA = 1;
	private static final int RES_PICTURE = 2;

	// ���ҳ��Ŀؼ�
	private EditText et_title, et_content;
	private TextView tv_class, tv_date, tv_top;
	private String myPath = null;
	private String myContent = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_activity);
		myHelper = new MyOpenHelper(AddRecord.this, "MyRecord.db", null, 1);
		db = myHelper.getReadableDatabase();
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
//			startCamera(new View(AddRecord.this));
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
		System.out.println("��ʼ���ݳɹ�������");
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

	// ����ϵͳ���
	public void startAlbume(View view) {
		Intent picture = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(picture, RES_PICTURE);
	}

	// ����ϵͳ���
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
	 * ����ϵͳӦ�ú���ж����Ǹ����������ģ���ִ����Ӧ�߼�
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO �Զ����ɵķ������
		super.onActivityResult(requestCode, resultCode, data);
		// System.out.println("����ͼƬ��" + requestCode);

		if (resultCode == RESULT_OK) {
			if (requestCode == RES_CAMERA) {
				// ��װͼƬ·��Ϊ html <img> ��ǩ
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
				// System.out.println("ͼƬ��ʵ·����" + img_path);
				et_content.append(BasiceTool.setPhotoToHtml(BasiceTool
						.imgTagBuilder(img_path)));

			}
		}

	}

	// �����ҳ���б��棬���е���Ϣ�����ݿ�
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
		record.setStar_date(df.format(date));
		record.setState(9);
		record.setCategory(9);

		RecordDao dao = new RecordDao(AddRecord.this, "MyRecord.db", 1);
		dao.save(record);
		dao.close();
		// System.out.println("date-----"+df.format(date));
	}

	public void dialogDateAndTime(View view) {
		AlertDialog.Builder builder = new Builder(AddRecord.this);
		builder.setMessage("ѡ���������ں�ʱ�䣡");
		builder.setPositiveButton("ȷ��",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// tv_date.setText();
					}
				});
		builder.setNegativeButton("ȡ��",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		LayoutInflater factory = LayoutInflater.from(AddRecord.this);
		View myView = factory.inflate(R.layout.date_choice, null);
		builder.setView(myView).create().show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO �Զ����ɵķ������

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			System.out.println(et_content.getText().toString().equals("")
					+ "1111" + et_title.getText().toString().equals(""));
			System.out.println("22222"
					+ et_content.getText().toString().equals("")
					+ et_title.getText().toString().equals(""));
			if (et_content.getText().toString().equals("")
					&& et_title.getText().toString().equals("")) {
				Toast.makeText(AddRecord.this, "��¼Ϊ�գ����뱣�棡", Toast.LENGTH_LONG)
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
		builder.setMessage("ȷ��Ҫ������?");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						save(tv_top);
						AddRecord.this.finish();
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

}
