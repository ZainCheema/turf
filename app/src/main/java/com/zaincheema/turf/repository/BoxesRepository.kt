package com.zaincheema.turf.repository

import com.zaincheema.turf.api.TurfApiService
import com.zaincheema.turf.model.Box
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import javax.inject.Inject

// Where the impl for api calls and responses are held, called from the viewmodel
class BoxesRepository @Inject constructor(private val turfApiService: TurfApiService) {
    // TODO: Dispose of compositeDisposable when the activity is destroyed

    fun getBoxesList(): Observable<List<Box>> {
        return turfApiService.getBoxes()
    }

    fun updateBoxColor(tb: Box, selectedColorHex: Long): Call<Box> {
            return turfApiService.updateBoxColor(tb.copy(id = tb.id, colorHex = selectedColorHex!!))
        }
    }
