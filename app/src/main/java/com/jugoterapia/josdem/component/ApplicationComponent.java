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

import android.app.Application;
import android.content.Context;

import com.jugoterapia.josdem.JugoterapiaApplication;
import com.jugoterapia.josdem.context.ApplicationContext;
import com.jugoterapia.josdem.module.ApplicationModule;
import com.jugoterapia.josdem.service.impl.JugoterapiaServiceImpl;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

  void inject(JugoterapiaApplication jugoterapiaApplication);

  @ApplicationContext
  Context getContext();

  Application getApplication();

  JugoterapiaServiceImpl getJugoterapiaServiceImpl();

}