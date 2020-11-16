package com.example.virtualfridge.domain.settings

import com.example.virtualfridge.domain.logout.LogoutManager
import javax.inject.Inject

class SettingsActivityPresenter @Inject constructor(
    private val view: SettingsActivity,
    private val logoutManager: LogoutManager
) {

    fun logoutClicked() {
        logoutManager.logout()
        view.openLoginActivity()
    }
}