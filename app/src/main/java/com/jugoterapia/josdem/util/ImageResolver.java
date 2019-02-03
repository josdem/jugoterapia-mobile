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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.jugoterapia.josdem.R;
import com.jugoterapia.josdem.model.Beverage;

import java.net.URL;
import java.io.IOException;

public class ImageResolver {

  public static void setImage(ImageView image, Beverage beverage) {
    if(beverage.getImage() == ""){
      image.setImageResource(R.drawable.no_image);
    } else {
      try {
        URL url = new URL(beverage.getImage());
        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        image.setImageBitmap(bmp);
      } catch (IOException ioe){
        Log.d("error", ioe.getMessage());
      }
    }
  }

}
