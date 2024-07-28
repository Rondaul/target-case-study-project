package com.target.targetcasestudy.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.target.targetcasestudy.R
import com.target.targetcasestudy.ui.theme.Gray
import com.target.targetcasestudy.ui.theme.Green
import com.target.targetcasestudy.ui.theme.Red

/**
 * Composable function for Home Screen. Shows List of deals vertically.
 */
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(10) {
            DealItem(modifier = modifier)
            HorizontalDivider(
                modifier = Modifier.padding(start = 16.dp),
                color = colorResource(id = R.color.divider_color)
            )
        }
    }
}

/**
 * Composable function for each deal item shown in the home screen list.
 */
@Composable
fun DealItem(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        DealImage(modifier = modifier)
        Spacer(modifier = Modifier.width(12.dp))
        DealInfo(modifier = modifier)
    }
}

/**
 * Composable function for deal image in the deal item
 */
@Composable
fun DealImage(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .size(dimensionResource(id = R.dimen.deal_list_item_image_size))
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop,
        painter = painterResource(id = R.drawable.deal_image),
        contentDescription = "Item Image")
}

/**
 * Composable function for showing deal information in the deal item
 */
@Composable
fun DealInfo(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row {
            Text(
                text = "$34.99",
                color = Red,
                fontWeight = FontWeight.Bold,
                fontSize = 21.sp,
                modifier = modifier.padding(end = 4.dp)
            )
            Text(
                text = "reg. $34.99",
                color = colorResource(id = R.color.secondary_text_color),
                fontSize = 12.sp
            )
        }
        Text(text = "Online", color = Gray, fontSize = 12.sp)
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = "Women's Long Sleeve Denim Jacket - Universal Thread™",
            color = Color.Black,
            fontSize = 14.sp
        )
        Spacer(modifier = modifier.height(8.dp))
        Row {
            Text(
                text = "In stock",
                color = Green,
                fontSize = 12.sp,
                modifier = modifier.padding(end = 4.dp)
            )
            Text(
                text = "in aisle W20",
                color = Gray,
                fontSize = 12.sp
            )

        }
    }
}