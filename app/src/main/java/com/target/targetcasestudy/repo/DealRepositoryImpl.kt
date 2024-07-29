package com.target.targetcasestudy.repo

import com.target.targetcasestudy.api.ApiResult
import com.target.targetcasestudy.api.RemoteDealDataSource
import com.target.targetcasestudy.data.Deal
import com.target.targetcasestudy.data.Products
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DealRepositoryImpl @Inject constructor(
    private val remoteDealDataSource: RemoteDealDataSource,
    private val dispatcher: CoroutineDispatcher
):
    DealRepository {
    override suspend fun retrieveDeals(): Flow<ApiResult<Products>> {
        return flow {
            val result = remoteDealDataSource.retrieveDeals()
            emit(result)
        }.flowOn(dispatcher)
    }

    override suspend fun retrieveDeal(dealId: Int): Flow<ApiResult<Deal>> {
        return flow {
            val result = remoteDealDataSource.retrieveDeal(dealId)
            emit(result)
        }.flowOn(dispatcher)
    }
}