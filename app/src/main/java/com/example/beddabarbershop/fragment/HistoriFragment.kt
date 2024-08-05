package com.example.beddabarbershop.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beddabarbershop.adapter.HistoriAdapter
import com.example.beddabarbershop.databinding.FragmentHistoriBinding
import com.example.beddabarbershop.login.SPHelper
import com.example.beddabarbershop.model.DataReservasi
import com.example.beddabarbershop.model.ResponAllReservasi
import com.example.beddabarbershop.model.ResponReservasi
import com.example.beddabarbershop.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoriFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoriFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentHistoriBinding
    private lateinit var historiAdapter: HistoriAdapter
    private lateinit var newsArrayList : ArrayList<DataReservasi>
    private lateinit var spHelper: SPHelper

    lateinit var itemImage:ArrayList<Int>
    lateinit var itemTanggal:ArrayList<String>
    lateinit var itemWaktu:ArrayList<String>
    lateinit var itemLayanan:ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoriBinding.inflate(layoutInflater)
        spHelper = SPHelper(requireActivity())
        dataInitialize()

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoriFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoriFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun dataInitialize(){
        newsArrayList = ArrayList()

//        newsArrayList.add(
//            ResponReservasi(
//                R.drawable.time_reservasi,
//                "12-12-2022",
//                "10:00",
//                "Hair Cut",
//
//            )
//        )
//        newsArrayList.add(
//            ResponReservasi(
//                R.drawable.time_reservasi,
//                "12-12-2022",
//                "11:00",
//                "Hair Cut",
//
//            )
//        )
//
//        newsArrayList.add(
//            ResponReservasi(
//                R.drawable.time_reservasi,
//                "12-12-2022",
//                "12:00",
//                "Hair Cut",
//
//            )
//        )
//        newsArrayList.add(
//            ResponReservasi(
//                R.drawable.time_reservasi,
//                "13-12-2022",
//                "13:00",
//                "Hair Cut",
//
//                )
//        )
//        newsArrayList.add(
//            ResponReservasi(
//                R.drawable.time_reservasi,
//                "14-12-2022",
//                "14:00",
//                "Hair Cut",
//
//                )
//        )
//        newsArrayList.add(
//            ResponReservasi(
//                R.drawable.time_reservasi,
//                "15-12-2022",
//                "15:00",
//                "Hair Cut",
//
//                )
//        )

        historiAdapter = HistoriAdapter(requireActivity(),newsArrayList)

        ApiClient.apiService.getReservasiByIdCustomer(spHelper.getIdCustomer()!!).enqueue(object :
            Callback<ResponAllReservasi> {
            override fun onResponse(
                call: Call<ResponAllReservasi>,
                response: Response<ResponAllReservasi>
            ) {
                if(response.isSuccessful){
                    val data = response.body()
                    if(data != null){
                        newsArrayList.addAll(data.dataReservasi)
                        historiAdapter = HistoriAdapter(requireActivity(),newsArrayList)
                        binding.recyclerView.adapter = historiAdapter
                        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
                        binding.recyclerView.setHasFixedSize(true)
                        historiAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<ResponAllReservasi>, t: Throwable) {

            }
        })
    }
}