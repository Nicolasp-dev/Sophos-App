package com.kotlin.sophosapp.data.model.rs_user

import com.google.gson.annotations.SerializedName

data class RS_User(
  val id: String,
  @SerializedName("nombre") val name: String,
  @SerializedName("apellido") val lastName: String,
  @SerializedName("acceso") val access: Boolean,
  val admin: Boolean,
)