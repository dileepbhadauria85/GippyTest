package com.dileep.giphysampletest.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey val gifId: String,
    val prevKey: Int?,
    val nextKey: Int?
)
