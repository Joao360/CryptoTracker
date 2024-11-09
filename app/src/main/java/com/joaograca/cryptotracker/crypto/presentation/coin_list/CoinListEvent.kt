package com.joaograca.cryptotracker.crypto.presentation.coin_list

import com.joaograca.cryptotracker.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError) : CoinListEvent
}