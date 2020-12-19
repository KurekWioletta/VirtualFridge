package com.example.virtualfridge.utils

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

// klasa sluzaca do przekierowywania operatorow ciagu rxJavy na rozne watki
@Singleton
class RxTransformerManager @Inject constructor() {
    // wszystkie operatory przed ta metoda w ciagu beda odbywaly sie na IO thread
    // wszystkie operatory po tej metodzie w ciagu beda odbywaly sie na mainThread()
    // zwykle na poczatku mamy zapytanie na backend, a pozniej operacje na ui
    // w Android wszystkie operacje ktore "widoczne" musza sie odbywac w mainThread (np pokazanie dialogu)
    fun <T> applyIOScheduler(observable: Observable<T>): Observable<T> =
        observable.subscribeOn(schedulerIo())
            .observeOn(schedulerUi())

    fun schedulerIo(): Scheduler = Schedulers.io()

    fun schedulerUi(): Scheduler = mainThread()
}