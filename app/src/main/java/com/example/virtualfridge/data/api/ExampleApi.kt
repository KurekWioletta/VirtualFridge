package com.example.virtualfridge.data.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface ExampleApi {

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Observable<ResponseBody>

}
