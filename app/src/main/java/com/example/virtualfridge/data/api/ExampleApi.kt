package com.example.virtualfridge.data.api

import com.example.virtualfridge.data.models.UserResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ExampleApi {
    @FormUrlEncoded
    @POST("user/login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Observable<UserResponse>

    @FormUrlEncoded
    @POST("registration/register")
    fun registerUser(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String
    ): Observable<UserResponse>

    @FormUrlEncoded
    @POST("registration/register_with_google")
    fun registerUserWithGoogle(
        @Field("email") email: String,
        @Field("google_id") googleId: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String
    ): Observable<UserResponse>
}
