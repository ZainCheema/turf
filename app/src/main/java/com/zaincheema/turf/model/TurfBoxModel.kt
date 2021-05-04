package com.zaincheema.turf.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TurfBox(
    @field: Json(name = "id") val id: Int,
    @field: Json(name = "color") val colorHex: Long
)

