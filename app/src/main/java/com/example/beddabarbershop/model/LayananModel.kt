package com.example.beddabarbershop.model

data class LayananModel(
    val status: Boolean,
    val message: String,
    val data: ArrayList<SingleLayananModel>?
)

data class SingleLayananModel(
    val id_layanan: Int,
    val nama_layanan: String,
    val harga_layanan: Int,
    val gambar_layanan: String?
)