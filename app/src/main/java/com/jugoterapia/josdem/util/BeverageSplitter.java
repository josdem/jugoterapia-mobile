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

import java.util.StringTokenizer;

public class BeverageSplitter {

  public static String split(String beverages){
    StringTokenizer stringTokenizer = new StringTokenizer(beverages, ",");
    StringBuilder stringBuilder = new StringBuilder();
    while (stringTokenizer.hasMoreElements()) {
      stringBuilder.append(stringTokenizer.nextElement());
      stringBuilder.append("\n");
    }
    return stringBuilder.toString();
  }

}
