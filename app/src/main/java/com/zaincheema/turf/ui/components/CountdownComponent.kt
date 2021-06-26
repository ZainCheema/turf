package com.zaincheema.turf.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.padding(20.dp),
            text = time,
            fontWeight = FontWeight.Bold,
            fontSize = 60.sp,
            textAlign = TextAlign.Center
        )
    }

}
