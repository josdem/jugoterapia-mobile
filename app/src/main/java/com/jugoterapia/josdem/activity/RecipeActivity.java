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
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jugoterapia.josdem.R;
import com.jugoterapia.josdem.model.Beverage;
import com.jugoterapia.josdem.service.impl.JugoterapiaServiceImpl;
import com.jugoterapia.josdem.util.ActivityComponentFactory;
import com.jugoterapia.josdem.util.BeverageSplitter;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @understands It shows beverage data such as title, ingredients and recipe
 */

public class RecipeActivity extends Activity {

  @Inject
  JugoterapiaServiceImpl jugoterapiaService;

  private Beverage beverage;

  private void displayResults(Beverage beverage) {
    TextView name = (TextView) findViewById(R.id.name);
    name.setText(beverage.getName());
    TextView ingredients = (TextView) findViewById(R.id.ingredients);

    ingredients.setText(BeverageSplitter.split(beverage.getIngredients()));

    TextView recipeText = (TextView) findViewById(R.id.recipe);
    recipeText.setText(beverage.getRecipe());
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe);
    ActivityComponentFactory.getActivityComponent(this).inject(this);

    Integer beverageId = this.getIntent().getExtras().getInt("currentBeverage");
    Call<Beverage> call = jugoterapiaService.getBeverage(beverageId);
    call.enqueue(new retrofit2.Callback<Beverage>() {

      @Override
      public void onResponse(Call<Beverage> call, Response<Beverage> response) {
        displayResults(response.body());
      }

      @Override
      public void onFailure(Call<Beverage> call, Throwable t) {
        Log.d("error", t.getMessage());
      }
    });

  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
    savedInstanceState.putInt("currentBeverage", this.getIntent().getExtras().getInt("currentBeverage"));
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    this.getIntent().putExtra("currentCategory", savedInstanceState.getInt("currentBeverage"));
  }

}
