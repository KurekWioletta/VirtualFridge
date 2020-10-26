package com.example.virtualfridge.utils

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

import javax.inject.Singleton

@Singleton
class RxTransformerManager @Inject constructor() {
    fun <T> applyIOScheduler(observable: Observable<T>): Observable<T> =
        observable.subscribeOn(schedulerIo())
            .observeOn(schedulerUi())

    fun schedulerIo(): Scheduler = Schedulers.io()

    fun schedulerUi(): Scheduler = mainThread()
}