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
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

@SuppressLint("RestrictedApi")
class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity.kt"
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
    private var compositeDisposable = CompositeDisposable()

    private val service = TurfApiService.retrofit.create<TurfApiService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        compositeDisposable.add(
            service.getTurfBoxes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response -> onGetSuccess(response) }, { t -> onGetFailure(t) })
        )

        supportActionBar?.hide()

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun onGetSuccess(response: List<TurfBox>) {
        Log.d(TAG, "Connected to API, loaded in Turf Boxes")
        Log.d(TAG, response.toString())

        turfBoxes = response

        setContent {
            TurfMap()
        }
    }

    private fun onGetFailure(t: Throwable) {
        Log.e(TAG, "Failed to connect to API :(")
        Log.d(TAG, t.toString())
    }

    private fun handleBoxClick(tb: TurfBox) {
        Toast.makeText(this, "ayo im box ${tb.id}", Toast.LENGTH_LONG).show()

        service.updateTurfBoxColor(tb).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d(TAG, response.toString())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(TAG, "Color change failed :(")
                Log.d(TAG, t.toString())
            }

        })
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
                            .clickable(true, null, null) { handleBoxClick(turfBoxes[t]) }
                            .aspectRatio(1f)
                            .background(Color(turfBoxes[t].colorHex))
                    )
                }
            }
        }
    }
}
