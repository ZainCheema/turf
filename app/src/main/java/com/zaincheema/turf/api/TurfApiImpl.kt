package com.zaincheema.turf.api

import com.zaincheema.turf.model.Box
import javax.inject.Inject

class TurfApiImpl @Inject constructor(private val turfApiService: TurfApiService) : TurfApiHelper {
    override fun getBoxes() = turfApiService.getBoxes()

    override fun updateBoxColor(tb: Box) = turfApiService.updateBoxColor(tb)
}