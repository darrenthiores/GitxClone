package com.icebeal.gitxclone.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_favorite")
data class User(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("login")
    var username:String,
    @SerializedName("avatar_url")
    var avatar:String
)