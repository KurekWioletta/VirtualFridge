package com.example.virtualfridge.di

import android.app.Application
import com.example.virtualfridge.data.internal.UserDataStore
import dagger.Module
import dagger.Provides

@Module
public class VfApplicationModule {

    @Provides
    // zaleznosci mozna providowac tak jak tu przez @Provides lub @Inject przy konstruktorze wstrzykiwanej klasy np kazdy presenter
    fun provideFragmentManager(context: Application): UserDataStore {
        return UserDataStore(context)
    }

}
