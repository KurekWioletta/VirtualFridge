package com.example.virtualfridge.domain.board

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class BoardFragmentModule {

    @Provides
    fun provideContext(calendarFragment: BoardFragment): Context =
        calendarFragment.requireContext()

}