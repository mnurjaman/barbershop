package com.example.beddabarbershop.model


import com.google.gson.annotations.SerializedName

data class DataReservasi(
    @SerializedName("id_admin")
    val idAdmin: String,
    @SerializedName("id_customer")
    val idCustomer: String,
    @SerializedName("id_reservasi")
    val idReservasi: String,
    @SerializedName("jam")
    val jam: String,
    @SerializedName("nama_layanan")
    val layanan: String,
    @SerializedName("harga_layanan")
    val harga_layanan: Int,
    @SerializedName("review_text")
    val review_Text: String?,
    @SerializedName("review_rating")
    val review_rating: Int?,
    @SerializedName("status")
    val status: String,
    @SerializedName("tanggal")
    val tanggal: String
)