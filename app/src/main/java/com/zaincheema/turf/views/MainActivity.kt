package com.zaincheema.turf.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zaincheema.turf.TurfApiService
import com.zaincheema.turf.model.TurfBox
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.create

@SuppressLint("RestrictedApi")
class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity.kt"

    // https://www.pushing-pixels.org/2019/12/04/working-with-retrofit-and-moshi-in-kotlin.html
    // https://github.com/roharon/retrofit2-kotlin-example/blob/master/app/src/main/java/com/example/retrofit2_kotlin/MainActivity.kt
    // https://code.tutsplus.com/tutorials/connect-to-an-api-with-retrofit-rxjava-2-and-kotlin--cms-32133
    // https://dev.to/paulodhiambo/kotlin-rxjava-retrofit-tutorial-18hn
    private lateinit var turfBoxes: List<TurfBox>

    private val colors: List<Color> = listOf(
        Color(0xFF000000),
        Color(0xFF888888),
        Color(0xFFFFFFFF),
        Color(0xFFFF0000),
        Color(0xFF00FF00),
        Color(0xFF0000FF),
        Color(0xFFFFFF00),
        Color(0xFF00FFFF),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // https://medium.com/mobile-app-development-publication/rxjava-2-making-threading-easy-in-android-in-kotlin-603d8342d6c
        // RxJava schedulers:
        // https://stackoverflow.com/questions/33370339/what-is-the-difference-between-schedulers-io-and-schedulers-computation

        // Moshi
        // https://proandroiddev.com/getting-started-using-moshi-for-json-parsing-with-kotlin-5a460bf3935a

        // RxJava UI Thread
        // https://stackoverflow.com/questions/60688724/android-rxjava-update-ui-from-background-thread-using-observable

        val service = TurfApiService.retrofit.create<TurfApiService>()

        val compositeDisposable = CompositeDisposable()

        compositeDisposable.add(
            service.getTurfBoxes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response -> onSuccess(response) }, { t -> onFailure(t) })
        )

        supportActionBar?.hide()

    }


    private fun onSuccess(response: List<TurfBox>) {
        Log.d(TAG, "Connected to API, loaded in Turf Boxes")
        Log.d(TAG, response.toString())

        turfBoxes = response

        setContent {
            TurfMap()
        }
    }

    private fun onFailure(t: Throwable) {
        Log.e(TAG, "Failed to connect to API :(")
        Log.d(TAG, t.toString())
    }

    private fun handleBoxClick(turf_box_id: Int) {
        Toast.makeText(this, "ayo im box $turf_box_id", Toast.LENGTH_LONG).show()
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun TurfMap() {
        Column() {
            TopAppBar(title = { Text(text = "turf") }, backgroundColor = Color.White)
            LazyVerticalGrid(
                cells = GridCells.Fixed(10),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(8.dp)
            ) {
                items(turfBoxes.size) { t ->
                    Box(
                        Modifier
                            .clickable(true, null, null) { handleBoxClick(turfBoxes[t].id) }
                            .aspectRatio(1f)
                            .background(Color(turfBoxes[t].colorHex))
                    )
                }
            }
            //ScrollableR
        }
    }
}