package com.zaincheema.turf.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zaincheema.turf.ui.components.BoxesGridView
import com.zaincheema.turf.ui.components.ColorPicker
import com.zaincheema.turf.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("RestrictedApi")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private var boxClicked by mutableStateOf(false)


    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        viewModel.getBoxesFromService()
        setContent {
            Display()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.removeCompositeDisposable()
    }



    @ExperimentalAnimationApi
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Display() {
        Column {
            TopAppBar(title = { Text(text = "turf") }, backgroundColor = Color.White)
            BoxesGridView()
            // TODO: AnimatedVisibility logic for colorpicker and countdown
            ColorPicker()
        }
    }
}

