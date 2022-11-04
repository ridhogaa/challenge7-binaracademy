package com.ergea.challengetopseven.data.repository

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ergea.challengetopseven.data.local.database.AppDatabase
import com.ergea.challengetopseven.data.local.database.movie.FavoriteMovieDao
import com.ergea.challengetopseven.data.local.database.movie.FavoriteMovieEntity
import com.ergea.challengetopseven.data.local.database.user.UserDao
import com.ergea.challengetopseven.data.local.database.user.UserEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val favoriteMovieDao: FavoriteMovieDao
) {
    suspend fun getAllUsers(): List<UserEntity> = userDao.getAllUsers()

    suspend fun insertUser(userEntity: UserEntity) =
        userDao.insertUser(userEntity)

    suspend fun getIfUserExist(username: String): Boolean =
        userDao.getIfUserExists(username)

    suspend fun updateUser(
        userId: Int,
        username: String,
        fullname: String,
        tanggalLahir: String,
        alamat: String,
        gambar: String
    ) = userDao.updateUser(userId, username, fullname, tanggalLahir, alamat, gambar)

    suspend fun getUserById(id: Int): UserEntity = userDao.getUserById(id)

    suspend fun getUserByEmail(email: String): UserEntity = userDao.getUserByEmail(email)

    fun validateUserLogin(email: String, password: String) =
        userDao.validateUserLogin(email, password)

    suspend fun getFavoriteMovies(id_user: Int) = favoriteMovieDao.getFavoriteMovies(id_user)

    suspend fun addFavorite(favoriteMovie: FavoriteMovieEntity) =
        favoriteMovieDao.addFavorite(favoriteMovie)

    suspend fun deleteFavorite(movieId: Int, usrId: Int) =
        favoriteMovieDao.deleteFavorite(movieId, usrId)

    suspend fun isFavoriteMovie(uniqueIdMovie: Int, id_user: Int) =
        favoriteMovieDao.isFavoriteMovie(uniqueIdMovie, id_user)
}