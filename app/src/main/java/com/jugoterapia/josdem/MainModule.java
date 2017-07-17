package com.jugoterapia.josdem;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

import com.jugoterapia.josdem.service.JugoterapiaService;
import com.jugoterapia.josdem.activity.CategoryActivity;

@Module(
        library = true,
        injects = {CategoryActivity.class})
public class MainModule {

  @Provides
  @Singleton
  JugoterapiaService buildRestClient() {

    RestAdapter adapter =
            new RestAdapter.Builder().setEndpoint("http://jugoterapia.josdem.io").build();

    return adapter.create(JugoterapiaService.class);
  }
}


