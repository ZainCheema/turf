package com.zaincheema.turf.views

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.core.app.ComponentActivity

@SuppressLint("RestrictedApi")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            hello()
        }
    }

    @Composable
    fun hello() {
        Text("Helloooooo")
    }
}