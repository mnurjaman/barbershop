package com.example.beddabarbershop.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.beddabarbershop.R
import com.example.beddabarbershop.adapter.HomeAdapter
import com.example.beddabarbershop.databinding.FragmentHomeBinding
import com.example.beddabarbershop.model.HomeModel

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var newsArrayList : ArrayList<HomeModel>

    lateinit var img_Model:ArrayList<Int>
    lateinit var item_Jenismodel:ArrayList<String>
    lateinit var item_Modelrambut:ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        dataInitialize()

        return binding.root
    }


    private fun dataInitialize(){
        newsArrayList = ArrayList()

        newsArrayList.add(
            HomeModel(
                R.drawable.undercut,
                "Model Rambut",
                "Undercut",

            )
        )

        newsArrayList.add(
            HomeModel(
                R.drawable.pompadour,
                "Model Rambut",
                "Pompadour",

                )
        )
        newsArrayList.add(
            HomeModel(
                R.drawable.commahair,
                "Model Rambut",
                "Comma Hair",

                )
        )
        newsArrayList.add(
            HomeModel(
                R.drawable.buzzcut,
                "Model Rambut",
                "Buzz Cut",

                )
        )
        newsArrayList.add(
            HomeModel(
                R.drawable.combover,
                "Model Rambut",
                "Comb Over",

                )
        )
        newsArrayList.add(
            HomeModel(
                R.drawable.lowfade,
                "Model Rambut",
                "Low fade",

                )
        )
        newsArrayList.add(
            HomeModel(
                R.drawable.twoblocks,
                "Model Rambut",
                "Two Block",

                )
        )
        newsArrayList.add(
            HomeModel(
                R.drawable.caesarcut,
                "Model Rambut",
                "Caesar Cut",

                )
        )
        newsArrayList.add(
            HomeModel(
                R.drawable.combover,
                "Model Rambut",
                "Comb Over",

                )
        )
        newsArrayList.add(
            HomeModel(
                R.drawable.cepmek,
                "Model Rambut",
                "Cepmek",

                )
        )

        homeAdapter = HomeAdapter(newsArrayList)
        binding.recyclerview.adapter = homeAdapter
        binding.recyclerview.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.recyclerview.setHasFixedSize(true)
        homeAdapter.notifyDataSetChanged()
    }
}
