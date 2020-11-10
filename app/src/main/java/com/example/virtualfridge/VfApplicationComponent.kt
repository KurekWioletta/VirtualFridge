package com.example.virtualfridge

import android.app.Application
import com.example.virtualfridge.data.api.ExampleApiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    VfApplicationModule::class,
    AndroidInjectionModule::class,
    ExampleApiModule::class,
    ActivitiesModule::class,
    FragmentsModule::class
])
interface VfApplicationComponent {

    @Component.Builder
    interface Builder {

        fun build(): VfApplicationComponent

        @BindsInstance
        fun applicationBind(application: Application): Builder
    }

    fun inject(application: VfApplication)
}
