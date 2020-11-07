package com.example.virtualfridge.domain.main

import com.example.virtualfridge.domain.base.BaseActivity
import dagger.Binds
import dagger.Module

@Module
class MainActivityProvidesModule {

}

@Module
abstract class MainActivityBindsModule {

    @Binds
    abstract fun provideBaseActivity(loginActivity: MainActivity): BaseActivity

}