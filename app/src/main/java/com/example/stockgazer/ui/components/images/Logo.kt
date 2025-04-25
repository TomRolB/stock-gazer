package com.example.stockgazer.ui.components.images

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import coil3.compose.SubcomposeAsyncImage
import com.example.stockgazer.ui.theme.LogoImageSize
import com.example.stockgazer.ui.theme.Primary100
import com.example.stockgazer.ui.theme.Primary700

@Composable
fun Logo(logoUrl: String, symbol: String) {
    SubcomposeAsyncImage(
        model = logoUrl,
        contentDescription =  "${symbol}'s logo",
        error = { DefaultLogo(symbol) },
        modifier = Modifier
            .size(LogoImageSize)
            .clip(shape = CircleShape)
    )
}

@Composable
fun DefaultLogo(symbol: String) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.background(color = Primary700)) {
        Text(
            symbol.first().uppercase(),
            color = Primary100,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}