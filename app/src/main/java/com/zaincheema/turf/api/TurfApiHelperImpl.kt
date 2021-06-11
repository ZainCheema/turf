package com.zaincheema.turf.api

import javax.inject.Inject

class TurfApiImpl @Inject constructor(private val turfApiService: TurfApiService) {
    fun getBoxes() = turfApiService.getBoxes()
}