package com.example.virtualfridge.data.api

import com.example.virtualfridge.data.api.models.FamilyMemberResponse
import com.example.virtualfridge.data.api.models.InvitationResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

interface FamilyApi {

    @GET("family/members/{userId}")
    fun familyMembers(
        @Path("userId") userId: String
    ): Observable<List<FamilyMemberResponse>>

    @GET("family/invitations/{userId}")
    fun invitations(
        @Path("userId") userId: String
    ): Observable<List<InvitationResponse>>

    @PUT("family/invitations/accept")
    fun acceptInvitation(
        @Query("invitationId") invitationId: String
    ): Observable<String>

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
    ): Observable<String>

    @POST("family/invite")
    fun inviteMember(
        @Query("userId") userId: String,
        @Query("memberEmail") memberEmail: String
    ): Observable<ResponseBody>

}
