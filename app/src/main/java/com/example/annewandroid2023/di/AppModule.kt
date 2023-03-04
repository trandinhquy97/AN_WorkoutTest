package com.example.annewandroid2023.di

import android.content.Context
import androidx.room.Room
import com.example.annewandroid2023.SchedulerProvider
import com.example.annewandroid2023.common.Constants
import com.example.annewandroid2023.data.local.AppDatabase
import com.example.annewandroid2023.data.remote.AppApi
import com.example.annewandroid2023.data.repository.MainRepositoryImpl
import com.example.annewandroid2023.domain.app.SchedulerProviderInterface
import com.example.annewandroid2023.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideSchedulerProviderInterface(): SchedulerProviderInterface {
        return SchedulerProvider
    }

    @Provides
    @Singleton
    fun providePaprikaApi(): AppApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppApi::class.java)
    }

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "workout_db"
    ).build()

    @Provides
    @Singleton
    fun provideCoinRepository(api: AppApi, appDatabase: AppDatabase): MainRepository {
        return MainRepositoryImpl(api, appDatabase)
    }
}