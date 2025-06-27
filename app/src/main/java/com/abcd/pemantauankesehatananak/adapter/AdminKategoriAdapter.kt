package com.abcd.pemantauankesehatananak.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.pemantauankesehatananak.R
import com.abcd.pemantauankesehatananak.data.model.KategoriModel
import com.abcd.pemantauankesehatananak.databinding.ListAdminKategoriBinding
import com.abcd.pemantauankesehatananak.utils.OnClickItem

class AdminKategoriAdapter(
    private var listKategori: ArrayList<KategoriModel>,
    private var onClick: OnClickItem.ClickAdminKategori
): RecyclerView.Adapter<AdminKategoriAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListAdminKategoriBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListAdminKategoriBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return (listKategori.size+1)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            if(position==0){
                tvNo.text = "NO"
                tvKategori.text = "Kategori"
                tvDeskripsi.text = "Deskripsi"
                tvSetting.text = ""

                tvNo.setBackgroundResource(R.drawable.bg_table_title)
                tvKategori.setBackgroundResource(R.drawable.bg_table_title)
                tvDeskripsi.setBackgroundResource(R.drawable.bg_table_title)
                tvSetting.setBackgroundResource(R.drawable.bg_table_title)

                tvNo.setTextColor(Color.parseColor("#ffffff"))
                tvKategori.setTextColor(Color.parseColor("#ffffff"))
                tvDeskripsi.setTextColor(Color.parseColor("#ffffff"))
                tvSetting.setTextColor(Color.parseColor("#ffffff"))

                tvNo.setTypeface(null, Typeface.BOLD)
                tvKategori.setTypeface(null, Typeface.BOLD)
                tvDeskripsi.setTypeface(null, Typeface.BOLD)
                tvSetting.setTypeface(null, Typeface.BOLD)
            }
            else{
                val kategori = listKategori[(position-1)]

                tvNo.text = "$position"
                tvKategori.text = kategori.kategori
                tvDeskripsi.text = kategori.deskripsi
                tvSetting.text = ":::"

                tvNo.setBackgroundResource(R.drawable.bg_table)
                tvKategori.setBackgroundResource(R.drawable.bg_table)
                tvDeskripsi.setBackgroundResource(R.drawable.bg_table)
                tvSetting.setBackgroundResource(R.drawable.bg_table)

                tvNo.setTextColor(Color.parseColor("#000000"))
                tvKategori.setTextColor(Color.parseColor("#000000"))
                tvDeskripsi.setTextColor(Color.parseColor("#000000"))
                tvSetting.setTextColor(Color.parseColor("#000000"))

                tvNo.setTypeface(null, Typeface.NORMAL)
                tvKategori.setTypeface(null, Typeface.NORMAL)
                tvDeskripsi.setTypeface(null, Typeface.NORMAL)
                tvSetting.setTypeface(null, Typeface.NORMAL)

                tvDeskripsi.setOnClickListener {
                    onClick.clickDeskripsi(kategori.deskripsi!!)
                }
                tvSetting.setOnClickListener {
                    onClick.clickSetting(kategori, it)
                }
            }
        }
    }
}