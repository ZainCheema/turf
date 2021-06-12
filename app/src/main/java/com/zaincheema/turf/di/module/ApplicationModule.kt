package com.zaincheema.turf.di.module
import com.zaincheema.turf.BuildConfig
import com.zaincheema.turf.api.TurfApiHelper
import com.zaincheema.turf.api.TurfApiImpl
import com.zaincheema.turf.api.TurfApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create<TurfApiService>()

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: TurfApiImpl): TurfApiHelper = apiHelper

}