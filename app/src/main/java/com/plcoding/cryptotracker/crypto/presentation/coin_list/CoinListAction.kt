package com.plcoding.cryptotracker.crypto.presentation.coin_list

import com.plcoding.cryptotracker.crypto.presentation.models.CoinState

sealed interface CoinListAction {
    data class OnCoinClick(val coinState: CoinState) : CoinListAction
}