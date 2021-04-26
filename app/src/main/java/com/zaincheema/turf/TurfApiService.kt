package com.zaincheema.turf

import com.zaincheema.turf.model.TurfBox
import okhttp3.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TurfApiService {
    @GET("/api/v1/boxes/all")
    fun getTurfBoxes(@Query("turf_boxes") turfBoxes: List<TurfBox>): Call

    companion object {
        const val API_URL = "https://turf-api-nzzq3nbe4a-uc.a.run.app/"
    }
}