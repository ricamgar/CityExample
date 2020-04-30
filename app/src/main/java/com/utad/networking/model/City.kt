package com.utad.networking.model

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("woeid")
    val id: Int,
    val title: String
)