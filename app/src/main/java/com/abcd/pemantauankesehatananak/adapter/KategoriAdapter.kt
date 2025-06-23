package com.abcd.pemantauankesehatananak.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.pemantauankesehatananak.data.model.KategoriModel
import com.abcd.pemantauankesehatananak.databinding.ItemListKategoriBinding
import com.abcd.pemantauankesehatananak.utils.OnClickItem

class KategoriAdapter(
    private val listKategori: ArrayList<KategoriModel>,
    private val onClick: OnClickItem.ClickKategori,
) : RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder>() {

    inner class KategoriViewHolder(val binding: ItemListKategoriBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListKategoriBinding.inflate(inflater, parent, false)
        return KategoriViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KategoriViewHolder, position: Int) {
        val kategori = listKategori[position]
        holder.apply {
            binding.apply {
                tvJudul.text = kategori.kategori
            }
            itemView.setOnClickListener {
                onClick.clickKategori(kategori)
            }
        }

    }

    override fun getItemCount() = listKategori.size

}