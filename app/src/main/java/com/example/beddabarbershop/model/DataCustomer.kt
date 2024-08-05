package com.example.beddabarbershop.model


import com.google.gson.annotations.SerializedName

data class DataCustomer(
    @SerializedName("alamat")
    val alamat: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id_customer")
    val idCustomer: String,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("no_hp")
    val noHp: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)