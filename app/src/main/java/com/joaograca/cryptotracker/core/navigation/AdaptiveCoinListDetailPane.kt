@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.joaograca.cryptotracker.core.navigation

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joaograca.cryptotracker.core.presentation.util.ObserveAsEvents
import com.joaograca.cryptotracker.core.presentation.util.toString
import com.joaograca.cryptotracker.crypto.presentation.coin_detail.CoinDetailScreen
import com.joaograca.cryptotracker.crypto.presentation.coin_list.CoinListAction
import com.joaograca.cryptotracker.crypto.presentation.coin_list.CoinListEvent
import com.joaograca.cryptotracker.crypto.presentation.coin_list.CoinListScreen
import com.joaograca.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AdaptiveCoinListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: CoinListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvents(events = viewModel.events) { event ->
        when (event) {
            is CoinListEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                CoinListScreen(
                    coinListState = state,
                    onAction = { action ->
                        viewModel.onAction(action)
                        when (action) {
                            is CoinListAction.OnCoinClick -> {
                                navigator.navigateTo(pane = ListDetailPaneScaffoldRole.Detail)
                            }
                        }
                    },
                    onRefresh = viewModel::refreshCoins
                )
            }
        },
        detailPane = {
            AnimatedPane {
                CoinDetailScreen(state)
            }
        },
        modifier = modifier
    )
}