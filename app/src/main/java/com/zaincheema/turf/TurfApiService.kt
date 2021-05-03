package com.zaincheema.turf

import com.zaincheema.turf.model.TurfBox
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TurfApiService {
    @GET("/api/v1/boxes/all")
    fun getTurfBoxes(): Call<List<TurfBox>>

    @GET("/")
    fun checkEndpoint(): Observable<String>


    companion object {
       const val API_URL = "https://turf-api-nzzq3nbe4a-uc.a.run.app/"
       //const val API_URL = "http://127.0.0.1:5000/"
    }
}