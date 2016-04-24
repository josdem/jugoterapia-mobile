/*
  Copyright 2014
  José Luis De la Cruz Morales <joseluis.delacruz@gmail.com>
  José Juan Reyes Zuñiga <neodevelop@gmail.com>

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
import com.jugoterapia.josdem.loader.RESTLoader;
import com.jugoterapia.josdem.loader.RESTLoader.RESTResponse;
import com.jugoterapia.josdem.model.Beverage;
import com.jugoterapia.josdem.state.ApplicationState;

/**
 * @understands It shows beverage data such as title, ingredients and recipe
 */

public class RecipeActivity extends FragmentActivity implements LoaderCallbacks<RESTLoader.RESTResponse> {

	private static final String ARGS_URI    = "com.jugoterapia.android.activity.ARGS_URI";
    private static final String ARGS_PARAMS = "com.jugoterapia.android.activity.ARGS_PARAMS";
    private static final int LOADER_ID = 0x1;

    private Beverage beverage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe);

		FragmentManager fm = getSupportFragmentManager();

		ListFragment list =(ListFragment) fm.findFragmentById(R.id.frameLayout);
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
		this.getIntent().putExtra("currentCategory",savedInstanceState.getInt("currentBeverage"));
	}

	@Override
    public Loader<RESTLoader.RESTResponse> onCreateLoader(int id, Bundle args) {
        if (args != null && args.containsKey(ARGS_URI) && args.containsKey(ARGS_PARAMS)) {
            Uri    action = args.getParcelable(ARGS_URI);
            Bundle params = args.getParcelable(ARGS_PARAMS);
            return new RESTLoader(this, RESTLoader.HTTPVerb.POST, action, params);
        }
        return null;
    }

	@Override
    public void onLoadFinished(Loader<RESTLoader.RESTResponse> loader, RESTLoader.RESTResponse data) {
        int    code = data.getCode();
        String json = data.getData();

        if (code == 200 && !json.equals("")) {
        	try {
        		beverage = new Gson().fromJson(json, Beverage.class);

        		StringTokenizer stringTokenizer = new StringTokenizer(beverage.getIngredients(), "*");
        		StringBuilder stringBuilder = new StringBuilder();

        		while(stringTokenizer.hasMoreElements()){
        			stringBuilder.append("* ");
        			stringBuilder.append(stringTokenizer.nextElement());
        			stringBuilder.append("\n");
        		}
        		TextView name = (TextView) findViewById(R.id.name);
        	    name.setText(new String(beverage.getName()));
        		TextView ingredients = (TextView) findViewById(R.id.ingredients);

        	    ingredients.setText(new String(stringBuilder.toString()));

        		TextView recipeText = (TextView) findViewById(R.id.recipe);
        		recipeText.setText(new String(beverage.getRecipe()));

        		FrameLayout layout = (FrameLayout)findViewById(R.id.frameLayout);
                layout.setVisibility(View.GONE);
        	} catch (JsonSyntaxException jse){
            	Log.i("exception: ", jse.toString());
            	Toast.makeText(this, ApplicationState.CONNECTION_MESSAGE, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, ApplicationState.CONNECTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

	@Override
	public void onLoaderReset(Loader<RESTResponse> arg0) {
	}

}
