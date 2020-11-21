package com.example.virtualfridge.di

import com.example.virtualfridge.domain.board.BoardFragment
import com.example.virtualfridge.domain.calendar.CalendarFragment
import com.example.virtualfridge.domain.family.FamilyFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector(
        modules = [
        ]
    )
    abstract fun bindCalendarFragment(): CalendarFragment

    @ContributesAndroidInjector(
        modules = [
        ]
    )
    abstract fun bindFamilyFragment(): FamilyFragment

    @ContributesAndroidInjector(
        modules = [
        ]
    )
    abstract fun bindBoardFragment(): BoardFragment

}
