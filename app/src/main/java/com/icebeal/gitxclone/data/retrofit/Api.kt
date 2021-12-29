package com.icebeal.gitxclone.data.retrofit

import com.icebeal.gitxclone.BuildConfig
import com.icebeal.gitxclone.data.model.Search
import com.icebeal.gitxclone.data.model.User
import com.icebeal.gitxclone.data.model.UserDetail
import com.icebeal.gitxclone.data.model.UserRepos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("search/users")
    fun getUser(@Query("q", ) username:String, @Header("Authorization") authorization : String = "token ${BuildConfig.API_KEY}") : Call<Search>

    @GET("users/{username}")
    fun getDetail(@Path("username") username: String, @Header("Authorization") authorization : String = "token ${BuildConfig.API_KEY}") : Call<UserDetail>

    @GET("users/{username}/followers")
    fun getFollower(@Path("username") username: String, @Header("Authorization") authorization : String = "token ${BuildConfig.API_KEY}") : Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String, @Header("Authorization") authorization : String = "token ${BuildConfig.API_KEY}") : Call<ArrayList<User>>

    @GET("users/{username}/repos")
    fun getRepos(@Path("username") username: String, @Header("Authorization") authorization : String = "token ${BuildConfig.API_KEY}") : Call<ArrayList<UserRepos>>

}