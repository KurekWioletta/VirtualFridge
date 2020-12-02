package com.example.virtualfridge.di

import com.example.virtualfridge.domain.board.BoardFragment
import com.example.virtualfridge.domain.board.BoardFragmentModule
import com.example.virtualfridge.domain.calendar.CalendarFragment
import com.example.virtualfridge.domain.calendar.CalendarFragmentModule
import com.example.virtualfridge.domain.family.FamilyFragment
import com.example.virtualfridge.domain.family.FamilyFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector(
        modules = [
            CalendarFragmentModule::class
        ]
    )
    abstract fun bindCalendarFragment(): CalendarFragment

    @ContributesAndroidInjector(
        modules = [
            FamilyFragmentModule::class
        ]
    )
    abstract fun bindFamilyFragment(): FamilyFragment

    @ContributesAndroidInjector(
        modules = [
            BoardFragmentModule::class
        ]
    )
    abstract fun bindBoardFragment(): BoardFragment

}
