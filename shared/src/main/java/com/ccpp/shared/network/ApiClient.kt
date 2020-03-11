package com.ccpp.shared.network

import com.ccpp.shared.domain.*
import com.ccpp.shared.domain.match_details.MatchDetailsRes
import com.ccpp.shared.domain.contest.CreateContestRes
import com.ccpp.shared.domain.contest.MatchContestRes
import com.ccpp.shared.domain.contest.UserMatchContestRes
import com.ccpp.shared.domain.create_bet.CreateBetReq
import com.ccpp.shared.domain.create_bet.CreateBetRes
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

internal interface ApiClient {
    @POST("oauth/token?grant_type=password")
    fun callLoginAsync(@QueryMap options: Map<String, String>): Deferred<Response<LoginRes>>


    @GET("top-headlines?sources=google-news&apiKey=f1e5ca69296b4e70a3fb7fc722a63615")
    fun callLoginWithSocialAsync(
        @Query("token")
        token: String,
        @Query("socialmedia")
        socialMedia: String,
        @Query("email")
        emailId: String
    ): Deferred<Response<LoginResult>>


    @POST("api/v1/sign_up")
    fun callSignUpAsync(@Body signUpReq: SignUpReq): Deferred<Response<LoginRes>>

    @GET("api/v1/matches")
    fun callMatchesListingAsync(@Query ("event_type") event_type: String,@Query ("play_status") play_status: String): Deferred<Response<MatchListingRes>>


    @GET("api/v1/passwords/forgot")
    fun callForgetPasswordAsync(@QueryMap emailId: String): Deferred<Response<ForgetPassRes>>


    @GET("api/v1/matches/{matchId}/user_match_show.json?play_status=in_play&event_type=cricket&session_type=simultaneous_open_session")
    fun callMatchDetailsAsync(@Path("matchId") matchId: Int): Deferred<Response<MatchDetailsRes>>



    @GET("ballchalu/api/v1/contests")
    fun callMatchContestAsync(@Query("match_id") match_id: String): Deferred<Response<MatchContestRes>>

    @GET("ballchalu/api/v1/user_contests")
    fun callUserMatchContestAsync(@Query("match_id") match_id: String): Deferred<Response<UserMatchContestRes>>

    @POST("ballchalu/api/v1/contests/{match_id}/user_contests")
    fun callCretateContestAsync(@Path("match_id") match_id: String): Deferred<Response<CreateContestRes>>

    @POST("http://heroic.ballchalu.in/ballchalu/api/v1/user_contests/{contestsId}/bet_slips")
    fun callCretateBetAsync(@Body betReq: CreateBetReq,@Path("contestsId") contestsId: String): Deferred<Response<CreateBetRes>>

}