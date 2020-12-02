package com.example.virtualfridge.domain.family

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class FamilyFragmentModule {

    @Provides
    fun provideContext(calendarFragment: FamilyFragment): Context =
        calendarFragment.requireContext()

}