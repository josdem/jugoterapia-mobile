/*
  Copyright 2017 José Luis De la Cruz Morales <joseluis.delacruz@gmail.com>

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
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jugoterapia.josdem.R;
import com.jugoterapia.josdem.component.ActivityComponent;
import com.jugoterapia.josdem.model.Beverage;
import com.jugoterapia.josdem.service.impl.JugoterapiaServiceImpl;
import com.jugoterapia.josdem.util.ActivityComponentFactory;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @understands It shows all beverages title based in category
 */

public class BeverageActivity extends Activity {

  @Inject
  JugoterapiaServiceImpl jugoterapiaService;

  private ArrayAdapter<Beverage> adapter;
  private ActivityComponent activityComponent;

  private void displayResults(List<Beverage> beverages) {
    ArrayAdapter adapter = new ArrayAdapter<Beverage>(this, R.layout.list_beverage);

    ListView listView = (ListView) findViewById(R.id.listViewBeverages);
    listView.setAdapter(adapter);

    adapter.clear();
    for (Beverage beverage : beverages) {
      adapter.add(beverage);
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
    setContentView(R.layout.activity_beverage);
    ActivityComponentFactory.getActivityComponent(activityComponent, this).inject(this);

    adapter = new ArrayAdapter<Beverage>(this, R.layout.list_beverage);

    Integer categoryId = this.getIntent().getExtras().getInt("currentCategory");
    Call<List<Beverage>> call = jugoterapiaService.getBeverages(categoryId);
    call.enqueue(new retrofit2.Callback<List<Beverage>>() {

      @Override
      public void onResponse(Call<List<Beverage>> call, Response<List<Beverage>> response) {
        displayResults(response.body());
      }

      @Override
      public void onFailure(Call<List<Beverage>> call, Throwable t) {
        Log.d("error", t.getMessage());
      }
    });

  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
    savedInstanceState.putInt("currentCategory", this.getIntent().getExtras().getInt("currentCategory"));
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    this.getIntent().putExtra("currentCategory", savedInstanceState.getInt("currentCategory"));
  }


  private void listViewClicked(AdapterView<?> parent, View view, int position, long id) {
    Beverage beverage = (Beverage) parent.getAdapter().getItem(position);
    Intent intent = new Intent(this, RecipeActivity.class);
    intent.putExtra("currentBeverage", beverage.getId());
    startActivity(intent);
  }

}
