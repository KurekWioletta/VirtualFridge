package com.example.virtualfridge.di

import com.example.virtualfridge.data.api.EventsApi
import com.example.virtualfridge.data.api.FamilyApi
import com.example.virtualfridge.data.api.NotesApi
import com.example.virtualfridge.data.api.UserApi
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object ApisModule {

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
//            .baseUrl("https://safe-peak-25532.herokuapp.com/")
            .baseUrl("http://10.0.2.2:8080".toHttpUrl())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    fun provideBoardApi(retrofit: Retrofit): NotesApi = retrofit.create(NotesApi::class.java)

    @Provides
    fun provideFamilyApi(retrofit: Retrofit): FamilyApi = retrofit.create(FamilyApi::class.java)

    @Provides
    fun provideEventsApi(retrofit: Retrofit): EventsApi = retrofit.create(EventsApi::class.java)

}