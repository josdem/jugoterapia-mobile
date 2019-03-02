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

package com.jugoterapia.josdem.service.impl;

import com.jugoterapia.josdem.model.Beverage;
import com.jugoterapia.josdem.model.Category;
import com.jugoterapia.josdem.service.JugoterapiaService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.http.Path;

@Singleton
public class JugoterapiaServiceImpl implements JugoterapiaService {

  JugoterapiaService jugoterapiaService = null;

  @Inject
  public JugoterapiaServiceImpl(){
    jugoterapiaService = JugoterapiaService.retrofit.create(JugoterapiaService.class);
  }

  @Override
  public Call<List<Category>> getCategories(@Path("language") String language) {
    return jugoterapiaService.getCategories(language);
  }

  @Override
  public Call<List<Beverage>> getBeverages(@Path("categoryId") Integer id) {
    return jugoterapiaService.getBeverages(id);
  }

  @Override
  public Call<Beverage> getBeverage(@Path("beverageId") Integer id) {
    return jugoterapiaService.getBeverage(id);
  }

}
