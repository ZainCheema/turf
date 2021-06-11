package com.zaincheema.turf.respository

import com.zaincheema.turf.api.TurfApiHelper
import javax.inject.Inject

// Where the impl for api calls and responses are held, called from the viewmodel
// https://blog.mindorks.com/dagger-hilt-tutorial
class BoxesRepository @Inject constructor(private val turfApiHelper: TurfApiHelper) {
    fun getBoxes() = turfApiHelper.getBoxes()
}