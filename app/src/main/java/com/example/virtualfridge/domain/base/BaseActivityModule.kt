package com.example.virtualfridge.domain.base

import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides

@Module
class BaseActivityModule {

    @Provides
    fun provideFragmentManager(activity: BaseActivity): FragmentManager = activity.supportFragmentManager

}