package com.icebeal.gitxclone.data.model

import com.google.gson.annotations.SerializedName

data class UserRepos(

    @SerializedName("name")
    var repos_name : String,
    @SerializedName("description")
    var description : String,
    @SerializedName("fork")
    var fork : Boolean,
    @SerializedName("stargazers_count")
    var star:Int,
    @SerializedName("watchers_count")
    var watch:Int,
    @SerializedName("language")
    var language:String,
    @SerializedName("html_url")
    var url:String

)