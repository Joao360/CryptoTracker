package com.joaograca.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaograca.cryptotracker.core.domain.util.onError
import com.joaograca.cryptotracker.core.domain.util.onSuccess
import com.joaograca.cryptotracker.crypto.domain.Coin
import com.joaograca.cryptotracker.crypto.domain.CoinDataSource
import com.joaograca.cryptotracker.crypto.presentation.models.CoinState
import com.joaograca.cryptotracker.crypto.presentation.models.toCoinState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state = _state.onStart { loadCoins() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = CoinListState()
        )

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {
                selectCoin(coinState = action.coinState)
            }
        }
    }

    private fun selectCoin(coinState: CoinState) {
        _state.update { it.copy(selectedCoin = coinState) }

        viewModelScope.launch {
            coinDataSource.getCoinHistory(
                coinId = coinState.id,
                start = ZonedDateTime.now().minusDays(5),
                end = ZonedDateTime.now()
            )
                .onSuccess { history ->
                    println(history)
                }
                .onError { error ->
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            coinDataSource.getCoins()
                .onSuccess { coins ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            coins = coins.map(Coin::toCoinState)
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }
}