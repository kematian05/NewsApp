package com.example.newsapp.data.responses


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Source(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)