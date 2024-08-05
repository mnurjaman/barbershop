package com.example.beddabarbershop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.beddabarbershop.R
import com.example.beddabarbershop.login.SPHelper

class GetstartedActivity : AppCompatActivity() {
    private lateinit var spHelper: SPHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_getstarted)

        spHelper = SPHelper(this)
        val Proses = findViewById<Button>(R.id.btnstarted)
        Proses.setOnClickListener {
            if(spHelper.getStatusLogin()){
                startActivity(Intent(this, HomeActivity::class.java))
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
            }

        }
    }
}