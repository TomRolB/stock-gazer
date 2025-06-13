package com.example.stockgazer.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.stockgazer.R
import com.example.stockgazer.ui.components.text.Headline
import com.example.stockgazer.ui.theme.HeadlineToTextSpacing
import com.example.stockgazer.ui.theme.IconToTextVerticalSpacingMedium

@Composable
fun BiometricErrorScreen(innerPadding: PaddingValues, errorTitle: String, errorMessage: String) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.mipmap.stockgazer_logo_foreground),
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(IconToTextVerticalSpacingMedium))
            Headline(errorTitle)
            Spacer(modifier = Modifier.height(HeadlineToTextSpacing))
            Text(errorMessage, color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center)
        }
    }
}