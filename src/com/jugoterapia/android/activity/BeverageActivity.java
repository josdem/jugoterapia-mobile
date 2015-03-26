package com.jugoterapia.android.activity;

import java.util.List;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jugoterapia.android.R;
import com.jugoterapia.android.bean.BeverageWrapper;
import com.jugoterapia.android.loader.RESTLoader;
import com.jugoterapia.android.loader.RESTLoader.RESTResponse;
import com.jugoterapia.android.model.Beverage;
import com.jugoterapia.android.state.ApplicationState;

/**
 * @understands It shows all beverages title based in category 
 */

public class BeverageActivity extends FragmentActivity implements LoaderCallbacks<RESTLoader.RESTResponse> {
	
	private static final String ARGS_URI    = "com.jugoterapia.android.activity.ARGS_URI";
    private static final String ARGS_PARAMS = "com.jugoterapia.android.activity.ARGS_PARAMS";
    private static final int LOADER_ID = 0x1;
    
    private ArrayAdapter<Beverage> mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beverage);
		
		FragmentManager fm = getSupportFragmentManager();
		
		ListFragment list =(ListFragment) fm.findFragmentById(R.id.frameLayout); 
        if (list == null){
        	list = new ListFragment();
        	FragmentTransaction ft = fm.beginTransaction();
        	ft.add(R.id.frameLayout, list);
        	ft.commit();
        }
        
        mAdapter = new ArrayAdapter<Beverage>(this, R.layout.list_beverage);
        
        Uri beverageUri = Uri.parse(ApplicationState.BEVERAGES_URL);
        Bundle params = new Bundle();
        params.putInt("categoryId", this.getIntent().getExtras().getInt("currentCategory"));
        
        Bundle args = new Bundle();
        args.putParcelable(ARGS_URI, beverageUri);
        args.putParcelable(ARGS_PARAMS, params);
        
        getSupportLoaderManager().initLoader(LOADER_ID, args, this);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	  super.onSaveInstanceState(savedInstanceState);
	  savedInstanceState.putInt("currentCategory", this.getIntent().getExtras().getInt("currentCategory"));
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		this.getIntent().putExtra("currentCategory",savedInstanceState.getInt("currentCategory"));
	}
	
	@Override
    public Loader<RESTLoader.RESTResponse> onCreateLoader(int id, Bundle args) {
        if (args != null && args.containsKey(ARGS_URI) && args.containsKey(ARGS_PARAMS)) {
            Uri    action = args.getParcelable(ARGS_URI);
            Bundle params = args.getParcelable(ARGS_PARAMS);
            return new RESTLoader(this, RESTLoader.HTTPVerb.GET, action, params);
        }
        return null;
    }

	@Override
    public void onLoadFinished(Loader<RESTLoader.RESTResponse> loader, RESTLoader.RESTResponse data) {
        int    code = data.getCode();
        String json = data.getData();
        
        if (code == 200 && !json.equals("")) {
            ListView listView = (ListView) findViewById(R.id.listViewBeverages);
            try{
            	BeverageWrapper response = new Gson().fromJson(json, BeverageWrapper.class);
            	List<Beverage> beverages = response.getBeverages();
            	mAdapter.clear();
            	for (Beverage beverage : beverages) {
            		mAdapter.add(beverage);
            	}
            	listView.setAdapter(mAdapter);
            	listView.setOnItemClickListener(new OnItemClickListener() {
            		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            			listViewClicked(parent, view, position, id);
            		}
            	});
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

	private void listViewClicked(AdapterView<?> parent, View view, int position, long id) {
		Beverage beverage = (Beverage) parent.getAdapter().getItem(position);
		Intent intent = new Intent(this, RecipeActivity.class);
		intent.putExtra("currentBeverage", beverage.getId());
		startActivity(intent);
	}

	@Override
	public void onLoaderReset(Loader<RESTResponse> arg0) {
	}

}
