package com.example.virtualfridge

import com.example.virtualfridge.domain.base.BaseActivityModule
import com.example.virtualfridge.domain.login.LoginActivity
import com.example.virtualfridge.domain.login.LoginActivityBindsModule
import com.example.virtualfridge.domain.login.LoginActivityProvidesModule
import com.example.virtualfridge.domain.main.MainActivity
import com.example.virtualfridge.domain.register.RegisterActivity
import com.example.virtualfridge.domain.register.RegisterActivityBindsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [
        LoginActivityProvidesModule::class,
        LoginActivityBindsModule::class,
        BaseActivityModule::class
    ])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [
        RegisterActivityBindsModule::class,
        BaseActivityModule::class
    ])
    abstract fun bindRegisterActivity(): RegisterActivity

}
