package com.ergea.challengetopseven.data.local.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ergea.challengetopseven.data.local.database.movie.FavoriteMovieDao
import com.ergea.challengetopseven.data.local.database.movie.FavoriteMovieEntity
import com.ergea.challengetopseven.data.local.database.user.UserDao
import com.ergea.challengetopseven.data.local.database.user.UserEntity


@Database(entities = [UserEntity::class, FavoriteMovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    abstract fun userDao(): UserDao

    companion object {
        private var dbINSTANCE: AppDatabase? = null
        fun getAppDB(context: Context): AppDatabase {
            if (dbINSTANCE == null) {
                dbINSTANCE = Room.databaseBuilder<AppDatabase>(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "MYDB.db"
                ).allowMainThreadQueries().build()
            }
            return dbINSTANCE!!
        }
    }
}
