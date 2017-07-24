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
import javax.inject.Inject;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.jugoterapia.josdem.JugoterapiaApplication;
import com.jugoterapia.josdem.R;
import com.jugoterapia.josdem.adapter.CategoryAdapter;
import com.jugoterapia.josdem.component.ActivityComponent;
import com.jugoterapia.josdem.component.DaggerActivityComponent;
import com.jugoterapia.josdem.loader.Callback;
import com.jugoterapia.josdem.loader.RetrofitLoader;
import com.jugoterapia.josdem.loader.RetrofitLoaderManager;
import com.jugoterapia.josdem.model.Category;
import com.jugoterapia.josdem.module.ActivityModule;
import com.jugoterapia.josdem.service.JugoterapiaService;
import com.jugoterapia.josdem.state.ApplicationState;

/**
 * @understands It shows juice categories
 */

public class CategoryActivity extends Activity implements Callback<List<Category>> {

  private static final String ARGS_URI = "com.jugoterapia.android.activity.ARGS_URI";
  private static final String ARGS_PARAMS = "com.jugoterapia.android.activity.ARGS_PARAMS";
  private static final int LOADER_ID = 0x1;

  CategoryAdapter adapter;

  @Inject
  JugoterapiaService jugoterapiaService;

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

  @Override
  public void onFailure(Exception ex) {
    Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
  }


  @Override
  public void onSuccess(List<Category> result) {
    Log.d("IssuesLoader", "onSuccess");
    displayResults(result);
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

    CategoryLoader loader = new CategoryLoader(this, jugoterapiaService);

    RetrofitLoaderManager.init(getLoaderManager(), 0, loader, this);
  }

  static class CategoryLoader extends RetrofitLoader<List<Category>, JugoterapiaService> {

    public CategoryLoader(Context context, JugoterapiaService service) {
      super(context, service);
    }

    @Override
    public List<Category> call(JugoterapiaService service) {
      Log.d("IssuesLoader", "Calling get categories");
      return service.getCategories();
    }
  }

}
