package com.dileep.giphysampletest.ui

import com.dileep.giphysampletest.data.source.local.entity.Gif

interface TrendingGifToggleListener {
    fun onToggleFavorite(gif: Gif)
}