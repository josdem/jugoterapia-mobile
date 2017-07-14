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

import java.util.StringTokenizer;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jugoterapia.josdem.R;
import com.jugoterapia.josdem.loader.RestLoader;
import com.jugoterapia.josdem.loader.RestResponse;
import com.jugoterapia.josdem.model.Beverage;
import com.jugoterapia.josdem.state.ApplicationState;
import com.jugoterapia.josdem.util.BeverageSplitter;

/**
 * @understands It shows beverage data such as title, ingredients and recipe
 */

public class RecipeActivity extends FragmentActivity implements LoaderCallbacks<RestResponse> {

  private static final String ARGS_URI = "com.jugoterapia.android.activity.ARGS_URI";
  private static final String ARGS_PARAMS = "com.jugoterapia.android.activity.ARGS_PARAMS";
  private static final int LOADER_ID = 0x1;

  private Beverage beverage;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe);

    FragmentManager fm = getSupportFragmentManager();
    ListFragment list = (ListFragment) fm.findFragmentById(R.id.frameLayout);
    if (list == null){
      list = new ListFragment();
      FragmentTransaction ft = fm.beginTransaction();
      ft.add(R.id.frameLayout, list);
      ft.commit();
    }

    Uri recipeUri = Uri.parse(ApplicationState.RECIPE_URL);

    Bundle params = new Bundle();
    params.putInt("beverageId", this.getIntent().getExtras().getInt("currentBeverage"));
    Bundle args = new Bundle();
    args.putParcelable(ARGS_URI, recipeUri);
    args.putParcelable(ARGS_PARAMS, params);

    getSupportLoaderManager().initLoader(LOADER_ID, args, this);
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

  @Override
  public Loader<RestResponse> onCreateLoader(int id, Bundle args) {
    Uri action = args.getParcelable(ARGS_URI);
    Bundle params = args.getParcelable(ARGS_PARAMS);
    return new RestLoader(this, action, params);
  }

  @Override
  public void onLoadFinished(Loader<RestResponse> loader, RestResponse data) {
    int code = data.getCode();
    String json = data.getData();

    if (code == 200 && !json.isEmpty()) {
      try {
        beverage = new Gson().fromJson(json, Beverage.class);

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(beverage.getName());
        TextView ingredients = (TextView) findViewById(R.id.ingredients);

        ingredients.setText(BeverageSplitter.split(beverage.getIngredients()));

        TextView recipeText = (TextView) findViewById(R.id.recipe);
        recipeText.setText(beverage.getRecipe());
        FrameLayout layout = (FrameLayout) findViewById(R.id.frameLayout);
        layout.setVisibility(View.GONE);
      } catch (JsonSyntaxException jse) {
        Log.i("exception: ", jse.toString());
        Toast.makeText(this, ApplicationState.PARSING_RECIPE_MESSAGE, Toast.LENGTH_SHORT).show();
      }
    } else {
      Toast.makeText(this, ApplicationState.CONNECTION_MESSAGE, Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onLoaderReset(Loader<RestResponse> arg0) {
  }

}
