package com.example.virtualfridge.domain.main

import com.example.virtualfridge.data.api.UserApi
import com.example.virtualfridge.data.internal.UserDataStore
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(
    private val view: MainActivity,
    private val userApi: UserApi,
    private val userDataStore: UserDataStore
) {

    fun init() {
    }
}