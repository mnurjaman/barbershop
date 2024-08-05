package com.example.beddabarbershop.model

import com.google.gson.annotations.SerializedName

data class ResponCustomer(
    @SerializedName("data")
    val data: List<DataCustomer>,
    @SerializedName("status")
    val status: Boolean
)
