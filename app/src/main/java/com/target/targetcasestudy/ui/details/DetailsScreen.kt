package com.target.targetcasestudy.ui.details

import android.util.Log
import androidx.compose.foundation.ScrollState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.target.targetcasestudy.R
import com.target.targetcasestudy.data.Deal
import com.target.targetcasestudy.ui.common.CircularProgress
import com.target.targetcasestudy.ui.common.ComposableLifecycle
import com.target.targetcasestudy.ui.common.ErrorScreen
import com.target.targetcasestudy.ui.common.rememberFlowWithLifecycle
import com.target.targetcasestudy.ui.home.DealAmountAndStatus
import com.target.targetcasestudy.ui.theme.Red

private const val TAG = "Details Screen"

@Composable
fun DetailsScreen(id: Int?,
                  modifier: Modifier = Modifier,
                  detailsViewModel: DetailsViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()
    val detailsUiState = detailsViewModel.viewState.collectAsStateWithLifecycle()
    val detailsEffect = rememberFlowWithLifecycle(flow = detailsViewModel.viewEffects)
    var showProgress by remember { mutableStateOf(false) }
    var showErrorUI by remember { mutableStateOf(Pair("", false)) }

    ComposableLifecycle { source, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            if (id != null) {
                detailsViewModel.sendEvent(DetailsEvent.RetrieveDeal(id))
            } else {
                Log.e(TAG, "Id cannot be null!")
            }
        }
    }

    LaunchedEffect(Unit) {
        detailsEffect.collect { effect ->
            when(effect) {
                is DetailsEffect.Loading -> {
                    showProgress = true
                }
                is DetailsEffect.Error -> {
                    showErrorUI = Pair(effect.errorMsg, true)
                }
                DetailsEffect.Success -> {
                    showProgress = false
                    showErrorUI = Pair("", false)
                }
            }
        }
    }

    if (showProgress) {
        CircularProgress()
    } else if (showErrorUI.second) {
        ErrorScreen(errorMsg = showErrorUI.first) {
            if (id != null) {
                detailsViewModel.sendEvent(DetailsEvent.RetrieveDeal(id))
            } else {
                Log.e(TAG, "Id cannot be null!")
            }
        }
    } else {
        DetailsContent(
            deal = detailsUiState.value.deal,
            scrollState = scrollState,
            modifier = modifier)
    }
}

@Composable
fun DetailsContent(
    deal: Deal?,
    scrollState: ScrollState,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.details_background_color))
    ) {
        val (detailContent, addCardButton) = createRefs()
        Column(modifier = Modifier
            .constrainAs(detailContent) {
                bottom.linkTo(addCardButton.top)
                top.linkTo(parent.top)
                width = Dimension.fillToConstraints
            }
            .padding(bottom = 2.dp)) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .weight(1f, false)
                    .padding(top = 38.dp, bottom = 38.dp)
            ) {
                Surface(shadowElevation = 2.dp) {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        DealImageDetail(imageUrl = deal?.imageUrl, modifier = modifier)
                        Spacer(modifier = modifier.height(16.dp))
                        Text(
                            text = deal?.title ?: "",
                            color = Color.Black,
                            fontSize = 18.sp,
                            lineHeight = 24.sp
                        )
                        Spacer(modifier = modifier.height(24.dp))
                        DealAmountAndStatus(
                            salePrice = deal?.salePrice?.displayString ?: "",
                            regularPrice = deal?.regularPrice?.displayString ?: "",
                            status = deal?.fulfillment ?: "",
                            modifier = modifier
                        )
                    }
                }
                Spacer(modifier = modifier.height(8.dp))
                ProductDetails(productDescription = deal?.description ?: "")
            }
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
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DealImageDetail(imageUrl: String?, modifier: Modifier = Modifier) {
    GlideImage(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.deal_detail_image_size))
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop,
        model = imageUrl,
        contentDescription = "Item Image")
}

@Composable
fun ProductDetails(productDescription: String, modifier: Modifier = Modifier) {
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
                text = productDescription,
                color = colorResource(id = R.color.lighter_gray),
                fontSize = 16.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL)
@Composable
fun DetailsPreview() {
    DetailsScreen(1)
}