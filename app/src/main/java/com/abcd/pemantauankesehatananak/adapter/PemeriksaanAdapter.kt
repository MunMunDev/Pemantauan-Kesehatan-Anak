package com.abcd.pemantauankesehatananak.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.pemantauankesehatananak.data.model.PelayananModel
import com.abcd.pemantauankesehatananak.databinding.ItemListPemeriksaanBinding
import com.abcd.pemantauankesehatananak.ui.activity.user.main.MainActivity
import com.abcd.pemantauankesehatananak.utils.TanggalDanWaktu

class PemeriksaanAdapter(
    private val listPemeriksaan: ArrayList<PelayananModel>,
) : RecyclerView.Adapter<PemeriksaanAdapter.PemeriksaanViewHolder>() {
    private val tanggalDanWaktu = TanggalDanWaktu()

    private var tempPemeriksaan = listPemeriksaan
    @SuppressLint("NotifyDataSetChanged", "DefaultLocale")
    fun searchData(kata: String){
        val vKata = kata.lowercase().trim()
        val data = listPemeriksaan.filter {
            (
                it.pelayanan!!.lowercase().trim().contains(vKata)
                or
                it.hasil!!.lowercase().trim().contains(vKata)
                or
                it.tanggal!!.lowercase().trim().contains(vKata)
                or
                it.waktu!!.lowercase().trim().contains(vKata)
            )
        }
        tempPemeriksaan = data as ArrayList<PelayananModel>
        notifyDataSetChanged()
    }

    inner class PemeriksaanViewHolder(val binding: ItemListPemeriksaanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PemeriksaanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListPemeriksaanBinding.inflate(inflater, parent, false)
        return PemeriksaanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PemeriksaanViewHolder, position: Int) {
        val pemeriksaan = tempPemeriksaan[position]
        holder.apply {
            binding.apply {
                val tanggal = tanggalDanWaktu.konversiBulan(pemeriksaan.tanggal!!)
                val waktu = tanggalDanWaktu.waktuNoSecond(pemeriksaan.waktu!!)
                val tanggalWaktu = "$tanggal - $waktu"

                tvPelayanan.text = pemeriksaan.pelayanan
                tvHasil.text = pemeriksaan.hasil
                tvTanggalWaktu.text = tanggalWaktu
            }

            itemView.setOnClickListener {
                val i = Intent(itemView.context, MainActivity::class.java)
                i.putExtra("pemeriksaan", pemeriksaan)
                itemView.context.startActivity(i)
            }

        }

    }

    override fun getItemCount() = tempPemeriksaan.size
}