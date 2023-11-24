package com.bharath.dailyquotesapp.feature_quotes.di


import com.bharath.dailyquotesapp.feature_quotes.data.data_source.ApiCons
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.QuotesApi
import com.bharath.dailyquotesapp.feature_quotes.data.repository.RepositoryImpl
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {


    @Provides
    @Singleton
    fun provideQuotesApi(): QuotesApi {
        return Retrofit.Builder()
            .baseUrl(ApiCons.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuotesApi::class.java)
    }


    @Provides
    @Singleton
    fun provideNetworkRepository(api: QuotesApi): Repository {
        return RepositoryImpl(api)
    }
}