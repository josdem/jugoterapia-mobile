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

import android.widget.ImageView;

import com.jugoterapia.josdem.R;
import com.jugoterapia.josdem.model.Beverage;

public class ImageResolver {

  public static void setImage(ImageView image, Beverage beverage){
    if(beverage.getId() == 66){
      image.setImageResource(R.drawable._66);
    } else {
      image.setImageResource(R.drawable.no_image);
    }
  }

}
