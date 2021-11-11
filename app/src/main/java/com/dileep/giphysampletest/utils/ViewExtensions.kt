package com.dileep.giphysampletest.utils

import android.widget.ImageView
import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.dileep.giphysampletest.R
import com.dileep.giphysampletest.data.source.local.entity.Gif

/* ImageView */
@BindingAdapter("layoutWidth", "layoutHeight")
fun ImageView.setLayoutHeight(width: Int, height: Int){
    this.layoutParams.apply {
        this.width = width * 3
        this.height = height * 3
    }
}

@BindingAdapter("imageUrl")
fun ImageView.setUrl(gif: Gif) {
    Glide.with(this)
        .load(gif.url)
        .error(R.drawable.ic_baseline_error_24)
        .into(this)
}

/* ToggleButton */
@BindingAdapter("favorite")
fun ToggleButton.setFavorite(isFavorite: Boolean) {
    this.isChecked = isFavorite
}

/* RecyclerView*/
@BindingAdapter("gridLayoutManager")
fun RecyclerView.setGridLayoutManager(columns: Int) {
    this.layoutManager = StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL)
}