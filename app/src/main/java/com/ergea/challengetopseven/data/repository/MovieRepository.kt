package com.ergea.challengetopseven.data.repository

import androidx.lifecycle.MutableLiveData
import com.ergea.challengetopseven.data.remote.movie.ApiServiceMovie
import com.ergea.challengetopseven.data.remote.movie.model.GetMovieDetailResponse
import com.ergea.challengetopseven.data.remote.movie.model.GetMoviePopularResponse
import com.ergea.challengetopseven.data.remote.movie.model.ResultMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiServiceMovie: ApiServiceMovie
) {
    suspend fun getMovieList() = apiServiceMovie.getMovieList()
    suspend fun getMovieDetail(id: Int) = apiServiceMovie.getMovieDetail(id)
}