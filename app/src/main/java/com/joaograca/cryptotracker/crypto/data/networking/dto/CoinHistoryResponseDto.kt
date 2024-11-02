package com.joaograca.cryptotracker.crypto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryResponseDto(
    val data: List<CoinPriceDto>
)
