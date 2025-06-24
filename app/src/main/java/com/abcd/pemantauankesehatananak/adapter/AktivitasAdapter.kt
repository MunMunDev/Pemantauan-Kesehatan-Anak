package com.abcd.pemantauankesehatananak.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.pemantauankesehatananak.R
import com.abcd.pemantauankesehatananak.data.model.AktivitasModel
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.databinding.ItemListAktivitasBinding
import com.abcd.pemantauankesehatananak.utils.OnClickItem
import com.bumptech.glide.Glide

class AktivitasAdapter(
    private val listAktivitas: ArrayList<AktivitasModel>,
    private val onClick: OnClickItem.ClickAktivitas,
    private val home: Boolean
) : RecyclerView.Adapter<AktivitasAdapter.AktivitasViewHolder>() {

    private var tempAktivitas = listAktivitas
    private var tempAktivitas2 = tempAktivitas
    @SuppressLint("NotifyDataSetChanged", "DefaultLocale")
    fun searchData(kata: String){
        val vKata = kata.lowercase().trim()
        val data = listAktivitas.filter {
            (
                it.judul!!.lowercase().trim().contains(vKata)
                or
                it.deskripsi!!.lowercase().trim().contains(vKata)
//                or
//                it.kategori?.kategori!!.lowercase().trim().contains(vKata)
                or
                it.usia_minimal!!.lowercase().trim().contains(vKata)
            )
        }
        tempAktivitas = data as ArrayList<AktivitasModel>
        tempAktivitas2 = tempAktivitas
        notifyDataSetChanged()
    }

    fun searchKategori(kata: String){
        val vKata = kata.lowercase().trim()
        val data = tempAktivitas.filter {
            (
                it.kategori?.kategori!!.lowercase().trim().contains(vKata)
            )
        }
        tempAktivitas2 = data as ArrayList<AktivitasModel>
        notifyDataSetChanged()
    }

    inner class AktivitasViewHolder(val binding: ItemListAktivitasBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AktivitasViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListAktivitasBinding.inflate(inflater, parent, false)
        return AktivitasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AktivitasViewHolder, position: Int) {
        val aktivitas = tempAktivitas2[position]
        holder.apply {
            binding.apply {
                tvJudul.text = aktivitas.judul
                tvDeskripsi.text = aktivitas.deskripsi
                tvKategori.text = aktivitas.kategori?.kategori
                tvUsia.text = "${aktivitas.usia_minimal} Bulan"

                val videoId = searchIdUrlVideo(aktivitas.video_url!!)
                val imgUrl = "https://img.youtube.com/vi/$videoId/0.jpg"

                Glide
                    .with(holder.itemView)
                    .load(imgUrl)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_image_error2)
                    .into(ivGambar)
            }

            itemView.setOnClickListener {
                onClick.clickAktivitas(aktivitas)
            }

        }

    }

    override fun getItemCount() = if(home) 3 else tempAktivitas2.size

    private fun searchIdUrlVideo(urlVideo: String): String {
        var url = ""
        try {
            val arrayUrlIdVideo = urlVideo.split("v=")
            url = arrayUrlIdVideo[1]

            try {
                val arraySearchUrlSymbol = url.split("&")
                url = arraySearchUrlSymbol[0]
            } catch (_: Exception){
                url = arrayUrlIdVideo[1]
            }
        } catch (ex: Exception){
            try {
                val arrayUrlIdVideo = urlVideo.split("si=")
                url = arrayUrlIdVideo[1]
            } catch (ex: Exception){
                url = "0"
            }
        }
        return url
    }
}