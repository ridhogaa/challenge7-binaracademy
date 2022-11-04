package com.ergea.challengetopseven.data.remote.movie

import com.ergea.challengetopseven.data.remote.movie.model.GetMovieDetailResponse
import com.ergea.challengetopseven.data.remote.movie.model.GetMoviePopularResponse
import com.ergea.challengetopseven.data.remote.movie.model.ResultMovies
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiServiceMovie {
    @GET("/3/movie/popular?api_key=9428967aca5607f7a2bbcb7a46f0ecfe")
    suspend fun getMovieList(): Response<GetMoviePopularResponse>

    @GET("/3/movie/{id}?api_key=9428967aca5607f7a2bbcb7a46f0ecfe")
    suspend fun getMovieDetail(@Path("id") id: Int): Response<GetMovieDetailResponse>
}