package com.example.testapp.di

import com.example.testapp.utils.CONSTANTS
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    @Named("BooksRetrofit")
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(CONSTANTS.API_BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    @Named("CountryRetrofit")
    fun provideRetrofitForCountry(
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(CONSTANTS.IP_COUNTRY_BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    @Named("CountryMapRetrofit")
    fun provideRetrofitForCountryMap(
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(CONSTANTS.COUNTRY_CODE_BASE_URL)
            .build()
    }


    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
        )
    }

}