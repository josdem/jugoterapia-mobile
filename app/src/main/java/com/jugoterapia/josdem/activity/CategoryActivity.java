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

package com.jugoterapia.josdem.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jugoterapia.josdem.R;
import com.jugoterapia.josdem.adapter.CategoryAdapter;
import com.jugoterapia.josdem.component.ActivityComponent;
import com.jugoterapia.josdem.model.Category;
import com.jugoterapia.josdem.service.impl.JugoterapiaServiceImpl;
import com.jugoterapia.josdem.util.ActivityComponentFactory;
import com.jugoterapia.josdem.util.ConnectionDialog;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @understands It shows juice categories
 */

public class CategoryActivity extends Activity {

  @Inject
  JugoterapiaServiceImpl jugoterapiaService;

  private Integer clickCounter = 0;
  private CategoryAdapter adapter;
  private ActivityComponent activityComponent;


  private void listViewClicked(AdapterView<?> parent, View view, int position, long id) {
    Category selectedCategory = (Category) parent.getAdapter().getItem(position);
    Intent intent = new Intent(this, BeverageActivity.class);
    intent.putExtra("currentCategory", selectedCategory.getId());
    startActivity(intent);
  }

  private void displayResults(List<Category> categories) {
    CategoryAdapter adapter = new CategoryAdapter(this, R.layout.list_category);

    ListView listView = (ListView) findViewById(R.id.listViewCategories);
    listView.setAdapter(adapter);

    adapter.clear();
    for (Category category : categories) {
      adapter.add(category);
    }

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listViewClicked(parent, view, position, id);
      }
    });
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityComponentFactory.getActivityComponent(activityComponent, this).inject(this);

    setContentView(R.layout.activity_category);

    String language = getString(R.string.language);

    Call<List<Category>> call = jugoterapiaService.getCategories(language);
    call.enqueue(new retrofit2.Callback<List<Category>>() {

      @Override
      public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
        displayResults(response.body());
      }

      @Override
      public void onFailure(Call<List<Category>> call, Throwable t) {
        ConnectionDialog.show(CategoryActivity.this);
        Log.d("error", t.getMessage());
      }

    });

    final int abTitleId = getResources().getIdentifier("action_bar", "id", "android");
    findViewById(abTitleId).setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        clickCounter++;
        if(clickCounter >= 5){
          Log.d("Easter Egg:","On Action Bar");
          new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle)
                  .setMessage(R.string.dialogMessage)
                  .setPositiveButton(R.string.dialogButton, null)
                  .show();
          clickCounter=0;
        }
      }
    });

  }

  private Activity getActivity(){
    return CategoryActivity.this;
  }

}
