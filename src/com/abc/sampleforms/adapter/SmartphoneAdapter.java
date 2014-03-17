package com.abc.sampleforms.adapter;

import com.abc.sampleforms.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SmartphoneAdapter extends BaseAdapter {

	private String[] brands;
	private int[] imgResources;
	private int length;
	private LayoutInflater inflater;
	private Context context;

	public SmartphoneAdapter(Context context, String[] OS, int[] imgResID) {
		this.context = context;
		brands = OS;
		imgResources = imgResID;
		length = brands.length;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		ViewHolder holder = new ViewHolder();
		if (view == null) {
			view = inflater.inflate(R.layout.compound_spinner_item, null);
			holder.imageView = (ImageView) view
					.findViewById(R.id.imageView_Logo);
			holder.textView = (TextView) view.findViewById(R.id.textView_OS);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		Log.e("Smartphone Adapter", "position : " + position);
		Picasso.with(context).load(imgResources[position])
				.into(holder.imageView);
		holder.textView.setText(brands[position]);
		return view;
	}

	class ViewHolder {
		ImageView imageView;
		TextView textView;
	}

}
