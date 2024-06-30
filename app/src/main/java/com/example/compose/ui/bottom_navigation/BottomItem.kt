package com.example.compose.ui.bottom_navigation

import com.example.compose.R

sealed class BottomItem(val title: String, val iconId: Int, val route: String) {
    object Screen1: BottomItem("Screen 1", R.drawable.icon, "screen_1")
    object Screen2: BottomItem("Screen 2", R.drawable.icon, "screen_2")
    object Screen3: BottomItem("Screen 3", R.drawable.icon, "screen_3")
    object Screen4: BottomItem("Screen 4", R.drawable.icon, "screen_4")
}