package com.example.stockgazer.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.stockgazer.R
import com.example.stockgazer.ui.components.text.Headline
import com.example.stockgazer.ui.screens.chart.ChartViewModel
import com.example.stockgazer.ui.screens.chart.LatestPrice
import com.example.stockgazer.ui.screens.shared.UserViewModel
import com.example.stockgazer.ui.theme.ElementSpacing
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun YourTradesSection(latestPrice: LatestPrice) {
    val viewModel = hiltViewModel<ChartViewModel>()
    val showTradeCreationModal = viewModel.showTradeCreationModal.collectAsState()
    val trades = viewModel.trades.collectAsState(emptyList())
    val rotationAngle by animateFloatAsState(
        targetValue = if (showTradeCreationModal.value) 45f else 0f,
        label = "Icon Rotation"
    )

    val userViewModel = hiltViewModel<UserViewModel>()
    val userData = userViewModel.userData.collectAsStateWithLifecycle()

    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Headline(stringResource(R.string.your_trades_title))
        IconButton(
            onClick = {
                if (userData.value == null)
                    userViewModel.launchCredentialManager(context)

                viewModel.toggleAddingTrade()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = stringResource(R.string.add_icon_content_description),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.rotate(rotationAngle)
            )
        }
    }

    Spacer(modifier = Modifier.height(ElementSpacing))

    AnimatedVisibility(
        visible = showTradeCreationModal.value
    ) {
        TradeCreationCard()
    }
    Spacer(modifier = Modifier.height(ElementSpacing))

    trades.value.forEach {
        val price = it.price.toDouble()
        TradeRegister(
            type = it.type,
            amount = it.amount.toInt(),
            price = price,
            date = it.datetime.toLocalDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) ?: "",
            time = it.datetime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")) ?: "",
            percentChange = (latestPrice.value / price - 1) * 100
        )
    }
}
