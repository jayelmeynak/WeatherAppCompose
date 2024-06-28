package com.example.compose.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.compose.ui.data.WeatherModel
import com.example.compose.ui.theme.CardNewBg
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MainList(daysList: List<WeatherModel>, currentDay: MutableState<WeatherModel>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(
            daysList
        ) { _, item ->
            ListItem(item = item, currentDay)

        }
    }
}


@Composable
fun ListItem(item: WeatherModel, currentDay: MutableState<WeatherModel>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp)
            .clickable {
                if (item.hours.isEmpty()) return@clickable
                currentDay.value = item
            },
        colors = CardDefaults.cardColors(
            containerColor = CardNewBg
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 6.dp, top = 4.dp, bottom = 6.dp)
            ) {
                Text(
                    text = extractTime(item.time),
                    fontSize = 14.sp,
                    color = Color.White
                )
                Text(
                    text = item.condition,
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = if (item.hours.isEmpty()) "${item.currentTemp}°C" else "${item.maxTemp}°C/${item.minTemp}°C",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.weight(0.5f)
            )
            AsyncImage(
                model = "https:${item.icon}",
                contentDescription = "condition",
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(35.dp)
                    .weight(0.5f)
            )
        }

    }
}

@Composable
fun DialogSearch(dialogState: MutableState<Boolean>, city: MutableState<String>) {
    val dialogText = remember{
        mutableStateOf("")
    }
    AlertDialog(onDismissRequest = {
        dialogState.value = false
    },
        confirmButton = {
            TextButton(onClick = {
                dialogState.value = false
                city.value = dialogText.value
            }) {
                Text(text = "Oк")
            }

        },
        dismissButton = {
            TextButton(onClick = {
                dialogState.value = false
            }) {
                Text(text = "Назад")
            }

        },
        title = {
            Column {
                Text(text = "Введите название города")
                TextField(value = dialogText.value, onValueChange = {
                    dialogText.value = it
                })
            }
        }
    )
}

private fun extractTime(dateTimeString: String): String {
    return if (dateTimeString.length > 10) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val dateTime = LocalDateTime.parse(dateTimeString, formatter)
        val timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        dateTime.format(timeFormatter)
    } else dateTimeString
}