package com.joaograca.cryptotracker.crypto.domain

import com.joaograca.cryptotracker.core.domain.util.NetworkError
import com.joaograca.cryptotracker.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}