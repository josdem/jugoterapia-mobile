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

package com.jugoterapia.josdem.component;

import com.jugoterapia.josdem.activity.BeverageActivity;
import com.jugoterapia.josdem.activity.CategoryActivity;
import com.jugoterapia.josdem.activity.RecipeActivity;
import com.jugoterapia.josdem.activity.SignActivity;
import com.jugoterapia.josdem.context.PerActivity;
import com.jugoterapia.josdem.module.ActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

  void inject(CategoryActivity categoryActivity);
  void inject(BeverageActivity beverageActivity);
  void inject(RecipeActivity recipeActivity);
  void inject(SignActivity signActivity);

}