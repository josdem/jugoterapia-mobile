/*
  Copyright 2015 José Luis De la Cruz Morales <joseluis.delacruz@gmail.com>

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

package com.jugoterapia.josdem.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jugoterapia.josdem.R;
import com.jugoterapia.josdem.model.Category;

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
