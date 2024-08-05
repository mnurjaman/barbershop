package com.example.beddabarbershop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.beddabarbershop.R
import com.example.beddabarbershop.databinding.ActivityAboutBinding
import com.example.beddabarbershop.databinding.ActivityEditPasswordBinding
import com.example.beddabarbershop.databinding.FragmentProfilBinding
import com.example.beddabarbershop.fragment.ProfilFragment
class EditPasswordActivity : AppCompatActivity() {


    private lateinit var binding: ActivityEditPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnback()
    }

    private fun btnback() {
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

    }

}