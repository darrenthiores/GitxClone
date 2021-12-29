package com.icebeal.gitxclone.data.model

import com.google.gson.annotations.SerializedName


data class Search(
    @SerializedName("items")
    var search:ArrayList<User>
)