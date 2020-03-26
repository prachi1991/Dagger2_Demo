package com.ccpp.shared.network

import com.ccpp.shared.domain.*
import com.ccpp.shared.domain.bccoins.BcCoinLedgersRes
import com.ccpp.shared.domain.bccoins.BcCoinRes
import com.ccpp.shared.domain.bccoins.buy.BcCoinBuyRes
import com.ccpp.shared.domain.contest.CreateContestRes
import com.ccpp.shared.domain.contest.MatchContestRes
import com.ccpp.shared.domain.contest.UserMatchContestRes
import com.ccpp.shared.domain.create_bet.CreateBetReq
import com.ccpp.shared.domain.create_bet.CreateBetRes
import com.ccpp.shared.domain.create_bet.CreateSessionBetReq
import com.ccpp.shared.domain.create_bet.CreateSessionBetRes
import com.ccpp.shared.domain.match_details.MatchDetailsRes
import com.ccpp.shared.domain.my_bets.MyBetsRes
import com.ccpp.shared.domain.position.PositionRes
import com.ccpp.shared.domain.user.UserRes
import com.ccpp.shared.domain.winner.WinnerRes
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
    fun callMatchesListingAsync(@Query("event_type") event_type: String, @Query("play_status") play_status: String): Deferred<Response<MatchListingRes>>


    @GET("api/v1/passwords/forgot")
    fun callForgetPasswordAsync(@QueryMap emailId: String): Deferred<Response<ForgetPassRes>>

    //Match detail screen api
    @GET("api/v1/matches/{providerId}/user_match_show.json?play_status=in_play&event_type=cricket&session_type=simultaneous_open_session")
    fun callMatchDetailsAsync(@Path("providerId") providerId: Int): Deferred<Response<MatchDetailsRes>>

    @GET("ballchalu/api/v1/user_contests/{contestId}/positions")
    fun callPositionDetailsAsync(@Path("contestId") contestId: Int): Deferred<Response<PositionRes>>


    //Contest screen api
    @GET("ballchalu/api/v1/contests")
    fun callMatchContestAsync(@Query("match_id") match_id: String): Deferred<Response<MatchContestRes>>

    @GET("ballchalu/api/v1/user_contests")
    fun callUserMatchContestAsync(@Query("match_id") match_id: String): Deferred<Response<UserMatchContestRes>>

    @POST("ballchalu/api/v1/contests/{match_id}/user_contests")
    fun callCreateContestAsync(@Path("match_id") match_id: String): Deferred<Response<CreateContestRes>>


    //Bet drawer screen api
    @POST("ballchalu/api/v1/user_contests/{contestsId}/bet_slips")
    fun callCreateBetAsync(@Body betReq: CreateBetReq, @Path("contestsId") contestsId: String): Deferred<Response<CreateBetRes>>

    @POST("ballchalu/api/v1/user_contests/{contestsId}/session_bet_slips")
    fun callCreateSessionBetAsync(@Body betReq: CreateSessionBetReq, @Path("contestsId") contestsId: String): Deferred<Response<CreateSessionBetRes>>

    @GET("ballchalu/api/v1/contests/{contestsId}/user_bets")
    fun callMyBetAsync(@Path("contestsId") contestsId: String): Deferred<Response<MyBetsRes>>

    @GET("api/v1/users")
    fun callUserAsync(): Deferred<Response<UserRes>>

    @GET("ballchalu/api/v1/bc_coins_ledgers")
    fun callBcCoinsLedgersAsync(@Query("page") page: Int): Deferred<Response<BcCoinLedgersRes>>

    @GET("ballchalu/api/v1/bc_coins_offers")
    fun callBcCoinsListAsync(): Deferred<Response<BcCoinRes>>

    @POST("ballchalu/api/v1/bc_coins_offers/{id}}/bc_coins_transactions")
    fun callBuyNowCoinsAsync(@Path("id") id: Int): Deferred<Response<BcCoinBuyRes>>

    @GET("ballchalu/api/v1/contests/{contestId}/top_runners")
    fun callWinnerListingAsync(
        @Path("contestId") contestId: Int, @Query("match_id") match_id: Int, @Query(
            "page"
        ) page: Int
    ): Deferred<Response<WinnerRes>>

}