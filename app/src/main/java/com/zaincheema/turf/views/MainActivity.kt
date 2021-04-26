package com.zaincheema.turf.views

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.squareup.moshi.Moshi
import com.zaincheema.turf.TurfApiService
import com.zaincheema.turf.model.TurfBox
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@SuppressLint("RestrictedApi")
class MainActivity : AppCompatActivity() {

    // https://www.pushing-pixels.org/2019/12/04/working-with-retrofit-and-moshi-in-kotlin.html
    // https://github.com/roharon/retrofit2-kotlin-example/blob/master/app/src/main/java/com/example/retrofit2_kotlin/MainActivity.kt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TurfMap()
        }

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(TurfApiService.API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val service = retrofit.create<TurfApiService>()
        val call = service.getTurfBoxes()
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun TurfMap() {
        LazyVerticalGrid(
            cells = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(8.dp)
        ) {
            items(count = 9) {
                Box(
                    Modifier
                        .aspectRatio(1f)
                        .padding(3.dp)
                        .background(Color.DarkGray)
                )
            }
        }
    }
}