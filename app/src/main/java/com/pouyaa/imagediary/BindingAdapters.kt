package com.pouyaa.imagediary

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageStr")
fun bindImageStr(view: ImageView, uriStr: String) {

    view.setImageURI(Uri.parse(uriStr))


}