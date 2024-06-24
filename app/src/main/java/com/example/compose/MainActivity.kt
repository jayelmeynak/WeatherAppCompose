package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val item1 = ItemRowModel(
            R.drawable.image_1,
            "Саша",
            "Ура! Урок по jetpack compose!!! Наконец то дождался. Как же все таки легко это все сделать. Спасибо большое, была бы возможность хоть 100 лайков бы поставил. Теперь ждём продолжения.Сергей, а вот есть вопрос по данному уроку: а как сделать, что бы раскрыт была, только 1 карточка? Например нажали на первую, она раскрылась. Потом нажали на третью, первая свернулась, а третья развернулась. Просто не понятно как можно проверять стейты других элементов? Или это не возможно сделать?"
        )
        val item2 = ItemRowModel(R.drawable.image_1, "Маша", "content")
        val item3 = ItemRowModel(R.drawable.image_2, "Даша", "content")
        val item4 = ItemRowModel(R.drawable.image_2, "Дима", "content")
        val item5 = ItemRowModel(R.drawable.image_3, "Андрей", "content")
        val item6 = ItemRowModel(R.drawable.image_3, "Макс", "content")
        val item7 = ItemRowModel(R.drawable.image_1, "Леша", "content")
        val item8 = ItemRowModel(R.drawable.image_1, "Антон", "content")
        val numbers = listOf(item1, item2, item3, item4, item5, item6, item7, item8)
        enableEdgeToEdge()
        setContent {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .background(Color.Gray)
            ) {
                itemsIndexed(numbers) { _, item ->
                    ItemColumn(item)
                }
            }
        }
    }
}

