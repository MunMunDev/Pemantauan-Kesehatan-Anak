package com.abcd.pemantauankesehatananak.ui.activity.pemeriksaan

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.abcd.pemantauankesehatananak.data.model.PelayananModel
import com.abcd.pemantauankesehatananak.databinding.ActivityDetailPemeriksaanBinding
import com.abcd.pemantauankesehatananak.utils.TanggalDanWaktu

@Suppress("DEPRECATION")
class DetailPemeriksaanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPemeriksaanBinding
    private var pelayanan: PelayananModel? = null
    private val tanggalDanWaktu = TanggalDanWaktu()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPemeriksaanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDataSebelumnya()
        setTopBar()
    }

    private fun setDataSebelumnya() {
        val i = intent
        if(i != null){
            pelayanan = i.getParcelableExtra("pemeriksaan")
            setData(pelayanan)
        }
    }

    private fun setTopBar() {
        binding.myAppBar.apply {
            tvTitle.text = pelayanan?.pelayanan
            ivNavDrawer.visibility = View.GONE
            ivBack.visibility = View.VISIBLE

            ivBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun setData(pelayanan: PelayananModel?) {
        pelayanan?.let {data->
            setDataKeterangan(data)
            setDataPemeriksaan(data)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDataKeterangan(data: PelayananModel) {
        binding.apply {
            tvPenanggungJawab.text = data.penanggung_jawab
            tvJabatan.text = data.jabatan
            data.tanggal?.let {tanggal->
                tvTanggal.text = tanggalDanWaktu.konversiBulan(tanggal)
            }
            data.waktu?.let {waktu->
                tvWaktu.text = tanggalDanWaktu.waktuNoSecond(waktu)+" WITA"
            }
        }
    }

    private fun setDataPemeriksaan(data: PelayananModel) {
        binding.apply {
            tvHasil.text = data.hasil
            tvKeterangan.text = data.keterangan
            tvCatatan.text = data.catatan
        }
    }

}