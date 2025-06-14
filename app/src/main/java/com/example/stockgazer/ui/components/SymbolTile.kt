package com.example.stockgazer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.stockgazer.R
import com.example.stockgazer.ui.components.images.Logo
import com.example.stockgazer.ui.theme.IconToTextSpacingMedium
import com.example.stockgazer.ui.theme.IconToTextSpacingSmall
import com.example.stockgazer.ui.theme.PaddingSmall

@Composable
fun SymbolTile(symbol: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(vertical = PaddingSmall)
            .padding(horizontal = PaddingSmall),
        horizontalArrangement = Arrangement.spacedBy(IconToTextSpacingMedium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val logoUrl = stringResource(R.string.logos_url) + "$symbol.png"
        Logo(logoUrl, symbol)
        Text(
            text = symbol,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
} 