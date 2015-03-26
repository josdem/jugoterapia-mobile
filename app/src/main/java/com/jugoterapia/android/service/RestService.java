package com.jugoterapia.android.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class RestService {
	
	private static final String EMPTY_STRING = "";

	public String post(String url, String key) {
		try{
			DefaultHttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost(url);
		    
		    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		    formparams.add(new BasicNameValuePair("key", key));
		    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "latin1");
		    
	    	httppost.setEntity(entity);
	    	HttpResponse response = httpclient.execute(httppost);
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			return EMPTY_STRING;
		}
	}

	public String get(String url) {
		try{
			DefaultHttpClient httpclient = new DefaultHttpClient();
		    HttpGet httpget = new HttpGet(url);
	    	HttpResponse response = httpclient.execute(httpget);
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			return EMPTY_STRING;
		}
	}
}
