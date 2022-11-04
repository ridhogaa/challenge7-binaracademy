package com.ergea.challengetopseven.data.local.database.user

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * from user")
    suspend fun getAllUsers(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username)")
    suspend fun getIfUserExists(username: String): Boolean

    @Query("SELECT * FROM user WHERE id_user = :id")
    suspend fun getUserById(id: Int): UserEntity

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    fun validateUserLogin(email: String, password: String): UserEntity?

    @Query("UPDATE user SET username=:username, nama_lengkap=:fullname, tanggal_lahir=:tanggalLahir, alamat=:alamat, gambar=:gambar WHERE id_user=:userId")
    suspend fun updateUser(
        userId: Int,
        username: String,
        fullname: String,
        tanggalLahir: String,
        alamat: String,
        gambar: String
    )
}