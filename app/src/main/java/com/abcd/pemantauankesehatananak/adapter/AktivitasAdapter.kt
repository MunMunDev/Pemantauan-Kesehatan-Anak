package com.abcd.pemantauankesehatananak.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.pemantauankesehatananak.R
import com.abcd.pemantauankesehatananak.data.model.AktivitasModel
import com.abcd.pemantauankesehatananak.databinding.ItemAktivitasBinding
import com.abcd.pemantauankesehatananak.databinding.ItemListAktivitasBinding
import com.abcd.pemantauankesehatananak.utils.Constant
import com.abcd.pemantauankesehatananak.utils.OnClickItem
import com.bumptech.glide.Glide
import java.net.MalformedURLException
import java.net.URL

class AktivitasAdapter(
    private val list: ArrayList<AktivitasModel>,
    private val onClick: OnClickItem.ClickAktivitas,
    private val home: Boolean
) : RecyclerView.Adapter<AktivitasAdapter.AktivitasViewHolder>() {

    inner class AktivitasViewHolder(val binding: ItemListAktivitasBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AktivitasViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListAktivitasBinding.inflate(inflater, parent, false)
        return AktivitasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AktivitasViewHolder, position: Int) {
        val aktivitas = list[position]
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

    override fun getItemCount() = if(home) 3 else list.size

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