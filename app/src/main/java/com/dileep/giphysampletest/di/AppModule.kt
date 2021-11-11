package com.dileep.giphysampletest.di

import android.app.Application
import com.dileep.giphysampletest.api.GippyApiService
import com.dileep.giphysampletest.data.source.local.GiphyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.giphy.com/v1/gifs/"
    @Singleton
    @Provides
    fun provideGiphyApi(): GippyApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GippyApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application) = GiphyDatabase.invoke(app)

    @Singleton
    @Provides
    fun provideGifDao(db: GiphyDatabase) = db.gifDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(db: GiphyDatabase) = db.remoteKeysDao()
}