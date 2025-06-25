package com.abcd.pemantauankesehatananak.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.pemantauankesehatananak.data.model.RiwayatAktivitasModel
import com.abcd.pemantauankesehatananak.databinding.ItemListRiwayatAktivitasBinding
import com.abcd.pemantauankesehatananak.utils.TanggalDanWaktu

class RiwayatAktivitasAdapter(
    private val listRiwayatAktivitas: List<RiwayatAktivitasModel>
) : RecyclerView.Adapter<RiwayatAktivitasAdapter.RiwayatAktivitasViewHolder>() {
    private val tanggalDanWaktu = TanggalDanWaktu()

    inner class RiwayatAktivitasViewHolder(val binding: ItemListRiwayatAktivitasBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiwayatAktivitasViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListRiwayatAktivitasBinding.inflate(inflater, parent, false)
        return RiwayatAktivitasViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RiwayatAktivitasViewHolder, position: Int) {
        val riwayatAktivitas = listRiwayatAktivitas[position]
        holder.binding.apply {
            val tanggal = tanggalDanWaktu.konversiBulan(riwayatAktivitas.tanggal!!)
            val waktu = "${tanggalDanWaktu.waktuNoSecond(riwayatAktivitas.waktu!!)} WITA"
            val vTanggalDanWaktu = "$tanggal - $waktu"
            val vPosition = position+1

            tvNo.text = "$vPosition"
            tvTanggal.text = vTanggalDanWaktu
        }
    }

    override fun getItemCount() = listRiwayatAktivitas.size
}