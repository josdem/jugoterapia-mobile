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

package com.jugoterapia.josdem.loader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class RestLoader extends AsyncTaskLoader<RestResponse> {
    private static final String TAG = RestLoader.class.getName();
    private static final long STALE_DELTA = 60 * 1000;

    private Uri action;
    private Bundle params;
    private RestResponse restResponse;

    private long lastLoad;

    public RestLoader(Context context, Uri action, Bundle params) {
        super(context);
        this.action = action;
        this.params = params;
    }

    @Override
    public RestResponse loadInBackground() {
        try {
            Log.d(TAG, "Executing request: " + ": "+ action.toString());
            HttpRequestBase request = new HttpGet();
            attachUriWithQuery(request, action, params);
            HttpClient client = new DefaultHttpClient();

            HttpResponse response = client.execute(request);
            HttpEntity responseEntity = response.getEntity();
            StatusLine responseStatus = response.getStatusLine();
            int statusCode = responseStatus != null ? responseStatus.getStatusCode() : 0;
            return new RestResponse(responseEntity != null ? EntityUtils.toString(responseEntity) : null, statusCode);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "A UrlEncodedFormEntity was created with an unsupported encoding.", e);
            return new RestResponse();
        } catch (ClientProtocolException e) {
            Log.e(TAG, "There was a problem when sending the request.", e);
            return new RestResponse();
        } catch (IOException e) {
            Log.e(TAG, "There was a problem when sending the request.", e);
            return new RestResponse();
        }
    }

    @Override
    public void deliverResult(RestResponse data) {
        restResponse = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if (restResponse != null) {
            super.deliverResult(restResponse);
        }
        if (restResponse == null || System.currentTimeMillis() - lastLoad >= STALE_DELTA) forceLoad();
        lastLoad = System.currentTimeMillis();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        restResponse = null;
        lastLoad = 0;
    }

    private void attachUriWithQuery(HttpRequestBase request, Uri uri, Bundle params) {
        try {
            Uri.Builder uriBuilder = uri.buildUpon();
            for (BasicNameValuePair param : paramsToList(params)) {
                uriBuilder.appendQueryParameter(param.getName(), param.getValue());
            }

            uri = uriBuilder.build();
            request.setURI(new URI(uri.toString()));

        } catch (URISyntaxException e) {
            Log.e(TAG, "URI syntax was incorrect: "+ uri.toString());
        }
    }

    private List<BasicNameValuePair> paramsToList(Bundle params) {
        List formList = new ArrayList<BasicNameValuePair>();

        for (String key : params.keySet()) {
            Object value = params.get(key);
            if (value != null) formList.add(new BasicNameValuePair(key, value.toString()));
        }

        return formList;
    }

}
