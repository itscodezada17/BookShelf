package com.example.testapp.books.bindings

import android.annotation.SuppressLint
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.testapp.R
import java.time.format.DateTimeFormatter

@SuppressLint("CheckResult")
@BindingAdapter("imageUrl")
fun loadImageWithGlide(imageView: AppCompatImageView, imageUrl: String?) {
    if (imageUrl!=null) {
        Glide.with(imageView.context).load(imageUrl)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView)
    }
    else{
        loadImageFromDrawable(imageView, R.drawable.ic_launcher_background)
    }
}

@BindingAdapter("srcDrawable")
fun loadImageFromDrawable(imageView: AppCompatImageView, srcDrawable: Int) {

    Glide.with(imageView.context).load(ContextCompat.getDrawable(imageView.context, srcDrawable))
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(imageView)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("publishedTxt")
fun setPublishedYearText(textView: AppCompatTextView, published: Long) {
    val date = DateTimeFormatter.ISO_INSTANT
        .format(java.time.Instant.ofEpochSecond(published))

    textView.text = "Published in ${date.substring(0,4)}"
}
