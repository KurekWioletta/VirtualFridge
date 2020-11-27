package com.example.virtualfridge.data.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

interface NotesApi {

    @FormUrlEncoded
    @POST("notes/create")
    fun createNote(
        @Field("userId") userId: String,
        @Field("familyMemberId") familyMemberId: String,
        @Field("note") note: String
    ): Observable<ResponseBody>

    @GET("notes/{userId}")
    fun notes(
        @Path("userId") userId: String
    ): Observable<ResponseBody>

    @DELETE("notes/delete/{noteId}")
    fun deleteNote(
        @Path("noteId") noteId: String
    ): Observable<ResponseBody>

}
