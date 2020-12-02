package com.example.virtualfridge.domain.register

import android.content.Context
import com.example.virtualfridge.domain.base.BaseActivity
import dagger.Binds
import dagger.Module

@Module
abstract class RegisterActivityBindsModule {

    @Binds
    abstract fun provideBaseActivity(registerActivity: RegisterActivity): BaseActivity

    @Binds
    abstract fun provideContext(registerActivity: RegisterActivity): Context

}