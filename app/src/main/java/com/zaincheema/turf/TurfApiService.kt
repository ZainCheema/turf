package com.zaincheema.turf

import com.zaincheema.turf.model.TurfBox
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TurfApiService {
    @GET("/api/v1/boxes/all")
    fun getTurfBoxes(): Call<List<TurfBox>>

    @GET("/")
    fun checkEndpoint(): Observable<String>


    companion object {
       private const val API_URL = "https://turf-api-nzzq3nbe4a-uc.a.run.app/"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
}