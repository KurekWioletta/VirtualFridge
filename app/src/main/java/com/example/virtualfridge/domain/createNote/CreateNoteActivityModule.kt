package com.example.virtualfridge.domain.createNote

import com.example.virtualfridge.domain.base.BaseActivity
import dagger.Binds
import dagger.Module

@Module
class CreateNoteActivityProvidesModule {

}

@Module
abstract class CreateNoteActivityBindsModule {

    @Binds
    abstract fun provideBaseActivity(loginActivity: CreateNoteActivity): BaseActivity

}