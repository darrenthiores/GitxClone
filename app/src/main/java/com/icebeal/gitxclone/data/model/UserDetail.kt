package com.icebeal.gitxclone.data.model

import com.google.gson.annotations.SerializedName

data class UserDetail(
    @SerializedName("name")
    var name:String,
    @SerializedName("login")
    var username:String,
    @SerializedName("avatar_url")
    var avatar:String,
    @SerializedName("bio")
    var bio:String,
    @SerializedName("followers")
    var followers:Int,
    @SerializedName("following")
    var following:Int,
    @SerializedName("public_repos")
    var repos:Int,
    @SerializedName("company")
    var company:String,
    @SerializedName("location")
    var location:String,
    @SerializedName("email")
    var email:String
)