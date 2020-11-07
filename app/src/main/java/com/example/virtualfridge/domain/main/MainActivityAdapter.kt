package com.example.virtualfridge.domain.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.virtualfridge.domain.board.BoardFragment
import com.example.virtualfridge.domain.calendar.CalendarFragment
import com.example.virtualfridge.domain.family.FamilyFragment
import javax.inject.Inject

class MainActivityAdapter @Inject constructor(
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment =
        when (position) {
            FRAGMENT_BOARD -> BoardFragment()
            FRAGMENT_CALENDAR -> CalendarFragment()
            FRAGMENT_FAMILY -> FamilyFragment()
            else -> throw IllegalArgumentException("Unknown fragment requested")
        }

    override fun getCount(): Int = PAGES_COUNT

    companion object {
        private const val PAGES_COUNT = 3
        public const val FRAGMENT_BOARD = 0
        public const val FRAGMENT_CALENDAR = 1
        public const val FRAGMENT_FAMILY = 2
    }
}