package com.zaincheema.turf

import com.squareup.moshi.Json
import com.zaincheema.turf.model.TurfBox
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface TurfApiService {
    @GET("/api/v1/boxes/")
    fun getTurfBoxes(): Observable<List<TurfBox>>

    @PUT("/api/v1/boxes/{id}")
    fun updateTurfBoxColor(@Path("id") id: Int, @Body tb: TurfBox): Observable<ResponseBody>

    companion object {
       private const val API_URL = "https://turf-api-nzzq3nbe4a-uc.a.run.app/"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
}