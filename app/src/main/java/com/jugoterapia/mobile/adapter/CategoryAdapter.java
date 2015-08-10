package com.jugoterapia.mobile.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jugoterapia.mobile.R;
import com.jugoterapia.mobile.model.Category;

public class CategoryAdapter extends ArrayAdapter<Category> {

	private List<Integer> images = new ArrayList<Integer>();
	private Context context;

	public CategoryAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.context = context;
		images.add(R.drawable.curativos);
		images.add(R.drawable.energetizantes);
		images.add(R.drawable.saludables);
		images.add(R.drawable.estimulantes);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_category, parent, false);
		
		TextView textView = (TextView) rowView.findViewById(R.id.categoryTextView);
		textView.setCompoundDrawablesWithIntrinsicBounds(images.get(position), 0, 0, 0);
		textView.setText(this.getItem(position).toString());
		
		return rowView;
	}


}
