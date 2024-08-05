package com.example.beddabarbershop.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.beddabarbershop.activity.AboutActivity
import com.example.beddabarbershop.activity.EditPasswordActivity
import com.example.beddabarbershop.activity.LoginActivity
import com.example.beddabarbershop.databinding.FragmentProfilBinding
import com.example.beddabarbershop.login.SPHelper
import com.github.drjacky.imagepicker.ImagePicker
import com.github.drjacky.imagepicker.constant.ImageProvider
import com.squareup.picasso.Picasso


class ProfilFragment : Fragment() {
    lateinit var spHelper: SPHelper
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentProfilBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // memanggil data yang ada pada database
        binding = FragmentProfilBinding.inflate(layoutInflater)

        spHelper = SPHelper(requireActivity())
        binding.tvName.text = spHelper.getNama()
        binding.tvEmail.text = spHelper.getEmail()
        binding.tvUsername.text = spHelper.getUsername()
        binding.tvTlp.text = spHelper.getNoHp()

        initSp()
        logout()
        var intent: Intent?
//        binding.btnEditpsswd.setOnClickListener {
//            startActivity(Intent(requireActivity(), EditPasswordActivity::class.java))
//        }

        binding.btnAbout.setOnClickListener {
            startActivity(Intent(requireActivity(), AboutActivity::class.java))
        }

        binding.imgprofil.setOnClickListener {
            pickImage()
        }

        return binding.root
        }

    //ketika user mengganti image
    private fun pickImage(){
        ImagePicker.with(requireActivity())
            .maxResultSize(1080,1080, true)
            .createIntentFromDialog { launcher.launch(it) }

    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                Picasso.get().load(uri).into(binding.imgprofil)
            }
        }

    //ketika exit di klik maka akan kembali ke halaman login
    private fun logout() {
        binding.ivExit.setOnClickListener {
            sharedPreferences.edit().clear().commit()
            val i = Intent(requireActivity(), LoginActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
            requireActivity().finish()
        }
    }

    fun initSp(){
        sharedPreferences = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
        }
    }




//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment HistoriFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ProfilFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//
//    }

