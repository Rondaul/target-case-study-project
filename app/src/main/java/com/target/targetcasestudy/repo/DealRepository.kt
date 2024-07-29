package com.target.targetcasestudy.repo

import com.target.targetcasestudy.data.Deal
import kotlinx.coroutines.flow.Flow

interface DealRepository {

    suspend fun retrieveDeals(): Flow<List<Deal>>

    suspend fun retrieveDeal(dealId: Int): Flow<Deal>
}