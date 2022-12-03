package com.kotlin.sophosapp.data.model.rs_user

data class RS_User(
  val id: String,
  val nombre: String,
  val apellido: String,
  val acceso: Boolean,
  val admin: Boolean,
)