package com.zaincheema.turf.respository

import com.zaincheema.turf.api.TurfApiService
import com.zaincheema.turf.model.Box
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

// Where the impl for api calls and responses are held, called from the viewmodel
class BoxesRepository @Inject constructor(private val turfApiService: TurfApiService) {
    // TODO: Dispose of compositeDisposable when the activity is destroyed
    private var selectedColorHex: Long? = null
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

    fun getBoxesList(): Observable<List<Box>> {
        return turfApiService.getBoxes()
    }

    fun handleBoxClick(tb: Box) {
        if (selectedColorHex != null) {
            turfApiService.updateBoxColor(tb.copy(id = tb.id, colorHex = selectedColorHex!!))
                .enqueue(object : Callback<Box> {
                    override fun onResponse(call: Call<Box>, response: Response<Box>) {
                        selectedColorHex = null
                        // TODO: Add logging functionality to replace LOG
                       // Log.d(TAG, "Color changed to $selectedColorHex")
                    }

                    override fun onFailure(call: Call<Box>, t: Throwable) {
//                        Log.e(TAG, "Color change failed :(")
//                        Log.d(TAG, t.toString())
                    }
                })
        } else {
            // TODO: Devise a new way to display Toast type info to the user
//            Toast.makeText(baseContext, "Color hasn't been selected!", Toast.LENGTH_LONG).show()
        }
    }

    fun setSelectedColor(colorHex: Long) {
        selectedColorHex = colorHex
    }


    fun getColorsList(): List<Long> {
        return colorsHex
    }

}