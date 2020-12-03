package com.example.virtualfridge.di

import android.app.Application
import com.example.virtualfridge.data.internal.UserDataStore
import dagger.Module
import dagger.Provides

@Module
public class VfApplicationModule {

    @Provides
    fun provideFragmentManager(context: Application): UserDataStore {
        return UserDataStore(context)
    }

}
