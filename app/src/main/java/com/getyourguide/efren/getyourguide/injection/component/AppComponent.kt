package com.getyourguide.efren.getyourguide.injection.component

import com.getyourguide.efren.getyourguide.GygApplication
import com.getyourguide.efren.getyourguide.injection.module.AppModule
import com.getyourguide.efren.getyourguide.injection.module.BuildersModule
import com.getyourguide.efren.getyourguide.injection.module.FragmentModule
import com.getyourguide.efren.getyourguide.injection.module.NetworkModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@SuppressWarnings("unchecked")
@Component(modules = [(AndroidInjectionModule::class), (BuildersModule::class), (AppModule::class), (NetworkModule::class), (FragmentModule::class)])
interface AppComponent {
    fun inject(app: GygApplication)
}