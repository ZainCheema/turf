package com.zaincheema.turf.viewmodels

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaincheema.turf.model.Box
import com.zaincheema.turf.repository.BoxesRepository
import com.zaincheema.turf.utils.CountdownUtil
import com.zaincheema.turf.utils.CountdownUtil.formatTime
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

// This ViewModel is in charge of updating UI in MainActivity.kt
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BoxesRepository
) : ViewModel() {

    private val TAG: String = "BoxesViewModel.kt"
    private val compositeDisposable = CompositeDisposable()

    private val _boxes = MutableLiveData<List<Box>>()
    val boxes: LiveData<List<Box>> = _boxes

    private var countdownTimer: CountDownTimer? = null

    private val _countdownOngoing: MutableLiveData<Boolean> = MutableLiveData(false)
    val countdownOngoing: LiveData<Boolean> = _countdownOngoing

    private val _time = MutableLiveData(CountdownUtil.COUNTDOWN.formatTime())
    val time: LiveData<String> = _time


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

    init {
        countdownTimer = object : CountDownTimer(CountdownUtil.COUNTDOWN, 1000) {
            override fun onTick(timeRemaining: Long) {
                _time.value = timeRemaining.formatTime()
            }

            override fun onFinish() {
                _countdownOngoing.value = false
            }

        }
    }

    fun getBoxesFromService() {
        val disposable = io.reactivex.rxjava3.core.Observable.interval(0, 1, TimeUnit.SECONDS)
            .timeInterval()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                repository.getBoxesList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ response ->
                        _boxes.value = response
                        Log.d(TAG, boxes.value.toString())
                    }, { t -> Log.e(TAG, t.toString()) })
            }
        compositeDisposable.add(disposable)
    }

    fun handleBoxClick(tb: Box) {
        if (selectedColorHex != null) {
            repository.updateBoxColor(tb, selectedColorHex!!)
                .enqueue(object : Callback<Box> {
                    override fun onResponse(call: Call<Box>, response: Response<Box>) {
                        // The color change has been successfully posted,
                        // so now the countdown can begin
                        selectedColorHex = null
                        startCountdown()
                    }

                    override fun onFailure(call: Call<Box>, t: Throwable) {
                        // TODO: Display error message
                    }
                })
        }
    }

    fun getColorsList(): List<Long> {
        return colorsHex
    }

    fun selectColor(color: Long) {
        selectedColorHex = color
    }

    fun deselectColor() {
        selectedColorHex = null
    }

    fun startCountdown() {
        _countdownOngoing.value = true
        countdownTimer!!.start()
    }

    fun removeCompositeDisposable() {
        compositeDisposable.dispose()
    }

}
