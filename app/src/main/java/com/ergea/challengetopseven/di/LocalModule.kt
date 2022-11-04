package com.ergea.challengetopseven.di

import android.content.Context
import androidx.room.Room
import com.ergea.challengetopseven.data.local.database.AppDatabase
import com.ergea.challengetopseven.data.local.database.movie.FavoriteMovieDao
import com.ergea.challengetopseven.data.local.database.user.UserDao
import com.ergea.challengetopseven.data.local.datastore.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ExecutorService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getAppDB(context)

    @Singleton
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()

    @Singleton
    @Provides
    fun provideFavoriteMovieDao(database: AppDatabase): FavoriteMovieDao =
        database.favoriteMovieDao()

    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(context)

}