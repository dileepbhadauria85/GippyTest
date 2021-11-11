package com.dileep.giphysampletest.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.dileep.giphysampletest.data.source.local.entity.Gif

@Dao
interface GifDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGifs(gifs: List<Gif>)

    @Query("SELECT * FROM gif")
    fun selectGifs(): PagingSource<Int, Gif>

    @Query("SELECT * FROM gif WHERE isFavorite = :isFavorite")
    fun selectFavoriteGifs(isFavorite: Boolean = true): PagingSource<Int, Gif>

    @Update
    suspend fun updateGif(gif: Gif): Int

    @Query("DELETE FROM gif")
    suspend fun clearGifs()
}