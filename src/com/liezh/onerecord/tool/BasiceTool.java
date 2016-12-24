package com.liezh.onerecord.tool;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.util.Log;

public class BasiceTool {

	/**
	 * ��ȡ�ֻ���SD���ĸ�·��������
	 * 
	 * @return
	 */
	public static String getSDPath() {
		String sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory().getPath();// ��ȡ��Ŀ¼
		}
		return sdDir;
	}

	public static final String IMG_HEAD = "<img src='";
	public static final String IMG_TAIL = "'/>";
	private static final String NEWLINE = "\n";

	public static Spanned setPhotoToHtml(String path) {
		ImageGetter imageGetter = new ImageGetter() {

			@Override
			public Drawable getDrawable(String source) {
				Drawable drawable = Drawable.createFromPath(source);
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight());
				return drawable;
			}
		};
//		System.out.println("path------" + path);
		return Html.fromHtml(path, imageGetter, null);
	}

	public static String imgTagBuilder(String path) {
		return BasiceTool.IMG_HEAD + path + BasiceTool.IMG_TAIL + NEWLINE;
	}

	public static void saveBitmapToFile(Bitmap bitmap, String _file)
			throws IOException {
		BufferedOutputStream os = null;
		try {
			File file = new File(_file);
			// String _filePath_file.replace(File.separatorChar +
			// file.getName(), "");
			int end = _file.lastIndexOf(File.separator);
			String _filePath = _file.substring(0, end);
			File filePath = new File(_filePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			file.createNewFile();
			os = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					Log.e("ͼƬ����", e.getMessage(), e);
				}
			}
		}
	}

	public Bitmap getBitmapByIntent(Intent data) {
		// Bundle bundle = data.getExtras();
		// Bitmap bitmap = (Bitmap) bundle.get("data");
		// FileInputStream fis = null;
		// try {
		// fis = new FileInputStream(myPath);
		// } catch (FileNotFoundException e) {
		// // TODO �Զ����ɵ� catch ��
		// e.printStackTrace();
		// }
		// Bitmap bitmap = BitmapFactory.decodeStream(fis);
		return null;
	}

	private String regMatchEnter = "\\s*|\t|\r|\n";
	private String regMatchTag = "<[^>]*>";

	/**
	 * ��������ʽ���html�ı�ǩ
	 * 
	 * @param HTMLSource
	 */
	public void aa(String HTMLSource) {
		// Pattern p = Pattern.compile(regMatchEnter);
		// Matcher m = p.matcher(HTMLSource);
		// HTMLSource=m.replaceAll("");

		Pattern p = Pattern.compile(regMatchTag);
		Matcher m = p.matcher(HTMLSource);
		HTMLSource = m.replaceAll("");
	}

	/**
	 * ��ȡhtml�ַ����е�һ��ͼƬ��·��
	 * 
	 * @param htmlcontent
	 * @return
	 */
	public static String getImgFromHtml(String htmlcontent) {
		if (htmlcontent != null) {
			String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
			Pattern p_image = Pattern.compile(regEx_img,
					Pattern.CASE_INSENSITIVE);
			Matcher m_image = p_image.matcher(htmlcontent);
			if (m_image.find()) {
				String img = m_image.group(0);
				Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)")
						.matcher(img);
				if (m.find()) {
					if (m.group(0) != null) {
						return m.group(0).substring(5, m.group(0).length() - 1);
					}
				}
			}
		}
		return "";
	}

	/**
	 * ��ȡhtml�ַ����е��������ݣ�ȥ����ǩ��
	 * 
	 * @param htmlcontent
	 * @return
	 */
	public static String getContentFromHtml(String htmlcontent) {
		if (htmlcontent != null) {
			return htmlcontent.replaceAll("<\\/?.+?>", "");
		}
		return "";
	}

}
