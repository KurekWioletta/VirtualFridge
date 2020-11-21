package com.example.virtualfridge.domain.createEvent

import com.example.virtualfridge.domain.base.BaseActivity
import dagger.Binds
import dagger.Module

@Module
class CreateEventActivityProvidesModule {

}

@Module
abstract class CreateEventActivityBindsModule {

    @Binds
    abstract fun provideBaseActivity(loginActivity: CreateEventActivity): BaseActivity

}