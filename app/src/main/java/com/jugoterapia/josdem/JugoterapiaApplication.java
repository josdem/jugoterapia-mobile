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

package com.jugoterapia.josdem;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.jugoterapia.josdem.component.ApplicationComponent;
import com.jugoterapia.josdem.component.DaggerApplicationComponent;
import com.jugoterapia.josdem.module.ApplicationModule;
import com.jugoterapia.josdem.state.ApplicationState;

import io.fabric.sdk.android.Fabric;

public class JugoterapiaApplication extends Application {

  protected ApplicationComponent applicationComponent;

  public static JugoterapiaApplication get(Context context) {
    return (JugoterapiaApplication) context.getApplicationContext();
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Fabric.with(this, new Crashlytics());
    ApplicationState.initializeFirebaseRemoteConfig();
    applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(new ApplicationModule(this))
            .build();
    applicationComponent.inject(this);
  }

  public ApplicationComponent getComponent(){
    return applicationComponent;
  }

}