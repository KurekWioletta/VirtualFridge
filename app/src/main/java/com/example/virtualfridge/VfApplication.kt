package com.example.virtualfridge

import android.app.Application
import com.example.virtualfridge.data.api.UserApi
import com.example.virtualfridge.data.internal.UserDataStore
import com.example.virtualfridge.di.DaggerVfApplicationComponent
import com.example.virtualfridge.utils.RxTransformerManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class VfApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var userDataStore: UserDataStore

    @Inject
    lateinit var rxTransformerManager: RxTransformerManager

    @Inject
    lateinit var userApi: UserApi

    override fun onCreate() {
        super.onCreate()
        // wstrzykniecie klasy aplikacji do glownego komponentu aplikacji, komponent ten jest klasa generowana
        DaggerVfApplicationComponent
            .builder()
            .applicationBind(this)
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    // wyslanie odswiezonego tokenu na serwer, aby w bazie byl zawsze zapisany poprawny token
    // dzieki niemu mozemy wyslac powiadomienie na konkretne urzadzenie
    fun refreshNotificationToken(token: String) {
        userDataStore.user()?.apply {
            userApi.notifications(id, token)
                .compose { rxTransformerManager.applyIOScheduler(it) }
                .subscribe({}, {})
        }
    }
}