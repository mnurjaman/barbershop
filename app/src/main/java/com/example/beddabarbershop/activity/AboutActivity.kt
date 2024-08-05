package com.example.beddabarbershop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.beddabarbershop.R
import com.example.beddabarbershop.databinding.ActivityAboutBinding
import com.example.beddabarbershop.fragment.ProfilFragment

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnbackprofil()
    }

    private fun btnbackprofil() {
        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}