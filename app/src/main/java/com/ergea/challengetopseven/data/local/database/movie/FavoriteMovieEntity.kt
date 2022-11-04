package com.ergea.challengetopseven.data.local.database.movie

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite_movie")
class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id_movie")
    var id_movie: Int? = null,
    @field:ColumnInfo(name = "unique_id_movie")
    var uniqueIdMovie: Int? = null,
    @field:ColumnInfo(name = "title")
    var title: String? = null,
    @field:ColumnInfo(name = "release_date")
    var releaseDate: String? = null,
    @field:ColumnInfo(name = "poster_path")
    var posterPath: String? = null,
    @field:ColumnInfo(name = "id_user")
    var id_user: Int? = null
) : Parcelable