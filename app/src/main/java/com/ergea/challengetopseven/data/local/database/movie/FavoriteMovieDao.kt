package com.ergea.challengetopseven.data.local.database.movie

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * from favorite_movie where id_user = :id_user")
    suspend fun getFavoriteMovies(id_user: Int): List<FavoriteMovieEntity>

    @Query("SELECT EXISTS(SELECT * FROM favorite_movie WHERE unique_id_movie = :uniqueIdMovie AND id_user = :id_user)")
    suspend fun isFavoriteMovie(uniqueIdMovie: Int, id_user: Int): Boolean

    @Insert
    suspend fun addFavorite(favoriteMovie: FavoriteMovieEntity)

    @Query("DELETE FROM favorite_movie WHERE unique_id_movie=:movieId AND id_user=:usrId")
    suspend fun deleteFavorite(movieId: Int, usrId: Int)
}