package com.example.beddabarbershop.login

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity

class SPHelper(context: Context) {

    val login = "Login"
    val myPref = "Main_pref"
    val sharedPreferences : SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(myPref, Context.MODE_PRIVATE)
    }

    fun getStatusLogin() : Boolean{
        return sharedPreferences.getBoolean(login, false)
    }
    fun getNama(): String?{
        return sharedPreferences.getString("nama", "")
    }

    fun getUsername(): String?{
        return sharedPreferences.getString("username", "")
    }
    fun getEmail(): String?{
        return sharedPreferences.getString("email", "")
    }
    fun getNoHp(): String?{
        return sharedPreferences.getString("no_hp", "")
    }
    fun getAlamat(): String?{
        return sharedPreferences.getString("alamat", "")
    }
    fun getIdCustomer(): String?{
        return sharedPreferences.getString("id_customer", "")
    }
    fun setIdCustomer(id_customer: String){
        sharedPreferences.edit().putString("id_customer", id_customer).apply()
    }
    fun setNama(nama: String){
        sharedPreferences.edit().putString("nama", nama).apply()
    }
    fun setUsername(username: String){
        sharedPreferences.edit().putString("username", username).apply()
    }
    fun setEmail(email: String){
        sharedPreferences.edit().putString("email", email).apply()
    }
    fun setNoHp(no_hp: String){
        sharedPreferences.edit().putString("no_hp", no_hp).apply()
    }
    fun setAlamat(alamat: String){
        sharedPreferences.edit().putString("alamat", alamat).apply()
    }
    fun setStatusLogin(status: Boolean){
        sharedPreferences.edit().putBoolean(login, status).apply()
    }
}