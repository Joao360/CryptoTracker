package com.joaograca.cryptotracker.core.data.networking

import com.joaograca.cryptotracker.BuildConfig

fun constructUrl(url: String): String {
    return when {
        url.contains(BuildConfig.BASE_URL) -> url
        url.startsWith("/") -> BuildConfig.BASE_URL + url
        else -> "/$url"
    }
}