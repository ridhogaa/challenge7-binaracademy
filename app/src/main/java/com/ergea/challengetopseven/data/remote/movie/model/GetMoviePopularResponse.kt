package com.ergea.challengetopseven.data.remote.movie.model


import com.google.gson.annotations.SerializedName

data class GetMoviePopularResponse(
    @SerializedName("results")
    val results: List<ResultMovies>,
    @SerializedName("status_message")
    val statusMessage: String? = null,
    @SerializedName("status_code")
    val statusCode: Int? = null,
    @SerializedName("success")
    val success: Boolean? = null

)