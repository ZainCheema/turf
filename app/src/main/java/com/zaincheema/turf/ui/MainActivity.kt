package com.zaincheema.turf.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zaincheema.turf.viewmodels.BoxesViewModel
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("RestrictedApi")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: BoxesViewModel by viewModels()


    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        viewModel.getBoxesList()
        setContent {
            Display()
        }
    }

    @ExperimentalAnimationApi
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun BoxGrid() {
        val boxes by viewModel.boxes.observeAsState()
        LazyVerticalGrid(
            cells = GridCells.Fixed(10),
            modifier = Modifier
                .background(Color.Transparent)
                .padding(8.dp)
        ) {
            items(viewModel.boxes.value!!.size) { t ->
                Box(
                    Modifier
                        .clickable(true, null, null) {
                            viewModel.handleBoxClick(viewModel.boxes.value!![t])
                        }
                        .aspectRatio(1f)
                        .background(Color(viewModel.boxes.value!![t].colorHex))
                )
            }
        }
    }

    @ExperimentalAnimationApi
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ColorPicker() {
        // BowWithConstraints will provide the maxWidth used below
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            // LazyRow to display your items horizontally
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                state = rememberLazyListState(),
                contentPadding = PaddingValues(all = 1.dp)
            ) {
                items(viewModel.getColorsList()) { item ->
                    Box(
                        modifier = Modifier
                            .scale(0.75f)
                            .aspectRatio(1f)
                            .background(Color(item))
                            .clickable {
                                Toast
                                    .makeText(
                                        baseContext,
                                        "color selected: $item",
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                                viewModel.setSelectedColor(item)
                            }
                    ) {
                        //Text(item) // card's content
                    }
                }
            }
        }
    }

    @ExperimentalAnimationApi
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Display() {
        Column {
            TopAppBar(title = { Text(text = "turf") }, backgroundColor = Color.White)
          //  BoxGrid()
            ColorPicker()
        }
    }
}

