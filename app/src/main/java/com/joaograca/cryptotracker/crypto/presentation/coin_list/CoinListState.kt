package com.joaograca.cryptotracker.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.joaograca.cryptotracker.crypto.presentation.models.CoinState

@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val coins: List<CoinState> = emptyList(),
    val selectedCoin: CoinState? = null
)
