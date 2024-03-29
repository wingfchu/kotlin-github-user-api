package com.wendychu.github_user_api.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.SearchView
import com.bumptech.glide.Glide
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
fun SearchView.getQueryChangeFlow(): StateFlow<String> {
    val query = MutableStateFlow("")
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            newText?.let {
                query.value = it
                return true
            }
            return false
        }

    })
    return query
}

fun TextView.setTextAndVisibility(text: String?) {
    visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
    text?.let { this.text = it }
}

fun ImageView.loadImage(@DrawableRes resId: Int) = Glide.with(this).load(resId).into(this)
fun ImageView.loadImage(url: String) = Glide.with(this).load(url).into(this)