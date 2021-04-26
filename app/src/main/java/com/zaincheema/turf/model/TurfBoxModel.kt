package com.zaincheema.turf.model

import androidx.compose.ui.graphics.Color
import com.squareup.moshi.Json

data class TurfBox(
    @field: Json(name = "id" ) val id: Int,
    @field: Json(name = "color") val color: String)