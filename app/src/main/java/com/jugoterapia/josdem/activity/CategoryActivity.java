/*
  Copyright 2014 José Luis De la Cruz Morales <joseluis.delacruz@gmail.com>

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

import java.util.List;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.jugoterapia.josdem.JugoterapiaApplication;
import com.jugoterapia.josdem.R;
import com.jugoterapia.josdem.adapter.CategoryAdapter;
import com.jugoterapia.josdem.component.ActivityComponent;
import com.jugoterapia.josdem.component.DaggerActivityComponent;
import com.jugoterapia.josdem.model.Category;
import com.jugoterapia.josdem.module.ActivityModule;
import com.jugoterapia.josdem.service.JugoterapiaService;
import com.jugoterapia.josdem.state.ApplicationState;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @understands It shows juice categories
 */

public class CategoryActivity extends Activity {

  private static final String ARGS_URI = "com.jugoterapia.android.activity.ARGS_URI";
  private static final String ARGS_PARAMS = "com.jugoterapia.android.activity.ARGS_PARAMS";
  private static final int LOADER_ID = 0x1;

  CategoryAdapter adapter;

  private ActivityComponent activityComponent;

  public ActivityComponent getActivityComponent() {
    if (activityComponent == null) {
      activityComponent = DaggerActivityComponent.builder()
              .activityModule(new ActivityModule(this))
              .applicationComponent(JugoterapiaApplication.get(this).getComponent())
              .build();
    }
    return activityComponent;
  }

  private void displayResults(List<Category> categories) {
    CategoryAdapter adapter = new CategoryAdapter(this, R.layout.list_category);

    ListView listView = (ListView) findViewById(R.id.listViewCategories);
    listView.setAdapter(adapter);

    adapter.clear();
    for (Category category : categories) {
      adapter.add(category);
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivityComponent().inject(this);

    setContentView(R.layout.activity_category);
    Uri beverageUri = Uri.parse(ApplicationState.CATEGORIES_URL);
    Bundle args = new Bundle();
    args.putParcelable(ARGS_URI, beverageUri);
    args.putParcelable(ARGS_PARAMS, new Bundle());

    JugoterapiaService jugoterapiaService = JugoterapiaService.retrofit.create(JugoterapiaService.class);
    Call<List<Category>> call = jugoterapiaService.getCategories();
    call.enqueue(new retrofit2.Callback<List<Category>>() {

      @Override
      public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
        displayResults(response.body());
      }

      @Override
      public void onFailure(Call<List<Category>> call, Throwable t) {
        Log.d("error", t.getMessage());
      }
    });
  }

}
