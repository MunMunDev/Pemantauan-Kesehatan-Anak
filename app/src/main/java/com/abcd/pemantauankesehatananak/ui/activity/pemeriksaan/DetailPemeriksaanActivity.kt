package com.abcd.pemantauankesehatananak.ui.activity.pemeriksaan

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.abcd.pemantauankesehatananak.data.model.PelayananModel
import com.abcd.pemantauankesehatananak.databinding.ActivityDetailPemeriksaanBinding

@Suppress("DEPRECATION")
class DetailPemeriksaanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPemeriksaanBinding
    private var pelayanan: PelayananModel? = null
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
        }
    }

    private fun setData(pelayanan: PelayananModel?) {
        pelayanan?.let {data->
            binding.apply {
                tvHasil.text = data.hasil
                tvKeterangan.text = data.keterangan
                tvCatatan.text = data.catatan
            }
        }
    }

}