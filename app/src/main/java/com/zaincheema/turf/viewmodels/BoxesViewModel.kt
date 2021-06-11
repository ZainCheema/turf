package com.zaincheema.turf.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.zaincheema.turf.model.Box
import com.zaincheema.turf.respository.BoxesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// This ViewModel is in charge of updating UI in MainActivity.kt
@HiltViewModel
class BoxesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: BoxesRepository
) : ViewModel() {
        private val _boxes = MutableLiveData<List<Box>>()

}