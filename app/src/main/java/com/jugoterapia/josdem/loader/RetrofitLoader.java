/*
  Copyright 2015 José Luis De la Cruz Morales <joseluis.delacruz@gmail.com>

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

import android.content.AsyncTaskLoader;
import android.content.Context;

public abstract class RetrofitLoader<D, R> extends AsyncTaskLoader<Response<D>> {

  private final R mService;

  private Response<D> mCachedResponse;

  public RetrofitLoader(Context context, R service) {

    super(context);

    mService = service;
  }

  @Override
  public Response<D> loadInBackground() {

    try {

      final D data = call(mService);
      mCachedResponse = Response.ok(data);

    } catch (Exception ex) {

      mCachedResponse = Response.error(ex);
    }

    return mCachedResponse;
  }

  @Override
  protected void onStartLoading() {

    super.onStartLoading();

    if (mCachedResponse != null) {

      deliverResult(mCachedResponse);
    }

    if (takeContentChanged() || mCachedResponse == null) {

      forceLoad();
    }
  }

  @Override
  protected void onReset() {

    super.onReset();

    mCachedResponse = null;
  }

  public abstract D call(R service);
}
