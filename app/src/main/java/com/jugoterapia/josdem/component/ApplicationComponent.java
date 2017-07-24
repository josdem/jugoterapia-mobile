package com.jugoterapia.josdem.component;

import android.app.Application;
import android.content.Context;

import com.jugoterapia.josdem.JugoterapiaApplication;
import com.jugoterapia.josdem.context.ApplicationContext;
import com.jugoterapia.josdem.module.ApplicationModule;
import com.jugoterapia.josdem.service.JugoterapiaService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

  void inject(JugoterapiaApplication jugoterapiaApplication);

  @ApplicationContext
  Context getContext();

  Application getApplication();

  JugoterapiaService getCategories();

}