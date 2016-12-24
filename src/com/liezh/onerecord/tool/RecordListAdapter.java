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
import com.liezh.onerecord.entity.Record;

public class RecordListAdapter extends BaseAdapter {

	private List<Record> mList;
	private LayoutInflater mInflater;

	
	

	public RecordListAdapter(Context context, List<Record> data) {
		mList = data;
		mInflater = LayoutInflater.from(context);
		System.out.println("000000000000000");
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.activity_synopsis, null);
			viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
			viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
			Record record = mList.get(position);
			
			viewHolder.tvTitle.setText(record.getTitle());
			viewHolder.tvDate.setText(record.getCreate_date());
//			System.out.println("befor--" + record.getContent());
			String content = BasiceTool.getContentFromHtml(record.getContent());
//			System.out.println("after--" + content);
			viewHolder.tvContent.setText(content);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		return convertView;
	}

	public class ViewHolder {
		public TextView tvTitle, tvContent, tvDate;
	}

	
	

}
