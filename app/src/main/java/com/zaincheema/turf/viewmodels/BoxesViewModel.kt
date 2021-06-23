package com.zaincheema.turf.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaincheema.turf.model.Box
import com.zaincheema.turf.respository.BoxesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

// This ViewModel is in charge of updating UI in MainActivity.kt
@HiltViewModel
class BoxesViewModel @Inject constructor(
   private val repository: BoxesRepository
) : ViewModel() {
        private val TAG : String = "BoxesViewModel.kt"
        val boxes = MutableLiveData<List<Box>>()
        private val compositeDisposable = CompositeDisposable()

    fun getBoxesList() {

        val disposable = io.reactivex.rxjava3.core.Observable.interval(0, 1, TimeUnit.SECONDS)
            .timeInterval()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                repository.getBoxesList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ response -> boxes.value = response
                               Log.d(TAG, boxes.value.toString())}, { t -> Log.e(TAG, t.toString()) })
            }
        compositeDisposable.add(disposable)
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