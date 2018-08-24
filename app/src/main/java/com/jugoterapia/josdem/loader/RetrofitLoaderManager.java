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

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;

public class RetrofitLoaderManager {

  public static <D, R> void init(final LoaderManager manager, final int loaderId,
                                 final RetrofitLoader<D, R> loader, final Callback<D> callback) {

    manager.initLoader(loaderId, Bundle.EMPTY, new LoaderCallbacks<Response<D>>() {

      @Override
      public Loader<Response<D>> onCreateLoader(int id, Bundle args) {

        return loader;
      }

      @Override
      public void onLoadFinished(Loader<Response<D>> loader, Response<D> data) {

        if (data.hasError()) {

          callback.onFailure(data.getException());

        } else {

          callback.onSuccess(data.getResult());
        }
      }

      @Override
      public void onLoaderReset(Loader<Response<D>> loader) {

        //Nothing to do here
      }
    });
  }
}
