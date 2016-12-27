package com.liezh.onerecord.tool;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.liezh.onerecord.R;
import com.liezh.onerecord.entity.NoteBook;

public class NoteBookListAdapter extends BaseAdapter {

	private List<NoteBook> mList;
	private LayoutInflater mInflater;

	public NoteBookListAdapter(Context context, List<NoteBook> data) {
		mList = data;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.notebook_item, null);
			viewHolder.tvBookName = (TextView) convertView.findViewById(R.id.tv_nb_name);
			viewHolder.tvCount = (TextView) convertView.findViewById(R.id.tv_nb_count);
			NoteBook NoteBook = mList.get(position);
			
			viewHolder.tvBookName.setText(NoteBook.getBook_name());
			viewHolder.tvCount.setText("记录数--"+String.valueOf(NoteBook.getCount()));
			convertView.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		return convertView;
	}

	public class ViewHolder {
		public TextView tvBookName;
		public TextView tvCount;
	}


}
