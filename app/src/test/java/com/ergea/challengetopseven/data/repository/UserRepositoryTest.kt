package com.ergea.challengetopseven.data.repository

import com.ergea.challengetopseven.data.local.database.movie.FavoriteMovieDao
import com.ergea.challengetopseven.data.local.database.user.UserDao
import com.ergea.challengetopseven.data.local.database.user.UserEntity
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

class UserRepositoryTest {

    private lateinit var userDao: UserDao
    private lateinit var repository: UserRepository
    private lateinit var favoriteMovieDao: FavoriteMovieDao

    @Before
    fun setUp() {
        userDao = mock()
        favoriteMovieDao = mock()
        repository = UserRepository(userDao, favoriteMovieDao)
    }

    @Test
    fun validateUserLogin() = runBlocking {
        val correct = mockk<UserEntity>()
        Mockito.`when`(userDao.validateUserLogin(any(), any())).thenReturn(correct)
        val response = repository.validateUserLogin("1", "1")
        assertEquals(response, correct)
    }

    @Test
    fun getUserById() = runBlocking {
        val correct = mockk<UserEntity>()
        Mockito.`when`(userDao.getUserById(any())).thenReturn(correct)
        val response = repository.getUserById(any())
        assertEquals(response, correct)
    }

    @Test
    fun getUserByEmail() = runBlocking {
        val correct = mockk<UserEntity>()
        Mockito.`when`(userDao.getUserByEmail(any())).thenReturn(correct)
        val response = repository.getUserByEmail("1")
        assertEquals(response, correct)
    }
}