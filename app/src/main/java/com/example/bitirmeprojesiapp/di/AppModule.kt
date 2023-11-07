package com.example.bitirmeprojesiapp.di

import com.example.bitirmeprojesiapp.data.datasource.YemeklerDataSource
import com.example.bitirmeprojesiapp.data.repo.YemeklerRepository
import com.example.bitirmeprojesiapp.retrofit.ApiUtils
import com.example.bitirmeprojesiapp.retrofit.YemeklerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton

    fun provideYemeklerDataSource(ydao:YemeklerDao) : YemeklerDataSource {
        return YemeklerDataSource(ydao)
    }

    @Provides
    @Singleton

    fun provideYemeklerRepository(yds:YemeklerDataSource) : YemeklerRepository {
        return YemeklerRepository(yds)
    }

    @Provides
    @Singleton

    fun provideYemeklerDao() : YemeklerDao {
        return ApiUtils.getYemeklerDao()
    }
}