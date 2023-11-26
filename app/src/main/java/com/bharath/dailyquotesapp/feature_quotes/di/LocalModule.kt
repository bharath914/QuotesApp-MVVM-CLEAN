package com.bharath.dailyquotesapp.feature_quotes.di


import android.app.Application
import android.content.Context
import androidx.room.Room
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.QuoteDataBase
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.repository.LocalRepoImpl
import com.bharath.dailyquotesapp.feature_quotes.domain.datastore.DataStoreRepository
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.LocalRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {


    @Provides
    @Singleton
    fun provideRoomDatabase(app: Application): QuoteDataBase {
        return Room.databaseBuilder(
            app,
            QuoteDataBase::class.java,
            "QuoteDatabase"
        ).build()
    }


    @Provides
    @Singleton
    fun provideLocalRepo(dataBase: QuoteDataBase): LocalRepo {
        return LocalRepoImpl(dataBase.dao)
    }


    @Provides
    @Singleton
    fun provideDataStoreRepo(@ApplicationContext context: Context) = DataStoreRepository(context)
}