package com.example.beddabarbershop.adapter

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.provider.MediaStore.Audio.Radio
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.beddabarbershop.R
import com.example.beddabarbershop.config.SdkConfig
import com.example.beddabarbershop.databinding.AddReviewDialogBinding
import com.example.beddabarbershop.login.SPHelper
import com.example.beddabarbershop.model.DataReservasi
import com.example.beddabarbershop.model.DefaultResponse
import com.example.beddabarbershop.model.ResponBiasa
import com.example.beddabarbershop.retrofit.ApiClient
import com.google.android.material.imageview.ShapeableImageView
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ItemDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.text.SimpleDateFormat

class HistoriAdapter(
    private val context: Activity,
    private val newList: ArrayList<DataReservasi>):
        RecyclerView.Adapter<HistoriAdapter.RecyclerViewHolder>(){

    val spHelper = SPHelper(context)

    class RecyclerViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val itemImage:ShapeableImageView = itemView.findViewById(R.id.item_image)
        //        val itemCustomer:TextView = itemView.findViewById(R.id.item_customer)
        val itemTanggal:TextView = itemView.findViewById(R.id.item_tanggal)
        val itemReview:TextView = itemView.findViewById(R.id.item_review)
        val itemLayanan:TextView = itemView.findViewById(R.id.item_layanan)
        val itemHarga:TextView = itemView.findViewById(R.id.item_harga)
        val itemStatus:TextView = itemView.findViewById(R.id.item_status)
        val cvMain:CardView = itemView.findViewById(R.id.card_view)
        val btnBatal:Button = itemView.findViewById(R.id.btn_batal)
        val btnBayar:Button = itemView.findViewById(R.id.btn_bayar)
        val btnReview:Button = itemView.findViewById(R.id.btn_review)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_histori,parent,false)
        return RecyclerViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentItem = newList[position]
//        holder.itemImage.setImageResource(currentItem.itemImage)
        val parser = SimpleDateFormat("yyyy-MM-dd")
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        val dateFormated = formatter.format(parser.parse(currentItem.tanggal));


        holder.itemTanggal.text = "Tanggal : " + dateFormated + " " + currentItem.jam
        holder.itemLayanan.text = "Layanan : " + currentItem.layanan
        holder.itemHarga.text = "Harga : Rp. " + NumberFormat.getInstance().format(currentItem.harga_layanan)
        holder.itemReview.text = "Review : Rating " + currentItem.review_rating + " / " + currentItem.review_Text

        /*
        0 = Diproses Admin,
        1 = Disetujui - Menunggu Pembayaran,
        2 = Ditolak,
        3 = Sudah Dibayar,
        4 = Sudah Dikirim,
        5 = Sudah Diterima Konsumen,
        6 = Pembayaran Pending,
        7 = Pembayaran Deny,
        */

        var status = "Status : "
        with(currentItem.status){
            if(this == "0"){
                status += "Diproses"
                holder.btnBatal.visibility = View.VISIBLE
            }else if(this == "1"){
                status += "Disetujui"
                holder.btnBayar.visibility = View.VISIBLE
            }else if(this == "2"){
                status += "Ditolak"
            }else if(this == "3"){
                status += "Sudah Dibayar"
            }else if(this == "4"){
                status += "Sudah Dikerjakan"
                holder.btnReview.visibility = View.VISIBLE
            }else if(this == "5"){
                status += "Sudah Direview"
                holder.itemReview.visibility = View.VISIBLE
            }else if(this == "6"){
                status += "Pembayaran Pending"
            }else if(this == "7"){
                status += "Disetujui - Menunggu Pembayaran"
            }else {
                status += ""
            }
        }

        holder.itemStatus.text = status

        holder.btnBatal.setOnClickListener {
            ApiClient.apiService.deleteReservasi(currentItem.idReservasi.toInt()).enqueue(object :
                Callback<ResponBiasa> {
                override fun onResponse(call: Call<ResponBiasa>, response: Response<ResponBiasa>) {
                    if(response.isSuccessful){
                        val data = response.body()
                        Toast.makeText(context, "" + data!!.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponBiasa>, t: Throwable) {
                    Toast.makeText(context, "" + t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

        holder.btnBayar.setOnClickListener {
            setupOrders(currentItem)
        }

        holder.btnReview.setOnClickListener{
            val dialogView = context.layoutInflater.inflate(R.layout.add_review_dialog,null)
            val dialogBinding: AddReviewDialogBinding = AddReviewDialogBinding.bind(dialogView)
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setView(dialogView)
            alertDialog.setTitle("Review Layanan")
            alertDialog.setMessage("Puas dengan layanan kami?")
            alertDialog.setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface, i ->
                val text = dialogBinding.etReview.text.toString()
                if(text.isEmpty()){
                    toast("Tambahkan Review!")
                    return@OnClickListener
                }
                val selectedId = dialogBinding.rgRating.checkedRadioButtonId

                val radioButton = dialogView.findViewById<RadioButton>(selectedId)
                val ratingText = radioButton.text.toString().toInt()

                tambahReview(text, ratingText, currentItem.idReservasi.toInt())
            })
            alertDialog.create().show()
        }
    }

    override fun getItemCount(): Int {
        return newList.size
    }

    private fun setupOrders(reservasiModel: DataReservasi) {

        // fake customer details

        val customerDetails = CustomerDetails()
        customerDetails.firstName = "" + spHelper.getNama()
        customerDetails.lastName = ""
        customerDetails.email = spHelper.getEmail().toString()
        customerDetails.phone = spHelper.getNoHp().toString()
        customerDetails.customerIdentifier = spHelper.getIdCustomer().toString()

        val shippingAddress = ShippingAddress()
        shippingAddress.address = spHelper.getAlamat().toString()
        shippingAddress.city = "Jogja"
        shippingAddress.postalCode = "55581"
        customerDetails.shippingAddress = shippingAddress

        val billingAddress = BillingAddress()
        billingAddress.address = spHelper.getAlamat().toString()
        billingAddress.city = "Jogja"
        billingAddress.postalCode = "55581"
        customerDetails.billingAddress = billingAddress

        val itemDetails = mutableListOf<ItemDetails>()
        itemDetails.add(
            ItemDetails(reservasiModel.idReservasi, reservasiModel.harga_layanan.toDouble(), 1, reservasiModel.layanan)
        )


        val transactionRequest = TransactionRequest("jogsunset-${reservasiModel.idReservasi}", reservasiModel.harga_layanan.toDouble())

        transactionRequest.customerDetails = customerDetails
        transactionRequest.itemDetails = itemDetails as ArrayList<ItemDetails>?
        transactionRequest.customField1 = reservasiModel.idReservasi

        MidtransSDK.getInstance().transactionRequest = transactionRequest
        MidtransSDK.getInstance().startPaymentUiFlow(context)

        Log.e("ENOG", "starting orders...")
    }

    private fun tambahReview(text: String, rating: Int, id_reservasi: Int){
        ApiClient.apiService.addReview(text, rating, id_reservasi).enqueue(object: Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                if(response.isSuccessful){
                    val data = response.body()!!
                    toast(data.message)
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                t.message?.let { toast(it) }
            }
        })
    }
    private fun toast(text: String){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
    
}
