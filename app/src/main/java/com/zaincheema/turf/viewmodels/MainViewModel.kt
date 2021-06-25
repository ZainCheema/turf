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

    fun startTimer() {
        _countdownOngoing.value = true
        countdownTimer = object : CountDownTimer(CountdownUtil.COUNTDOWN, 1000) {
            override fun onTick(p0: Long) {
                TODO("Not yet implemented")
            }

            override fun onFinish() {
                _countdownOngoing.value = false
            }

        }.start()
    }


    fun getColorsList(): List<Long> {
        return repository.getColorsList()
    }

    fun setSelectedColor(color: Long) {
        repository.setSelectedColor(color)
    }

    fun handleBoxClick(tb: Box) {
        repository.handleBoxClick(tb)
    }

    fun removeCompositeDisposable() {
        compositeDisposable.dispose()
    }

}