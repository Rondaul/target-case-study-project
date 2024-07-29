package com.target.targetcasestudy.api

import com.target.targetcasestudy.data.Deal
import com.target.targetcasestudy.data.Products
import javax.inject.Inject

/**
 * Class for fetching remote data via api call
 */
class RemoteDealDataSource @Inject constructor(private val dealApi: DealApi): BaseApiResponse() {

    /**
     * Retrieves list of deals from remote source
     */
    suspend fun retrieveDeals(): ApiResult<Products> {
        return makeApiCall { dealApi.retrieveDeals() }
    }

    /**
     * Retrieves a single deal item for the given dealId
     * @param dealId - id for the deal item to be fetched
     */
    suspend fun retrieveDeal(dealId: Int): ApiResult<Deal> {
        return makeApiCall { dealApi.retrieveDeal(dealId) }
    }
}