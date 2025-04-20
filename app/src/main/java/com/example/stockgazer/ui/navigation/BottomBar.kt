package com.example.stockgazer.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.stockgazer.R
import com.example.stockgazer.ui.theme.MontserratFontFamily
import com.example.stockgazer.ui.theme.Primary100
import com.example.stockgazer.ui.theme.Primary900


@Composable
fun BottomBar(
    onNavigate: (String) -> Unit,
) {
    val homeTab = TabBarItem(
        title = StockGazerScreen.Home.name,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    )
    val rankingTab = TabBarItem(
        title = StockGazerScreen.Search.name,
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search
    )
    val profileTab = TabBarItem(
        title = StockGazerScreen.Chart.name,
        selectedIcon = ImageVector.vectorResource(R.drawable.chart), // TODO: fix icon
        unselectedIcon = Icons.AutoMirrored.Outlined.List
    )

    val tabBarItems = listOf(homeTab, rankingTab, profileTab)

    TabView(tabBarItems, onNavigate)
}

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null
)

@Composable
fun TabView(tabBarItems: List<TabBarItem>, onNavigate: (String) -> Unit) {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(containerColor = Primary900) {
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    println(tabBarItem.title)
                    selectedTabIndex = index
                    onNavigate(tabBarItem.title)
                },
                icon = {
                    TabBarIconView(
                        isSelected = selectedTabIndex == index,
                        selectedIcon = tabBarItem.selectedIcon,
                        unselectedIcon = tabBarItem.unselectedIcon,
                        title = tabBarItem.title,
                        badgeAmount = tabBarItem.badgeAmount,
                    )
                },
                label = { Text(tabBarItem.title, fontFamily = MontserratFontFamily) },
                colors = NavigationBarItemColors(
                    selectedIndicatorColor = Primary100,
                    selectedIconColor = Primary900,
                    unselectedIconColor = Primary100,
                    selectedTextColor = Primary100,
                    unselectedTextColor = Primary100,
                    disabledIconColor = Primary900,
                    disabledTextColor = Primary900,
                )
            )
        }
    }
}

@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String,
    badgeAmount: Int? = null
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            imageVector = if (isSelected) {selectedIcon} else {unselectedIcon},
            contentDescription = title,
        )
    }
}

@Composable
fun TabBarBadgeView(count: Int? = null) {
    if (count != null) {
        Badge {
            Text(
                count.toString(),
            )
        }
    }
}