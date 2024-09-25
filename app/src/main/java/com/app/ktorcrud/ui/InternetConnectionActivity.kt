package com.app.ktorcrud.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.app.ktorcrud.R


@Composable
fun ShowInternetStatus() {
    Box {
        Image(
            painterResource(id = R.drawable.wifi),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )
    }
}


@Composable
fun ShowNoInternetStatus() {
    Box {
        Image(
            painterResource(id = R.drawable.nowifi),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )
    }
}