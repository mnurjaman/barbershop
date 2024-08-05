package com.example.beddabarbershop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.beddabarbershop.R
import com.example.beddabarbershop.login.SPHelper

class SplashActivity : AppCompatActivity() {
    private lateinit var spHelper: SPHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        spHelper = SPHelper(this)
        Handler().postDelayed({
            startActivity(Intent(this, GetstartedActivity::class.java))
//            if(spHelper.getStatusLogin()){
//                startActivity(Intent(this, HomeActivity::class.java))
//            }else{
//
//                startActivity(Intent(this, GetstartedActivity::class.java))
//            }
            finish()
        },2000)
    }
}