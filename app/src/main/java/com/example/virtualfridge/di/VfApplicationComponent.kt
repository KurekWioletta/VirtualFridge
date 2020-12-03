package com.example.virtualfridge.di

import android.app.Application
import com.example.virtualfridge.VfApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        VfApplicationModule::class,
        AndroidInjectionModule::class,
        ApisModule::class,
        ActivitiesModule::class,
        SingletonsModule::class,
        FragmentsModule::class
    ]
)
interface VfApplicationComponent {

    @Component.Builder
    interface Builder {

        fun build(): VfApplicationComponent

        @BindsInstance
        fun applicationBind(application: Application): Builder
    }

    fun inject(application: VfApplication)
}
