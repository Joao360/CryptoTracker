package com.joaograca.cryptotracker.crypto.presentation.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.joaograca.cryptotracker.crypto.presentation.coin_list.components.CoinListItem
import com.joaograca.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.joaograca.cryptotracker.ui.theme.CryptoTrackerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListScreen(
    coinListState: CoinListState,
    onAction: (CoinListAction) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (coinListState.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        PullToRefreshBox(isRefreshing = coinListState.isRefreshing, onRefresh = onRefresh) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(coinListState.coins) { coinState ->
                    CoinListItem(
                        coinState = coinState,
                        onClick = { onAction(CoinListAction.OnCoinClick(coinState)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewCoinListScreen() {
    CryptoTrackerTheme {
        CoinListScreen(
            coinListState = CoinListState(
                isLoading = false,
                coins = (1..100).map { previewCoin.copy(id = it.toString()) }
            ),
            onAction = {},
            onRefresh = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}