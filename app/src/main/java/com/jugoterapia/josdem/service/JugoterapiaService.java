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

package com.jugoterapia.josdem.service;

import java.util.List;

import com.jugoterapia.josdem.model.Beverage;
import com.jugoterapia.josdem.model.Category;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JugoterapiaService {

  @GET("/jugoterapia-server/beverage/categories")
  public Call<List<Category>> getCategories();

  @GET("http://jugoterapia.josdem.io/jugoterapia-server/beverage/beverages")
  public Call<List<Beverage>> getBeverages(@Query("categoryId") Integer id);

  public static final Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("http://jugoterapia.josdem.io/")
          .addConverterFactory(GsonConverterFactory.create())
          .build();

}
