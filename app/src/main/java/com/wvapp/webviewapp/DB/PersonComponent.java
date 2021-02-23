package com.wvapp.webviewapp.DB;

import com.wvapp.webviewapp.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ViewModelModule.class)
public interface PersonComponent {

    void inject(MainActivity mainActivity);
}
