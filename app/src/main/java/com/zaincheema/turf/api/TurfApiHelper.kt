package com.zaincheema.turf.api

import com.zaincheema.turf.model.Box
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.Body

interface TurfApiHelper {
    fun getBoxes(): Observable<List<Box>>

    fun updateBoxColor(@Body tb: Box): Call<Box>
}
