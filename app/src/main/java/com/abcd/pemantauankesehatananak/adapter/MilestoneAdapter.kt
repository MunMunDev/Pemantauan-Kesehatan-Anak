package com.abcd.pemantauankesehatananak.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.databinding.ItemMilestoneBinding
import com.abcd.pemantauankesehatananak.utils.OnClickItem

class MilestoneAdapter(
    private val list: List<MilestoneModel>,
    private val onCheckChanged: (MilestoneModel, Boolean) -> Unit,
    private val onClick: OnClickItem.ClickMilestone,
    private val home: Boolean
) : RecyclerView.Adapter<MilestoneAdapter.MilestoneViewHolder>() {

    inner class MilestoneViewHolder(val binding: ItemMilestoneBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MilestoneViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMilestoneBinding.inflate(inflater, parent, false)
        return MilestoneViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MilestoneViewHolder, position: Int) {
        val milestone = list[position]
        holder.binding.tvDeskripsi.text = milestone.deskripsi
        holder.binding.cbMilestone.isChecked = milestone.sudah_tercapai == 1
        holder.binding.cbMilestone.setOnCheckedChangeListener { _, isChecked ->
            onCheckChanged(milestone, isChecked)
        }
    }

    override fun getItemCount() = if(home) 3 else list.size
}