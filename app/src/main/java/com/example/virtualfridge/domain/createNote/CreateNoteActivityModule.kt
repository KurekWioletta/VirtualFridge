package com.example.virtualfridge.domain.createNote

import android.content.Context
import com.example.virtualfridge.domain.base.BaseActivity
import dagger.Binds
import dagger.Module

@Module
abstract class CreateNoteActivityBindsModule {

    @Binds
    abstract fun provideBaseActivity(loginActivity: CreateNoteActivity): BaseActivity

    @Binds
    abstract fun provideContext(loginActivity: CreateNoteActivity): Context

}