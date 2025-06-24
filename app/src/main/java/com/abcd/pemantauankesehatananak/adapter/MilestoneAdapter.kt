package com.abcd.pemantauankesehatananak.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.databinding.ItemListMilestoneBinding
import com.abcd.pemantauankesehatananak.utils.OnClickItem

class MilestoneAdapter(
    private val listMilestone: List<MilestoneModel>,
    private val onClick: OnClickItem.ClickMilestone,
    private val home: Boolean
) : RecyclerView.Adapter<MilestoneAdapter.MilestoneViewHolder>() {

    private var tempMilestone = listMilestone
    private var tempMilestone2 = tempMilestone

    @SuppressLint("NotifyDataSetChanged", "DefaultLocale")
    fun searchData(kata: String){
        val vKata = kata.lowercase().trim()
        val data = listMilestone.filter {
            (
                it.deskripsi!!.lowercase().trim().contains(vKata)
                or
                it.kategori?.kategori!!.lowercase().trim().contains(vKata)
                or
                it.usia_ideal!!.toString().contains(vKata)
            )
        }
        tempMilestone = data as ArrayList<MilestoneModel>
        tempMilestone2 = tempMilestone
        notifyDataSetChanged()
    }

    fun searchKategori(kata: String){
        val vKata = kata.lowercase().trim()
        val data = tempMilestone.filter {
            (
                it.kategori?.kategori!!.lowercase().trim().contains(vKata)
            )
        }
        tempMilestone2 = data as ArrayList<MilestoneModel>
        notifyDataSetChanged()
    }

    inner class MilestoneViewHolder(val binding: ItemListMilestoneBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MilestoneViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListMilestoneBinding.inflate(inflater, parent, false)
        return MilestoneViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MilestoneViewHolder, position: Int) {
        val milestone = tempMilestone2[position]
        holder.binding.apply {
            tvDeskripsi.text = milestone.deskripsi?.trim()
            tvKategori.text = milestone.kategori?.kategori
            tvUsia.text = milestone.usia_ideal?.toString()+" Bulan"
            cbMilestone.setOnCheckedChangeListener(null)
            cbMilestone.isChecked = milestone.sudah_tercapai == 1

            cbMilestone.setOnCheckedChangeListener { _, isChecked ->
                onClick.clickMilestone(milestone)
            }
        }
    }

    override fun getItemCount() = if(home) 3 else tempMilestone2.size
}