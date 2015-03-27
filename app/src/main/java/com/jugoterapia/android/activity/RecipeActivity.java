package com.jugoterapia.android.activity;

import java.io.UnsupportedEncodingException;
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
import com.jugoterapia.android.R;
import com.jugoterapia.android.loader.RESTLoader;
import com.jugoterapia.android.loader.RESTLoader.RESTResponse;
import com.jugoterapia.android.model.Beverage;
import com.jugoterapia.android.state.ApplicationState;

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
        Log.i("beverageId: ", this.getIntent().getExtras().getInt("currentBeverage") + "");
        
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
        		try {
        			name.setText(new String(beverage.getName().getBytes("ISO-8859-1"),"UTF-8"));
        		} catch (UnsupportedEncodingException e) {
        			e.printStackTrace();
        		}
        		
        		TextView ingredients = (TextView) findViewById(R.id.ingredients);
        		try {
        			ingredients.setText(new String(stringBuilder.toString().getBytes("ISO-8859-1"),"UTF-8"));
        		} catch (UnsupportedEncodingException e) {
        			e.printStackTrace();
        		}
        		
        		TextView recipeText = (TextView) findViewById(R.id.recipe);
        		try {
        			recipeText.setText(new String(beverage.getRecipe().getBytes("ISO-8859-1"),"UTF-8"));
        		} catch (UnsupportedEncodingException e) {
        			e.printStackTrace();
        		}
        		FrameLayout layout = (FrameLayout)findViewById(R.id.frameLayout);
                layout.setVisibility(View.GONE);
        	} catch (JsonSyntaxException jse){
            	Log.i("exeption: ", jse.toString());
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
