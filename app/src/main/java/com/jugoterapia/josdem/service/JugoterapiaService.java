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

package com.jugoterapia.josdem.service;

import com.jugoterapia.josdem.model.Beverage;
import com.jugoterapia.josdem.model.Category;
import com.jugoterapia.josdem.state.ApplicationState;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JugoterapiaService {

  @GET("/categories/{language}")
  public Call<List<Category>> getCategories(@Path("language") String language);

  @GET("/categories/{categoryId}/beverages")
  public Call<List<Beverage>> getBeverages(@Path("categoryId") Integer id);

  @GET("/beverages/{beverageId}")
  public Call<Beverage> getBeverage(@Path("beverageId") Integer id);

  public static final Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(ApplicationState.getServiceUrl())
          .addConverterFactory(GsonConverterFactory.create())
          .build();

}
