package com.example.virtualfridge.domain.main

import com.example.virtualfridge.data.api.ExampleApi
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(
    private val view: MainActivity,
    private val exampleApi: ExampleApi
) {
}