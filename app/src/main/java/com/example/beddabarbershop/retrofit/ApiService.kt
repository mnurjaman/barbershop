package com.example.beddabarbershop.retrofit

import com.example.beddabarbershop.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("reservasi")
    fun getUsernameByIdCustomer(@Query("id_customer") id_customer : String): Call<ResponReservasi>


    @GET("reservasi/indexByIdCustomer")
    fun getReservasiByIdCustomer(@Query("id_customer") id_customer : String): Call<ResponAllReservasi>

    @FormUrlEncoded
    @POST("reservasi/store")
    fun addReservasi(
        @Field("jam") waktu: String,
        @Field("layanan") layanan: String,
        @Field("tanggal") tanggal: String,
        @Field("id_customer") id_customer: Int,
    ):Call<ResponReservasi>

    @FormUrlEncoded
    @POST("login")
    fun addLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ):Call<ResponLogin>

    @DELETE("reservasi/delete/{id_reservasi}")
    fun deleteReservasi(
        @Path("id_reservasi") id_reservasi: Int
    ): Call<ResponBiasa>


    @GET("layanan/index")
    fun getLayanan(): Call<LayananModel>

    @FormUrlEncoded
    @POST("review/store")
    fun addReview(
        @Field("text") text: String,
        @Field("rating") rating: Int,
        @Field("id_reservasi") id_reservasi: Int,
    ):Call<DefaultResponse>
}