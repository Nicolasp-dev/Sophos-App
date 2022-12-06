package com.kotlin.sophosapp.data.model.rs_offices

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("Ciudad") val cityName: String,
    @SerializedName("IdOficina") val officeId: Int,
    @SerializedName("Latitud") val latitude: String,
    @SerializedName("Longitud") val longitude: String,
    @SerializedName("Nombre") val placeName: String
)