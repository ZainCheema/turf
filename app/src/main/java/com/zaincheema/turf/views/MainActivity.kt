package com.zaincheema.turf.views

import android.annotation.SuppressLint
import android.os.AsyncTask.execute
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zaincheema.turf.TurfApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.Callback
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@SuppressLint("RestrictedApi")
class MainActivity : AppCompatActivity() {
    val TAG: String = "MainActivity.kt"
    // https://www.pushing-pixels.org/2019/12/04/working-with-retrofit-and-moshi-in-kotlin.html
    // https://github.com/roharon/retrofit2-kotlin-example/blob/master/app/src/main/java/com/example/retrofit2_kotlin/MainActivity.kt
    // https://code.tutsplus.com/tutorials/connect-to-an-api-with-retrofit-rxjava-2-and-kotlin--cms-32133
    // https://dev.to/paulodhiambo/kotlin-rxjava-retrofit-tutorial-18hn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TurfMap()
        }

        // https://medium.com/mobile-app-development-publication/rxjava-2-making-threading-easy-in-android-in-kotlin-603d8342d6c
        // RxJava schedulers:
        // https://stackoverflow.com/questions/33370339/what-is-the-difference-between-schedulers-io-and-schedulers-computation

        // Moshi
        // https://proandroiddev.com/getting-started-using-moshi-for-json-parsing-with-kotlin-5a460bf3935a

        val service = TurfApiService.retrofit.create<TurfApiService>()

        val compositeDisposable = CompositeDisposable()

        compositeDisposable.add(
            service.checkEndpoint()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> onSuccess(response) }, { t -> onFailure(t) })
        )

        compositeDisposable.add(
            service.getTurfBoxes()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> onSuccess(response.toString()) }, { t -> onFailure(t) })
        )

    }


    private fun onSuccess(response: String) {
      //  Log.d(TAG, "Connected to API!")
        Log.d(TAG, response)
    }

    private fun onFailure(t: Throwable) {
    //    Log.d(TAG, "Failed to connect to API :(")
        Log.d(TAG, t.toString())
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