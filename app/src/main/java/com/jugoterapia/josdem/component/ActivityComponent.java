package com.jugoterapia.josdem.component;

import com.jugoterapia.josdem.activity.BeverageActivity;
import com.jugoterapia.josdem.activity.CategoryActivity;
import com.jugoterapia.josdem.activity.RecipeActivity;
import com.jugoterapia.josdem.activity.SignActivity;
import com.jugoterapia.josdem.context.PerActivity;
import com.jugoterapia.josdem.model.Category;
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