package com.dahlaran.genesis.di.application

import android.app.Application
import com.dahlaran.genesis.di.OpenWeatherApiDbModule
import com.dahlaran.genesis.di.ViewModelModule
import com.dahlaran.genesis.view.GenesisApplication
import com.dahlaran.genesis.view.widget.WeatherWidgetProvider
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityModule::class, OpenWeatherApiDbModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(genesisApplication: GenesisApplication)
    fun inject(provider: WeatherWidgetProvider)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}