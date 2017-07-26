package com.jugoterapia.josdem.util;

import android.app.Activity;

import com.jugoterapia.josdem.JugoterapiaApplication;
import com.jugoterapia.josdem.component.ActivityComponent;
import com.jugoterapia.josdem.component.DaggerActivityComponent;
import com.jugoterapia.josdem.module.ActivityModule;

public class ActivityComponentFactory {

  private static ActivityComponent activityComponent = null;

  public static ActivityComponent getActivityComponent(Activity activity) {
    if (activityComponent == null) {
      activityComponent = DaggerActivityComponent.builder()
              .activityModule(new ActivityModule(activity))
              .applicationComponent(JugoterapiaApplication.get(activity).getComponent())
              .build();
    }
    return activityComponent;
  }
}
