package com.example.virtualfridge.data.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

interface FamilyApi {

    @GET("family/invitations/{userId}")
    fun invitations(
        @Path("userId") userId: String
    ): Observable<ResponseBody>

    @PUT("family/invitations/accept")
    fun acceptInvitation(
        @Query("invitationId") invitationId: String
    ): Observable<ResponseBody>

    @PUT("family/invitations/decline")
    fun declineInvitation(
        @Query("invitationId") invitationId: String
    ): Observable<ResponseBody>

    @PUT("family/leave")
    fun leaveFamily(
        @Query("userId") userId: String
    ): Observable<ResponseBody>

    @POST("family/create")
    fun createFamily(
        @Query("userId") userId: String,
        @Query("familyName") familyName: String
    ): Observable<ResponseBody>

    @POST("family/invite")
    fun inviteMember(
        @Query("userId") userId: String,
        @Query("memberEmail") memberEmail: String
    ): Observable<ResponseBody>

}
