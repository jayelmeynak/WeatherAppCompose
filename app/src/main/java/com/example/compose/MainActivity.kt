package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val item1 = ItemRowModel(R.drawable.image_1, "Саша")
        val item2 = ItemRowModel(R.drawable.image_1, "Маша")
        val item3 = ItemRowModel(R.drawable.image_2, "Даша")
        val item4 = ItemRowModel(R.drawable.image_2, "Дима")
        val item5 = ItemRowModel(R.drawable.image_3, "Андрей")
        val item6 = ItemRowModel(R.drawable.image_3, "Макс")
        val item7 = ItemRowModel(R.drawable.image_1, "Леша")
        val item8 = ItemRowModel(R.drawable.image_1, "Антон")
        val numbers = listOf(item1, item2, item3, item4, item5, item6, item7, item8)
        enableEdgeToEdge()
        setContent {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .background(Color.Gray)
            ) {
                itemsIndexed(numbers) { _, item ->
                    ItemRow(item)
                }
            }
        }
    }
}

