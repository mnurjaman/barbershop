package com.example.beddabarbershop.fragment

import android.R
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.beddabarbershop.activity.HomeActivity
import com.example.beddabarbershop.databinding.FragmentReservasiBinding
import com.example.beddabarbershop.login.SPHelper
import com.example.beddabarbershop.model.LayananModel
import com.example.beddabarbershop.model.ResponReservasi
import com.example.beddabarbershop.model.SingleLayananModel
import com.example.beddabarbershop.retrofit.ApiClient
import com.google.android.material.chip.Chip
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ReservasiFragment : Fragment() {
    private lateinit var binding: FragmentReservasiBinding
    private lateinit var spHelper: SPHelper
//    private lateinit var homeBinding: FragmentReservasiBinding

    var jam: String = "10:00"
    var layanan: String = ""
    var tanggal: String = ""

    var id_layanan: Int = 999

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //fungsi  jam untuk menampilkan jam yang akan dipilih
        binding = FragmentReservasiBinding.inflate(layoutInflater)

        spHelper = SPHelper(requireActivity())

        getLayanan()

        tanggal = SimpleDateFormat("yyyy-MM-dd").format(Date())

        binding.tvdate.text = tanggal


        binding.imgdate.setOnClickListener {
            showDatePicker(it, Any())
        }
        binding.tvdate.setOnClickListener {
            showDatePicker(it, Any())
        }

        binding.cgJam.setOnCheckedStateChangeListener { group, checkedIds ->
            val ids = group.checkedChipIds
            for (id in ids) {
                val chip: Chip = group.findViewById(id!!)
                val text = chip.text.toString()
                jam = text
                Toast.makeText(requireActivity(), chip.text, Toast.LENGTH_SHORT).show()
            }
        }

        binding.spLayanan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                layanan = binding.spLayanan.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(requireActivity(), "Invalid", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnreservasi.setOnClickListener {
            addData()
        }


        return binding.root

    }
    //add reservasi
    private fun addData() {
        val IdCostumer = spHelper.getIdCustomer().toString().toInt()
        val Status = 0


        ApiClient.apiService.addReservasi(jam, layanan, tanggal, IdCostumer)
            .enqueue(object : Callback<ResponReservasi>{
                override fun onResponse(
                    call: Call<ResponReservasi>,
                    response: Response<ResponReservasi>
                ) {
                    val response = response.body()
                    if (response != null){
                        if (!response.status){
                            Toast.makeText(requireActivity(), "" + response.message, Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(requireActivity(), "Reservasi Berhasil!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(requireActivity(), HomeActivity::class.java))
                        }
                    }
                }

                override fun onFailure(call: Call<ResponReservasi>, t: Throwable) {
                    Toast.makeText(requireActivity(), "Gagal : " + t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }


    //fungsi DatePicker untuk menampilkan tanggal, waktu dan tahun yang akan dipilih
    fun showDatePicker(view: View, cal: Any){
        val call    = Calendar.getInstance(Locale.ENGLISH)
        val year    = cal.get(Calendar.YEAR)
        val month   = cal.get((Calendar.MONTH))
        val day     = cal.get((Calendar.DAY_OF_MONTH))

        val curYear = call.get(Calendar.YEAR)

        val dialog = DatePickerDialog(requireActivity(), {_,y , m, d ->

            if(y < curYear){
                Toast.makeText(requireActivity(), "Invalid Year!", Toast.LENGTH_SHORT).show()
            }else{
                binding.tvdate.text ="${d} ${DateFormatSymbols().months[m]} ${y}"
                tanggal = "${y}-${m+1}-${d}"
                Toast.makeText(requireActivity(), ""+tanggal, Toast.LENGTH_SHORT).show()
            }
                                            }, curYear, 0, 1)
        dialog.datePicker.minDate = System.currentTimeMillis() - 1000
        dialog.show()


    }

    fun getLayanan(){
        ApiClient.apiService.getLayanan().enqueue(object : Callback<LayananModel> {
            override fun onResponse(call: Call<LayananModel>, response: Response<LayananModel>) {
                if(response.isSuccessful){
                    val data = response.body()
                    if(data != null){
                        val layananItems: ArrayList<SingleLayananModel> = data.data!!
                        val listSpinner: MutableList<String> = ArrayList()
                        for (i in layananItems) {
                            listSpinner.add("" + i.nama_layanan + " - " + NumberFormat.getInstance().format(i.harga_layanan))
                        }
                        val adapter = ArrayAdapter<String>(
                            requireActivity(),
                            R.layout.simple_spinner_item, listSpinner
                        )
                        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                        binding.spLayanan.setAdapter(adapter)
                    }


                }
            }

            override fun onFailure(call: Call<LayananModel>, t: Throwable) {
                Toast.makeText(requireActivity(), "Gagal : " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}

private fun Calendar.set(hourOfDay: Int) {
    }

private fun Any.get(year: Int): Any {
    return year
}







