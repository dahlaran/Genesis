package com.dahlaran.genesis.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dahlaran.genesis.viewmodels.ViewModelFactory
import com.dahlaran.genesis.viewmodels.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    protected abstract fun weatherViewModel(weatherViewModel: WeatherViewModel): ViewModel

}