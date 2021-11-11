package com.dileep.giphysampletest.di

import com.dileep.giphysampletest.data.repo.GiphyRepository
import com.dileep.giphysampletest.data.repo.GiphyRepositoryImpl
import com.dileep.giphysampletest.data.source.local.LocalDataSource
import com.dileep.giphysampletest.data.source.local.LocalDataSourceImpl
import com.dileep.giphysampletest.data.source.remote.RemoteDataSource
import com.dileep.giphysampletest.data.source.remote.model.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindGiphyRepository(
        giphyRepositoryImpl: GiphyRepositoryImpl
    ): GiphyRepository

    @Binds
    abstract fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    abstract fun bindLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource

}