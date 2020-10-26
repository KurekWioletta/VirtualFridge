package com.example.virtualfridge.ui.login

import com.example.virtualfridge.ui.base.BaseActivity
import com.example.virtualfridge.ui.login.google.GoogleLoginListener
import com.example.virtualfridge.ui.login.google.GoogleLoginManager
import dagger.Binds
import dagger.Module


@Module
abstract class LoginActivityModule {

    @Binds
    abstract fun provideBaseActivity(loginActivity: LoginActivity): BaseActivity

    @Binds
    abstract fun provideGoogleLoginListener(loginActivity: LoginActivity): GoogleLoginListener

}