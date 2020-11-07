package com.example.virtualfridge.domain.settings

import android.content.Intent
import com.example.virtualfridge.domain.base.BaseActivity
import com.example.virtualfridge.domain.login.google.GoogleLoginListener
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class SettingsActivityProvidesModule {

    @Provides
    fun provideGoogleLoginListener(): GoogleLoginListener = object : GoogleLoginListener {
        override fun openGoogleLoginRequest(intent: Intent) {}

        override fun showGoogleLoginError() {}

        override fun openMainActivity() {}
    }

}

@Module
abstract class SettingsActivityBindsModule {

    @Binds
    abstract fun provideBaseActivity(loginActivity: SettingsActivity): BaseActivity

}