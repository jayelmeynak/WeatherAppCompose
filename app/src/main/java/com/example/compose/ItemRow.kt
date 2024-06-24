package com.example.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemColumn(item: ItemRowModel) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = item.image),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(CircleShape)
        )
        Column(modifier = Modifier.padding(top = 8.dp, start = 4.dp, bottom = 8.dp)) {
            Text(text = item.title,
                modifier = Modifier.padding(4.dp),
                fontSize = 20.sp)
            Text(text = item.content,
                modifier = Modifier.padding(4.dp).clickable {
                    isExpanded = !isExpanded
                },
                maxLines = if(isExpanded) 15 else 1,
                fontSize = 16.sp)
        }
    }
}