package com.example.beddabarbershop.model


import com.google.gson.annotations.SerializedName

data class ResponLogin(
    @SerializedName("message")
    val message: String,
    @SerializedName("customer")
    val customer: DataCustomer,
    @SerializedName("status")
    val status: Boolean
)