package com.zaincheema.turf.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.zaincheema.turf.utils.CountdownUtil
import com.zaincheema.turf.utils.CountdownUtil.formatTime
import com.zaincheema.turf.viewmodels.MainViewModel

@Composable
fun CountdownView(
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val time by viewModel.time.observeAsState(initial = CountdownUtil.COUNTDOWN.formatTime())

    CountdownView(time = time)
    }


@Composable
fun CountdownView(time: String) {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        Text(
            text = time,
            textAlign = TextAlign.Center
        )
    }

}
