package com.zaincheema.turf.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zaincheema.turf.TurfApiService
import com.zaincheema.turf.model.TurfBox
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import kotlin.properties.Delegates

@SuppressLint("RestrictedApi")
class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity.kt"
    private lateinit var turfBoxes: List<TurfBox>
    private var compositeDisposable = CompositeDisposable()
    private val service = TurfApiService.retrofit.create<TurfApiService>()

    private val colorsHex = listOf(
        0xFF000000, // Black
        0xFF888888, // Grey
        0xFFFFFFFF, // White
        0xFFFF0000, // Red
        0xFF00FF00, // Green
        0xFF0000FF, // Blue
        0xFFFFFF00, // Yellow
        0xFF00FFFF, // Light Blue
    )

    private var selectedColorHex: Long? = null

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        compositeDisposable.add(
            service.getTurfBoxes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response -> onGetSuccess(response) }, { t -> onGetFailure(t) })
       )

        compositeDisposable.add(
            service.notifyDbChange()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { response -> onChangeDetected(response) }
        )

        supportActionBar?.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    @ExperimentalAnimationApi
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

    private fun onChangeDetected(response: TurfBox) {
        Log.d(TAG, "CHANGE DETECTED")
        Log.d(TAG, response.toString())
    }

    private fun handleBoxClick(tb: TurfBox) {
        if (selectedColorHex != null) {
        service.updateTurfBoxColor(tb.copy(id = tb.id, colorHex = selectedColorHex!!))
            .enqueue(object : Callback<TurfBox> {
                override fun onResponse(call: Call<TurfBox>, response: Response<TurfBox>) {
                    Toast.makeText(baseContext, "Color changed to $selectedColorHex", Toast.LENGTH_LONG).show()
                    selectedColorHex = null
                    Log.d(TAG, response.body().toString())
                }

                override fun onFailure(call: Call<TurfBox>, t: Throwable) {
                    Log.e(TAG, "Color change failed :(")
                    Log.d(TAG, t.toString())
                }
            })
        } else {
            Toast.makeText(baseContext, "Color hasn't been selected!", Toast.LENGTH_LONG).show()
        }
    }

    @ExperimentalAnimationApi
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun TurfMap() {
        var showTimer = remember { mutableStateOf(false) }
        Column {
            TopAppBar(title = { Text(text = "turf") }, backgroundColor = Color.White)
            LazyVerticalGrid(
                cells = GridCells.Fixed(10),
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(8.dp)
            ) {
                items(turfBoxes.size) { t ->
                    Box(
                        Modifier
                            .clickable(true, null, null) {
                                handleBoxClick(turfBoxes[t])
                            }
                            .aspectRatio(1f)
                            .background(Color(turfBoxes[t].colorHex))
                    )
                }
            }
            // BowWithConstraints will provide the maxWidth used below
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                // LazyRow to display your items horizontally
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    state = rememberLazyListState(),
                    contentPadding = PaddingValues(all = 1.dp)
                ) {
                    items(colorsHex) { item ->
                        Box(
                            modifier = Modifier
                                .scale(0.75f)
                                .aspectRatio(1f)
                                .background(Color(item))
                                .clickable {
                                    Toast.makeText(baseContext, "color selected: $item", Toast.LENGTH_LONG).show()
                                    selectedColorHex = item
                                }
                        ) {
                            //Text(item) // card's content
                        }
                    }
                }
            }
        }
    }
}


