package com.example.virtualfridge.di

import com.example.virtualfridge.domain.base.BaseActivityModule
import com.example.virtualfridge.domain.createEvent.CreateEventActivity
import com.example.virtualfridge.domain.createEvent.CreateEventActivityBindsModule
import com.example.virtualfridge.domain.createNote.CreateNoteActivity
import com.example.virtualfridge.domain.createNote.CreateNoteActivityBindsModule
import com.example.virtualfridge.domain.login.LoginActivity
import com.example.virtualfridge.domain.login.LoginActivityBindsModule
import com.example.virtualfridge.domain.login.LoginActivityProvidesModule
import com.example.virtualfridge.domain.main.MainActivity
import com.example.virtualfridge.domain.main.MainActivityBindsModule
import com.example.virtualfridge.domain.main.MainActivityProvidesModule
import com.example.virtualfridge.domain.register.RegisterActivity
import com.example.virtualfridge.domain.register.RegisterActivityBindsModule
import com.example.virtualfridge.domain.settings.SettingsActivity
import com.example.virtualfridge.domain.settings.SettingsActivityBindsModule
import com.example.virtualfridge.domain.settings.SettingsActivityProvidesModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(
        modules = [
            MainActivityProvidesModule::class,
            MainActivityBindsModule::class,
            BaseActivityModule::class
        ]
    )
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(
        modules = [
            SettingsActivityProvidesModule::class,
            SettingsActivityBindsModule::class,
            BaseActivityModule::class
        ]
    )
    abstract fun bindSettingsActivity(): SettingsActivity

    @ContributesAndroidInjector(
        modules = [
            LoginActivityProvidesModule::class,
            LoginActivityBindsModule::class,
            BaseActivityModule::class
        ]
    )
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(
        modules = [
            RegisterActivityBindsModule::class,
            BaseActivityModule::class
        ]
    )
    abstract fun bindRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector(
        modules = [
            CreateEventActivityBindsModule::class,
            BaseActivityModule::class
        ]
    )
    abstract fun bindCreateEventActivity(): CreateEventActivity

    @ContributesAndroidInjector(
        modules = [
            CreateNoteActivityBindsModule::class,
            BaseActivityModule::class
        ]
    )
    abstract fun bindCreateNoteActivity(): CreateNoteActivity

}
