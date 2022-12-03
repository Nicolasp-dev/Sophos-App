package com.kotlin.sophosapp.data.model.rs_documents

data class RS_Docs_Submmit(
    val TipoId: String,
    val Identificacion: String,
    val Nombre: String,
    val Apellido: String,
    val Ciudad: String,
    val Correo: String,
    val TipoAdjunto: String,
    val Adjunto: String
)
