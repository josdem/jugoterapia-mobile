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

package com.jugoterapia.josdem.service.impl;

import android.util.Log;

import com.jugoterapia.josdem.model.Beverage;
import com.jugoterapia.josdem.model.Category;
import com.jugoterapia.josdem.model.Credentials;
import com.jugoterapia.josdem.service.JugoterapiaService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Query;

@Singleton
public class JugoterapiaServiceImpl implements JugoterapiaService {

  JugoterapiaService jugoterapiaService = null;

  @Inject
  public JugoterapiaServiceImpl(){
    jugoterapiaService = JugoterapiaService.retrofit.create(JugoterapiaService.class);
  }

  @Override
  public Call<List<Category>> getCategories() {
    return jugoterapiaService.getCategories();
  }

  @Override
  public Call<List<Beverage>> getBeverages(@Query("categoryId") Integer id) {
    return jugoterapiaService.getBeverages(id);
  }

  @Override
  public Call<Beverage> getBeverage(@Query("beverageId") Integer id) {
    return jugoterapiaService.getBeverage(id);
  }

  @Override
  public Call<Credentials> sendCredentials(@Body Credentials credentials) {
    Log.d("credentials: ", credentials.getName() + " : " + credentials.getEmail() + " : " + credentials.getToken());
    return jugoterapiaService.sendCredentials(credentials);
  }

}
