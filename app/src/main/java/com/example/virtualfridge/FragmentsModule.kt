package com.example.virtualfridge

import com.example.virtualfridge.domain.calendar.CalendarFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector(
        modules = [
        ]
    )
    abstract fun bindCalendarFragment(): CalendarFragment

}
