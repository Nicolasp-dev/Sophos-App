package com.kotlin.sophosapp.data.model.rs_offices

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("Ciudad") val name: String,
    val IdOficina: Int,
    val Latitud: String,
    val Longitud: String,
    val Nombre: String
)