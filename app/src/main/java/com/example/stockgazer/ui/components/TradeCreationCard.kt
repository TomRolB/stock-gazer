package com.example.stockgazer.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stockgazer.ui.components.input.DateField
import com.example.stockgazer.ui.theme.CardBorderRadius
import com.example.stockgazer.ui.theme.PaddingMedium
import com.example.stockgazer.ui.theme.Primary800

@Composable
fun TradeCreationCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Primary800),
        modifier = Modifier
            .fillMaxWidth()
            .height(375.dp),
        shape = RoundedCornerShape(CardBorderRadius),
    ) {
        Box(
            modifier = Modifier.padding(PaddingMedium)
        ) {
            Column {
                DateField(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

