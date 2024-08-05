package com.example.beddabarbershop.model


import com.google.gson.annotations.SerializedName

data class ResponReservasi(
    @SerializedName("message")
    val message: String,
    @SerializedName("reservasi")
    val dataReservasi: DataReservasi?,
    @SerializedName("status")
    val status: Boolean
)

data class ResponAllReservasi(
    @SerializedName("message")
    val message: String,
    @SerializedName("reservasi")
    val dataReservasi: ArrayList<DataReservasi>,
    @SerializedName("status")
    val status: Boolean
)

data class ResponBiasa(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)