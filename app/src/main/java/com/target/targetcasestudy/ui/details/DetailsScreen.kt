package com.target.targetcasestudy.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.target.targetcasestudy.R
import com.target.targetcasestudy.ui.home.DealAmountAndStatus
import com.target.targetcasestudy.ui.theme.Red

@Composable
fun DetailsScreen(id: Int?, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 38.dp)
            .background(colorResource(id = R.color.details_background_color))
    ) {
        val (detailContent, addCardButton) = createRefs()
        Column(modifier = Modifier
            .constrainAs(detailContent) {
                bottom.linkTo(addCardButton.top)
                top.linkTo(parent.top)
            }
            .verticalScroll(scrollState)
            .padding(bottom = 2.dp)) {
            Surface(shadowElevation = 2.dp) {
                Column(modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    DealImageDetail(modifier = modifier)
                    Spacer(modifier = modifier.height(16.dp))
                    Text(
                        text = "Women's Long Sleeve Denim Jacket - Universal Thread™",
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = modifier.height(24.dp))
                    DealAmountAndStatus(modifier = modifier)
                }
            }
            Spacer(modifier = modifier.height(8.dp))
            ProductDetails()
        }
        BottomAddToCardButton(
            modifier = Modifier.constrainAs(addCardButton) {
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Composable
fun BottomAddToCardButton(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Card(
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Button(
                onClick = { /* Handle button click */ },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Red),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Add to cart",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Composable function for deal image in the details screen
 */
@Composable
fun DealImageDetail(modifier:Modifier = Modifier) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.deal_detail_image_size))
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop,
        painter = painterResource(id = R.drawable.deal_image),
        contentDescription = "Item Image")
}

@Composable
fun ProductDetails(modifier: Modifier = Modifier) {
    Surface(
        shadowElevation = 4.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            Text(
                text = "Product Details",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.secondary_text_color)
            )
            Spacer(modifier = modifier.height(16.dp))
            Text(
                text = "Adult oversized crewneck tee made from 100% cotton for soft feel and comfy wear. Tailored in an oversized silhouette with a crewneck design with short sleeves and drop shoulders. At-hip length for wearing tucked in or out.\n" +
                        "\n" +
                        "Wild Fable™: A look for every story.",
                color = colorResource(id = R.color.lighter_gray),
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL)
@Composable
fun DetailsPreview() {
    DetailsScreen(1)
}