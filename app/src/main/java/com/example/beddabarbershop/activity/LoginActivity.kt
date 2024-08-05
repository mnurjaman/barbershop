package com.example.beddabarbershop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.beddabarbershop.R
import com.example.beddabarbershop.databinding.ActivityEditPasswordBinding
import com.example.beddabarbershop.databinding.ActivityLoginBinding
import com.example.beddabarbershop.fragment.HomeFragment
import com.example.beddabarbershop.login.SPHelper
import com.example.beddabarbershop.model.ResponLogin
import com.example.beddabarbershop.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var spHelper: SPHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        spHelper = SPHelper(this)

        binding.btnlogin.setOnClickListener {
            login()
        }
    }
    // login customer
    private fun login() {
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        if (username.isEmpty()){
            binding.etUsername.error = "Username Tidak Boleh Kosong!"
            return
        }
        if (password.isEmpty()){
            binding.etPassword.error = "Password Tidak Boleh Kosong!"
        }

        ApiClient.apiService.addLogin(username,password)
            .enqueue(object : Callback<ResponLogin>{
                override fun onResponse(call: Call<ResponLogin>, response: Response<ResponLogin>) {
                    val response = response.body()

                    Log.d("aaaaa", "" + response)

                    if (response != null){
                        if (response.status == false){
                            Toast.makeText(this@LoginActivity, response.message, Toast.LENGTH_SHORT).show()

                        }else{
                            val dt = response.customer
                            spHelper.setStatusLogin(true)
                            spHelper.setIdCustomer(dt.idCustomer)
                            spHelper.setNama(dt.nama)
                            spHelper.setEmail(dt.email)
                            spHelper.setNoHp(dt.noHp)
                            spHelper.setAlamat(dt.alamat)
                            spHelper.setUsername(dt.username)

                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        }
                    }
                }

                override fun onFailure(call: Call<ResponLogin>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, t.localizedMessage, Toast.LENGTH_LONG).show()
                    Log.d("Errorcok", t.localizedMessage + t.stackTraceToString())
                }
            })
    }
}