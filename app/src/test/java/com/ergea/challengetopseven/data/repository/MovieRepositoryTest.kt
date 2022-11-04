package com.ergea.challengetopseven.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ergea.challengetopseven.data.remote.movie.ApiServiceMovie
import com.ergea.challengetopseven.data.remote.movie.model.GetMovieDetailResponse
import com.ergea.challengetopseven.data.remote.movie.model.GetMoviePopularResponse
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import retrofit2.Response

class MovieRepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var api: ApiServiceMovie
    private lateinit var repository: MovieRepository

    @Before
    fun setUp() {
        api = mock()
        repository = MovieRepository(api)
    }

    @Test
    fun getMovieList() = runBlocking {
        val correct = mockk<Response<GetMoviePopularResponse>>()
        Mockito.`when`(api.getMovieList()).thenReturn(correct)
        val response = repository.getMovieList()
        assertEquals(correct, response)
    }

    @Test
    fun getMovieDetail() = runBlocking {
        val correct = mockk<Response<GetMovieDetailResponse>>()
        Mockito.`when`(api.getMovieDetail(any())).thenReturn(correct)
        val response = repository.getMovieDetail(32)
        assertEquals(correct, response)
    }
}