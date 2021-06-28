package com.zaincheema.turf.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.zaincheema.turf.ui.components.BoxesGridView
import com.zaincheema.turf.ui.components.ColorPicker
import com.zaincheema.turf.ui.components.CountdownView
import com.zaincheema.turf.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("RestrictedApi")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

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
        val countdownOngoing by viewModel.countdownOngoing.observeAsState()
        Surface(color = Color(0xFFF0F0DD)) {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = "turf",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        )
                    },
                )
                BoxesGridView()
                // TODO: AnimatedVisibility logic for colorpicker and countdown

                if (countdownOngoing == false) {
                    ColorPicker()
                } else {
                    CountdownView()
                }

            }
        }
    }

}

