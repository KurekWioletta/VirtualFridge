package com.example.virtualfridge.domain.main

import com.example.virtualfridge.data.api.ExampleApi
import com.example.virtualfridge.data.internal.UserDataStore
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(
    private val view: MainActivity,
    private val exampleApi: ExampleApi,
    private val userDataStore: UserDataStore
) {

    fun init() {
    }
}