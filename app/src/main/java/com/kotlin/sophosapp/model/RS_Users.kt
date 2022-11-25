package com.kotlin.sophosapp.model

data class RS_User(
    val id: String,
    val nombre: String,
    val apellido: String,
    val acceso: Boolean,
    val admin: Boolean,
)