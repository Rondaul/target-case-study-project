package com.target.targetcasestudy.repo

import com.target.targetcasestudy.api.ApiResult
import com.target.targetcasestudy.data.Deal
import com.target.targetcasestudy.data.Products
import kotlinx.coroutines.flow.Flow

interface DealRepository {

    suspend fun retrieveDeals(): Flow<ApiResult<Products>>

    suspend fun retrieveDeal(dealId: Int): Flow<ApiResult<Deal>>
}