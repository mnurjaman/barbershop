package com.example.beddabarbershop.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.beddabarbershop.*
import com.example.beddabarbershop.adapter.HistoriAdapter
import com.example.beddabarbershop.config.SdkConfig
import com.example.beddabarbershop.databinding.ActivityHomeBinding
import com.example.beddabarbershop.fragment.HistoriFragment
import com.example.beddabarbershop.fragment.HomeFragment
import com.example.beddabarbershop.fragment.ProfilFragment
import com.example.beddabarbershop.fragment.ReservasiFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder

class HomeActivity : AppCompatActivity(), TransactionFinishedCallback {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //isi content//
        val fragment = HomeFragment()
        binding.bottomNav.setOnNavigationItemSelectedListener(menuItemSelected)
        addFragment(fragment)
        initMidtransSdk()
    }

    //deteksi Menu Item yang di Klik//
    private val menuItemSelected = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.itemhome ->{
                val fragment = HomeFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemreservasi ->{
                val fragment = ReservasiFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemprofil ->{
                val fragment = ProfilFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemhistori ->{
                val fragment = HistoriFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun addFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(com.google.android.material.R.anim.design_bottom_sheet_slide_in, com.google.android.material.R.anim.design_bottom_sheet_slide_out )
            .replace(R.id.content, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }

    private fun initMidtransSdk() {
        val clientKey: String = SdkConfig.MERCHANT_CLIENT_KEY
        val baseUrl: String = SdkConfig.MERCHANT_BASE_CHECKOUT_URL
        val sdkUIFlowBuilder: SdkUIFlowBuilder = SdkUIFlowBuilder.init()
            .setClientKey(clientKey) // client_key is mandatory
            .setContext(this) // context is mandatory
            .setTransactionFinishedCallback(this) // set transaction finish callback (sdk callback)
            .setMerchantBaseUrl(baseUrl) //set merchant url
            .enableLog(true) // enable sdk log
            .setColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255")) // will replace theme on snap theme on MAP
            .setLanguage("en")
        sdkUIFlowBuilder.buildSDK()
    }

    override fun onTransactionFinished(p0: TransactionResult?) {
        when {
            p0?.response != null -> {

                when(p0.status){
                    TransactionResult.STATUS_SUCCESS -> {
                        Log.e("ENOG", "DONE : ${p0.response.transactionStatus} | ${p0.response.transactionId}")
                        toast("Payment Successfully")
                    }
                    TransactionResult.STATUS_PENDING -> {
                        Log.e("ENOG", "PENDING : ${p0.response.transactionStatus} | ${p0.response.transactionId}")
                        toast("Payment Pending")
                    }
                    TransactionResult.STATUS_FAILED -> {
                        Log.e("ENOG", "FAILED : ${p0.response.transactionStatus} | ${p0.response.statusMessage}")
                        toast("Payment Failed")
                    }
                    TransactionResult.STATUS_INVALID -> {
                        Log.e("ENOG", "INVALID : ${p0.response.transactionStatus} | ${p0.response.statusMessage}")
                        toast("Payment Invalid")
                    }
                }

            }
            p0?.isTransactionCanceled == true -> {
                toast("Transaction Cancelled")
            }
            else -> {
                toast("Transaction Failed")
            }
        }
    }

    private fun toast(text: String){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }


}