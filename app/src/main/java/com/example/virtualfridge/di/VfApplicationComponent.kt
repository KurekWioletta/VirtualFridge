package com.example.virtualfridge.di

import android.app.Application
import com.example.virtualfridge.VfApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

// zaleznosci dla calej aplikacji (te podane bezposrednio) lub zaleznosci zyjace w obrebie ekranow (rozpoznasz po @ContributesAndroidInjector)
// wszystko co ma @ContributesAndroidInjector bedzie generowalo komponent zyjacy w oobrebie konkretnego widoku (przyklad w ActivitiesModule)
@Singleton
@Component(
    modules = [
        VfApplicationModule::class,
        AndroidInjectionModule::class,
        ApisModule::class,
        ActivitiesModule::class,
        SingletonsModule::class,
        FragmentsModule::class
    ]
)
// glowny komponent aplikacji
interface VfApplicationComponent {

    @Component.Builder
    interface Builder {

        fun build(): VfApplicationComponent

        @BindsInstance
        fun applicationBind(application: Application): Builder
    }

    // funkcja do wstrzykniecia aplikacji (wszystkie elementy ktore posiadaja swoj komponent musza byc wstrzykniete do drzewa daggerowego - zaleznosci)
    // glownym komponentem jest komponent aplikacji, pozostale to jakby subkomponenty
    fun inject(application: VfApplication)
}
