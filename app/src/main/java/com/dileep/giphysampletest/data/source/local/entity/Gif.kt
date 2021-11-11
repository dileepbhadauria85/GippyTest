package com.dileep.giphysampletest.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "gif")
data class Gif(
    @PrimaryKey @field:SerializedName("id") val id: String,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("width") val width: Int,
    @field:SerializedName("height") val height: Int,
    @field:SerializedName("is_favorite") var isFavorite: Boolean
)


