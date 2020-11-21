package com.example.virtualfridge.data.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface NotesApi {

    @FormUrlEncoded
    @POST("notes/add")
    fun createNote(
        @Field("userId") userId: String,
        @Field("familyMemberId") familyMemberId: String,
        @Field("note") note: String
    ): Observable<ResponseBody>

    @POST("notes/{userId}")
    fun notes(
        @Path("userId") userId: String
    ): Observable<ResponseBody>

    @POST("notes/delete/{id}")
    fun deleteNote(
        @Path("id") noteId: String
    ): Observable<ResponseBody>

}
