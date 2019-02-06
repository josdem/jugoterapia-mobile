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

package com.jugoterapia.josdem.util;

import java.io.InputStream;

import android.util.Log;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
  ImageView imageView;

  public DownloadImageTask(ImageView imageView) {
    this.imageView = imageView;
  }

  protected Bitmap doInBackground(String... urls) {
    String urldisplay = urls[0];
    Bitmap mIcon11 = null;
    try {
      InputStream in = new java.net.URL(urldisplay).openStream();
      mIcon11 = BitmapFactory.decodeStream(in);
    } catch (Exception e) {
      Log.e("Error", e.getMessage());
      e.printStackTrace();
    }
    return mIcon11;
  }

  protected void onPostExecute(Bitmap result) {
    imageView.setImageBitmap(result);
  }

}
