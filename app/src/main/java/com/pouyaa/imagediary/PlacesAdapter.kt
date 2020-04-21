package com.pouyaa.imagediary

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pouyaa.imagediary.model.PlaceModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.places_layout.view.*

class PlacesAdapter(
    private var context: Context,
    private var myList: List<PlaceModel>
) : RecyclerView.Adapter<PlacesAdapter.MyViewHolder>() {
    private var onClickListener: OnClickListener? = null


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val placeTitle: TextView = view.placeTitleTextView
        val placeImage: CircleImageView = view.selectedImageOfPlaceImageView
        val placeDescription: TextView = view.placeDescriptionTextView

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(

            LayoutInflater.from(context).inflate(R.layout.places_layout, parent, false)


        )
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = myList[position]

        holder.placeDescription.text = model.description
        holder.placeImage.setImageURI(Uri.parse(model.image))
        holder.placeTitle.text = model.title



        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, model)
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: PlaceModel)
    }

    fun getPlaceModel(position: Int): PlaceModel {
        return myList[position]
    }
}