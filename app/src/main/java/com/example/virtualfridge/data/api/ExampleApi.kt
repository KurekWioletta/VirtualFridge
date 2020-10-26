package com.example.virtualfridge.data.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

interface ExampleApi {

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Observable<ResponseBody>

    @FormUrlEncoded
    @POST("users/register")
    fun registerUser(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String
    ): Observable<ResponseBody>

}
