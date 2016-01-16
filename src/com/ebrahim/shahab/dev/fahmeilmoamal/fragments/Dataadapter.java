package com.ebrahim.shahab.dev.fahmeilmoamal.fragments;

import java.util.ArrayList;



import com.ebrahimhaq.dev.fahmeilmoamal.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Dataadapter extends BaseAdapter {
	ArrayList<datamodel> data;
	Context context;
	public Dataadapter(ArrayList<datamodel> data, Context context ) {
		// TODO Auto-generated constructor stub
		this.data = new ArrayList<datamodel>();
		this.data=data;
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public datamodel getItem(int arg0) {
		// TODO Auto-generated method stub
		if (data != null) {
			return data.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		datamodel data1 = getItem(position);
		LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
		convertView = inflater.inflate(R.layout.list_item, null);
		TextView text = (TextView) convertView.findViewById(R.id.filename);
		text.setText(data.get(position).getFilename());
		
		return convertView;
	}

}
