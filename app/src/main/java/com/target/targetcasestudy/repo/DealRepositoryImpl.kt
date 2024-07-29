package com.target.targetcasestudy.repo

import com.target.targetcasestudy.data.Deal
import kotlinx.coroutines.flow.Flow

class DealRepositoryImpl: DealRepository {
    override suspend fun retrieveDeals(): Flow<List<Deal>> {
        TODO("Not yet implemented")
    }

    override suspend fun retrieveDeal(dealId: Int): Flow<Deal> {
        TODO("Not yet implemented")
    }
}