package com.joaograca.cryptotracker.di

import com.joaograca.cryptotracker.core.data.networking.HttpClientFactory
import com.joaograca.cryptotracker.crypto.data.networking.RemoteCoinDataSource
import com.joaograca.cryptotracker.crypto.domain.CoinDataSource
import com.joaograca.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.*
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()

    viewModelOf(::CoinListViewModel)
}