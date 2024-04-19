package com.maden.cleanrickandmortyandroid.di

import com.maden.cleanrickandmortyandroid.data.remote.HttpConstants.BASE_URL
import com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.RickAndMortyServiceApiInterface
import com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.service.RickAndMortyApiService
import com.maden.cleanrickandmortyandroid.data.remote.rick_and_morty.service.RickAndMortyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .build()

    @Singleton
    @Provides
    fun injectRetrofitApi(): RickAndMortyApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(RickAndMortyApiService::class.java)
    }

    @Singleton
    @Provides
    fun injectRickAndMortyRepository(api: RickAndMortyApiService): RickAndMortyServiceApiInterface {
        return RickAndMortyRepository(_apiService = api)
    }

}