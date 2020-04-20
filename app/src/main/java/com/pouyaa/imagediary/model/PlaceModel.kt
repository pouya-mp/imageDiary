package com.pouyaa.imagediary.model

import java.io.Serializable


data class PlaceModel(
    var id: Int,
    var title: String,
    var image: String,
    var description: String,
    var date: String,
    var location: String,
    var latitude: Double,
    var longitude: Double

) : Serializable