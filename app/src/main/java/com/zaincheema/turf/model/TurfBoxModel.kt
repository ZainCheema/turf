package com.zaincheema.turf.model

import com.squareup.moshi.Json

data class TurfBox(
    @field: Json(name = "id") val id: Int,
    @field: Json(name = "color") val color: String)

data class TurfBoxes(
    @field:Json(name = "turf_boxes") val turfBoxes: List<TurfBox>)
