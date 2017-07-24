package com.jugoterapia.josdem.module;

import android.app.Application;
import android.content.Context;

import com.jugoterapia.josdem.context.ApplicationContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

  private final Application application;

  public ApplicationModule(Application application) {
    this.application = application;
  }

  @Provides
  @ApplicationContext
  Context provideContext() {
    return application;
  }

  @Provides
  Application provideApplication() { return application; }

}