package com.example.virtualfridge.data.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

interface EventsApi {

    @FormUrlEncoded
    @POST("events/create")
    fun createEvent(
        @Field("userId") userId: String,
        @Field("title") title: String,
        @Field("description") description: String?,
        @Field("place") place: String?,
        @Field("startDate") startDate: String,
        @Field("endDate") endDate: String
    ): Observable<ResponseBody>

    @FormUrlEncoded
    @PUT("events/edit")
    fun editEvent(
        @Path("eventId") eventId: String,
        @Field("title") title: String,
        @Field("text") text: String,
        @Field("place") place: String,
        @Field("startDate") startDate: String,
        @Field("endDate") endDate: String
    ): Observable<ResponseBody>

    @GET("events/{userId}")
    fun events(
        @Path("userId") userId: String
    ): Observable<ResponseBody>

    @DELETE("events/delete/{eventId}")
    fun deleteEvent(
        @Path("eventId") eventId: String
    ): Observable<ResponseBody>

}
