package com.zaincheema.turf.api

import com.zaincheema.turf.model.Box
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.*

interface TurfApiService {
    @GET("/api/v1/boxes/")
    fun getBoxes(): Observable<List<Box>>

    @POST("/api/v1/boxes/")
    fun updateBoxColor(@Body tb: Box): Call<Box>
}