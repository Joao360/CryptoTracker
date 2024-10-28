package com.joaograca.cryptotracker.crypto.data.networking

import com.joaograca.cryptotracker.core.data.networking.constructUrl
import com.joaograca.cryptotracker.core.data.networking.safeCall
import com.joaograca.cryptotracker.core.domain.util.NetworkError
import com.joaograca.cryptotracker.core.domain.util.Result
import com.joaograca.cryptotracker.core.domain.util.map
import com.joaograca.cryptotracker.crypto.data.mappers.toCoin
import com.joaograca.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import com.joaograca.cryptotracker.crypto.domain.Coin
import com.joaograca.cryptotracker.crypto.domain.CoinDataSource
import io.ktor.client.*
import io.ktor.client.request.*

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }
}