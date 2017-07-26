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
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.jugoterapia.josdem.R;
import com.jugoterapia.josdem.model.Beverage;
import com.jugoterapia.josdem.state.ApplicationState;

import java.util.List;

/**
 * @understands It shows all beverages title based in category
 */

public class BeverageActivity extends Activity {

  private static final String ARGS_URI = "com.jugoterapia.android.activity.ARGS_URI";
  private static final String ARGS_PARAMS = "com.jugoterapia.android.activity.ARGS_PARAMS";
  private static final int LOADER_ID = 0x1;

  private ArrayAdapter<Beverage> adapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_beverage);

    adapter = new ArrayAdapter<Beverage>(this, R.layout.list_beverage);

    Uri beverageUri = Uri.parse(ApplicationState.BEVERAGES_URL);

    Bundle params = new Bundle();
    params.putInt("categoryId", this.getIntent().getExtras().getInt("currentCategory"));
    Bundle args = new Bundle();
    args.putParcelable(ARGS_URI, beverageUri);
    args.putParcelable(ARGS_PARAMS, params);

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
