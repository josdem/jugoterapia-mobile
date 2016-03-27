package com.jugoterapia.josdem.service

import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils

import groovy.transform.CompileStatic

@CompileStatic
class RestService {

	private static final String EMPTY_STRING = ""

	String post(String url, String key) {
		try{
			DefaultHttpClient httpclient = new DefaultHttpClient()
		    HttpPost httppost = new HttpPost(url)

		    List<NameValuePair> formparams = new ArrayList<NameValuePair>()
		    formparams.add(new BasicNameValuePair("key", key))
		    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "utf8")

	    	httppost.setEntity(entity)
	    	HttpResponse response = httpclient.execute(httppost)
			return EntityUtils.toString(response.getEntity())
		} catch (Exception e) {
			e.printStackTrace()
			return EMPTY_STRING
		}
	}

	String get(String url) {
		try{
			DefaultHttpClient httpclient = new DefaultHttpClient()
		    HttpGet httpget = new HttpGet(url)
	    	HttpResponse response = httpclient.execute(httpget)
			return EntityUtils.toString(response.getEntity())
		} catch (Exception e) {
			e.printStackTrace()
			return EMPTY_STRING
		}
	}
}
