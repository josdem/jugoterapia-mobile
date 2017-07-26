package com.jugoterapia.josdem.component;

import com.jugoterapia.josdem.activity.CategoryActivity;
import com.jugoterapia.josdem.context.PerActivity;
import com.jugoterapia.josdem.model.Category;
import com.jugoterapia.josdem.module.ActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

  void inject(CategoryActivity mainActivity);

}