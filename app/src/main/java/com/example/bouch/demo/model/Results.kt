package com.example.bouch.demo.model

import com.google.gson.annotations.SerializedName

/**
 * Created by bouch on 28/1/18.
 */
data class Results(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("poster_path") val posterPath: String
)