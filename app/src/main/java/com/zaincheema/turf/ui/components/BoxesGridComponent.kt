package com.zaincheema.turf.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zaincheema.turf.viewmodels.MainViewModel

@ExperimentalFoundationApi
@Composable
fun BoxesGridView(
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val boxes by viewModel.boxes.observeAsState()

    if(boxes?.isNotEmpty() == true) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(10),
            modifier = Modifier
                .background(Color.Transparent)
                .padding(8.dp)
        ) {
            boxes?.let {
                items(it.size) { t ->
                    Box(
                        Modifier
                            .clickable(true, null, null) {
                                viewModel.handleBoxClick(boxes!![t])
                                //boxClicked = true
                                // If the countdown has been completed, allow
                                // the color picker to be visible
                                if (!viewModel.countdownOngoing.value!!) {
                                    // TODO: ColorPicker visibility logic here
                                } else {
                                    // TODO: Countdown text logic
                                }
                            }
                            .aspectRatio(1f)
                            .background(Color(boxes!![t].colorHex))
                    )
                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text("LOADING")
        }
    }
}