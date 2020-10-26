package com.example.virtualfridge

import com.example.virtualfridge.ui.login.LoginActivity
import com.example.virtualfridge.ui.login.LoginActivityModule
import com.example.virtualfridge.ui.main.MainActivity
import com.example.virtualfridge.ui.register.RegisterActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [
        LoginActivityModule::class
    ])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun bindRegisterActivity(): RegisterActivity

}
