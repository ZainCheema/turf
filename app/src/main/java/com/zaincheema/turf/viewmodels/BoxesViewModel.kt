package com.zaincheema.turf.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.zaincheema.turf.model.Box
import com.zaincheema.turf.respository.BoxesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

// This ViewModel is in charge of updating UI in MainActivity.kt
@HiltViewModel
class BoxesViewModel @Inject constructor(
    _repository: BoxesRepository
) : ViewModel() {
        private val _boxes = MutableLiveData<List<Box>>()
        private val repository: BoxesRepository = _repository

    init {
        repository.updateBoxesList()
        _boxes.value = repository.boxes
    }

    fun getBoxesList(): MutableLiveData<List<Box>> {
        return _boxes
    }

    fun getColorsList(): List<Long> {
        return repository.getColorsList()
    }

    fun setSelectedColor(color: Long) {
        repository.setSelectedColor(color)
    }

    fun getSelectedColor() {
        repository.getSelectedColor()
    }

    fun handleBoxClick(tb: Box) {
        repository.handleBoxClick(tb)
    }

}