package com.dileep.giphysampletest.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dileep.giphysampletest.data.source.local.entity.RemoteKey

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE gifId = :gifId")
    suspend fun selectRemoteKeyGifId(gifId: String): RemoteKey?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}