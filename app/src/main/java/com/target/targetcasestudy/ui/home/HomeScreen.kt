package com.target.targetcasestudy.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.target.targetcasestudy.ui.theme.Gray
import com.target.targetcasestudy.ui.theme.Green
import com.target.targetcasestudy.ui.theme.Red

/**
 * Composable function for home screen. Show either loader, error or content ui based on states
 * and effects data.
 */
@Composable
fun HomeScreen(
    onDealItemClick: (dealId: Int) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()) {
    val homeUiState = homeViewModel.viewState.collectAsStateWithLifecycle()
    val homeEffect = rememberFlowWithLifecycle(flow = homeViewModel.viewEffects)
    var showProgress by remember { mutableStateOf(false) }
    var showErrorUI by remember { mutableStateOf(Pair("", false)) }

    ComposableLifecycle { source, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            homeViewModel.sendEvent(HomeEvent.RetrieveDeals)
        }
    }
    LaunchedEffect(Unit) {
        homeEffect.collect { effect ->
            when(effect) {
                is HomeEffect.Loading -> {
                    showProgress = true
                }
                is HomeEffect.Error -> {
                    showProgress = false
                    showErrorUI = Pair(effect.errorMsg, true)
                }
                HomeEffect.Success -> {
                    showProgress = false
                    showErrorUI = Pair("", false)
                }
            }
        }
    }

    if (showProgress) {
        CircularProgress()
    } else if(showErrorUI.second) {
        ErrorScreen(errorMsg = showErrorUI.first) {
            homeViewModel.sendEvent(HomeEvent.RetrieveDeals)
        }
    } else {
        HomeContent(homeUiState, onDealItemClick, modifier)
    }
}

/**
 * Composable function for home content. Show list of deals vertically
 */
@Composable
fun HomeContent(
    homeUiState: State<HomeUiState>,
    onDealItemClick: (dealId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(homeUiState.value.deals) { deal ->
            DealItem(
                deal = deal,
                onDealItemClick = onDealItemClick,
                modifier = modifier
            )
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
fun DealItem(deal: Deal, onDealItemClick: (dealId: Int) -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onDealItemClick(deal.id) }
    ) {
        DealImage(imageUrl = deal.imageUrl, modifier = modifier)
        Spacer(modifier = Modifier.width(12.dp))
        DealInfo(deal = deal, modifier = modifier)
    }
}

/**
 * Composable function for deal image in the deal item
 */
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DealImage(imageUrl: String, modifier: Modifier = Modifier) {
    GlideImage(
        modifier = modifier
            .size(dimensionResource(id = R.dimen.deal_list_item_image_size))
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop,
        model = imageUrl,
        contentDescription = "Item Image")
}

/**
 * Composable function for showing deal information in the deal item
 */
@Composable
fun DealInfo(deal: Deal, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        DealAmountAndStatus(
            salePrice = deal.salePrice?.displayString ?: "",
            regularPrice = deal.regularPrice.displayString,
            status = deal.fulfillment,
            modifier = modifier
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = deal.title,
            color = Color.Black,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
        Spacer(modifier = modifier.height(8.dp))
        Row {
            Text(
                text = deal.availability,
                color = Green,
                fontSize = 12.sp,
                modifier = modifier.padding(end = 4.dp)
            )
            Text(
                text = stringResource(R.string.in_aisle, deal.aisle),
                color = Gray,
                fontSize = 12.sp
            )

        }
    }
}

/**
 * Composable function for showing Deal amount and fulfillment status
 */
@Composable
fun DealAmountAndStatus(salePrice: String, regularPrice: String, status: String, modifier: Modifier) {
    Row {
        Text(
            text = salePrice,
            color = Red,
            fontWeight = FontWeight.Bold,
            fontSize = 21.sp,
            modifier = modifier.padding(end = 4.dp)
        )
        Text(
            text = stringResource(R.string.reg_price, regularPrice),
            color = colorResource(id = R.color.secondary_text_color),
            fontSize = 12.sp,
            modifier = modifier.offset(y = 2.dp)
        )
    }
    Text(text = status, color = Gray, fontSize = 12.sp)
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL)
@Composable
fun HomePreview() {
    HomeScreen({})
}