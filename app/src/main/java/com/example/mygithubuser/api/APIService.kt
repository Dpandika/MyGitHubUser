package com.example.mygithubuser.api

import com.example.mygithubuser.data.DetailUserResponse
import com.example.mygithubuser.data.User
import com.example.mygithubuser.data.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val token = "Authorization: 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"

interface Api {
    @GET("search/users")
    @Headers(token)
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers(token)
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers(token)
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers(token)
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}
