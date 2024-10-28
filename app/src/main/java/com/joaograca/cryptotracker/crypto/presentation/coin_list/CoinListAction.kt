package com.joaograca.cryptotracker.crypto.presentation.coin_list

import com.joaograca.cryptotracker.crypto.presentation.models.CoinState

sealed interface CoinListAction {
    data class OnCoinClick(val coinState: CoinState) : CoinListAction
}