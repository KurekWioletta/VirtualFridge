package com.example.virtualfridge.domain.calendar

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class CalendarFragmentModule {

    @Provides
    fun provideContext(calendarFragment: CalendarFragment): Context =
        calendarFragment.requireContext()

}