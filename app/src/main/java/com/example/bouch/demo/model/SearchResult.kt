package com.example.bouch.demo.model

import com.google.gson.annotations.SerializedName

/**
 * Created by bouch on 28/1/18.
 */
data class SearchResult(
        @SerializedName("page") val page: Int,
        @SerializedName("total_results") val totalResults: Int,
        @SerializedName("total_pages") val totalPages: Int,
        @SerializedName("results") val results: List<Results>
)