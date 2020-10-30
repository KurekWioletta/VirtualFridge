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
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String
    ): Observable<ResponseBody>

    @FormUrlEncoded
    @POST("users/register_with_google")
    fun registerUserWithGoogle(
        @Field("email") email: String,
        @Field("google_id") googleId: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String
    ): Observable<ResponseBody>
}
