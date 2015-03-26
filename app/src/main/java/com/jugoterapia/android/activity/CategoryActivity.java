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
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jugoterapia.android.R;
import com.jugoterapia.android.adapter.CategoryAdapter;
import com.jugoterapia.android.bean.CategoryWrapper;
import com.jugoterapia.android.loader.RESTLoader;
import com.jugoterapia.android.loader.RESTLoader.RESTResponse;
import com.jugoterapia.android.model.Category;
import com.jugoterapia.android.state.ApplicationState;

/**
 * @understands It shows juice categories 
 */

public class CategoryActivity extends FragmentActivity implements LoaderCallbacks<RESTLoader.RESTResponse> {
	
	private static final String ARGS_URI    = "com.jugoterapia.android.activity.ARGS_URI";
    private static final String ARGS_PARAMS = "com.jugoterapia.android.activity.ARGS_PARAMS";
    private static final int LOADER_ID = 0x1;
    
    CategoryAdapter adapter;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		
		FragmentManager fm = getSupportFragmentManager();
		
		ListFragment list =(ListFragment) fm.findFragmentById(R.id.frameLayout); 
        if (list == null){
        	list = new ListFragment();
        	FragmentTransaction ft = fm.beginTransaction();
        	ft.add(R.id.frameLayout, list);
        	ft.commit();
        }
        
        adapter = new CategoryAdapter(this, R.layout.list_category);
        
        Uri beverageUri = Uri.parse(ApplicationState.CATEGORIES_URL);
        Bundle params = new Bundle();
        
        Bundle args = new Bundle();
        args.putParcelable(ARGS_URI, beverageUri);
        args.putParcelable(ARGS_PARAMS, params);
        
        getSupportLoaderManager().initLoader(LOADER_ID, args, this);
	}
	
	private void listViewClicked(AdapterView<?> parent, View view, int position, long id) {
		Category selectedCategory = (Category) parent.getAdapter().getItem(position);
		Intent intent = new Intent(this, BeverageActivity.class);
		intent.putExtra("currentCategory", selectedCategory.getId());
		startActivity(intent);
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
        int code = data.getCode();
        String json = data.getData();
        
        if (code == 200 && !json.equals("")) {
            ListView listView = (ListView) findViewById(R.id.listViewCategories);
            try{
            	CategoryWrapper categoryWrapper = new Gson().fromJson(json, CategoryWrapper.class);
            	List<Category> beverages = categoryWrapper.getCategories();
            	adapter.clear();
            	for (Category category : beverages) {
            		adapter.add(category);
            	} 
            	listView.setAdapter(adapter);
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

	@Override
	public void onLoaderReset(Loader<RESTResponse> arg0) {
	}

}
