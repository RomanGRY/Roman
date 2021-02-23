package com.wvapp.webviewapp.DB;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PersonViewModel.class)
    abstract ViewModel bindPersonViewModel(PersonViewModel personViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Provides
    static Person person(){
        return new Person();
    }
}
