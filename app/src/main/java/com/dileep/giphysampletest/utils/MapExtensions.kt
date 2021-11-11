package com.dileep.giphysampletest.utils

import com.dileep.giphysampletest.data.source.local.entity.Gif
import com.dileep.giphysampletest.data.source.remote.RemoteTrendingResponse

fun RemoteTrendingResponse.mapToGif(): List<Gif> {
    return this.data.map {  // fixed width : 200 pixel
        val fixedWidthGif = it.images.fixedWidth
        Gif(it.id, fixedWidthGif.url, fixedWidthGif.width.toInt(), fixedWidthGif.height.toInt(), false)
    }
}
