package com.pouyaa.imagediary

import androidx.recyclerview.widget.DiffUtil
import com.pouyaa.imagediary.model.PlaceModel

class PlaceDiffCallback : DiffUtil.ItemCallback<PlaceModel>() {
    override fun areItemsTheSame(oldItem: PlaceModel, newItem: PlaceModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlaceModel, newItem: PlaceModel): Boolean {
        return oldItem == newItem
    }
}