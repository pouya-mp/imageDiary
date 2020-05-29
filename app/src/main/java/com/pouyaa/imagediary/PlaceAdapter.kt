package com.pouyaa.imagediary

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pouyaa.imagediary.model.PlaceModel
import kotlinx.android.synthetic.main.circucal_image_view.view.*
import kotlinx.android.synthetic.main.places_layout.view.*

class PlaceAdapter : ListAdapter<PlaceModel, PlaceAdapter.MyViewHolder>(PlaceDiffCallback()) {
    private var onClickListener: OnClickListener? = null

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                return MyViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.places_layout, parent, false)
                )
            }
        }

        val placeTitle: TextView = view.placeTitleTextView
        val placeImage: ImageView = view.selectedImageOfPlaceImageView
        val placeDescription: TextView = view.placeDescriptionTextView


        fun bind(item: PlaceModel) {
            placeDescription.text = item.description
            placeImage.setImageURI(Uri.parse(item.image))
            placeTitle.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, getItem(position))
        }

    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: PlaceModel)
    }

}