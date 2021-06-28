package com.zaincheema.turf.ui.components

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.zaincheema.turf.viewmodels.MainViewModel

@ExperimentalFoundationApi
@Composable
fun ColorPicker(
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val boxClicked by remember { mutableStateOf(false) }

    // BoxWithConstraints will provide the maxWidth used below
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
                                // TODO: When the box is selected, instantly color that box for the user, before retrofit
                                viewModel.selectColor(item)
                            }
                    ) {
                        // card's content
                    }
                }
            }
        }


}