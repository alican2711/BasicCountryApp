package com.alicanknt.ulkeler.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.alicanknt.ulkeler.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.net.URL

fun ImageView.dowloadFromUrl(url: String?,progresDrawable:CircularProgressDrawable){

    val options= RequestOptions()
        .placeholder(progresDrawable)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}
fun placeHolderProgressBar(context: Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }

}
@BindingAdapter("android:dowloadUrl")
fun dowlondImage(view: ImageView,url: String?){
    view.dowloadFromUrl(url, placeHolderProgressBar(view.context))

}