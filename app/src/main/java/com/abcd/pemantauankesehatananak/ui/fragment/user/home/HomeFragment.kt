package com.abcd.pemantauankesehatananak.ui.fragment.user.home

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.pemantauankesehatananak.adapter.AktivitasAdapter
import com.abcd.pemantauankesehatananak.adapter.MilestoneAdapter
import com.abcd.pemantauankesehatananak.data.model.AktivitasModel
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.databinding.FragmentHomeBinding
import com.abcd.pemantauankesehatananak.ui.activity.user.detail_aktivitas.DetailAktivitasActivity
import com.abcd.pemantauankesehatananak.ui.activity.user.main.MainActivity
import com.abcd.pemantauankesehatananak.utils.OnClickItem
import com.abcd.pemantauankesehatananak.utils.SharedPreferencesLogin
import com.abcd.pemantauankesehatananak.utils.TanggalDanWaktu
import com.abcd.pemantauankesehatananak.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val tanggalDanWaktu = TanggalDanWaktu()
    private lateinit var sharedPreferences: SharedPreferencesLogin

    // Adapter
    private lateinit var adapterAktivitas: AktivitasAdapter
    private lateinit var adapterMilestone: MilestoneAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = SharedPreferencesLogin(requireContext())
        fetchData()
    }

    private fun fetchData() {
        val umur = tanggalDanWaktu.hitungUsiaDalamBulan(sharedPreferences.getTanggalLahir())
        viewModel.loadData(sharedPreferences.getIdUser(), umur)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButton()
        getAktivitas()
        getMilestone()
    }

    private fun setButton() {
        binding.apply {
            btnAktivitas.setOnClickListener {
                (activity as MainActivity).setAktivitasFragment()
            }
            btnMilestone.setOnClickListener {
                (activity as MainActivity).setMilestoneFragment()
            }
        }
    }

    private fun getAktivitas() {
        viewModel.getAktivitas.observe(viewLifecycleOwner){result->
            when(result){
                is UIState.Loading-> setStartShimmerAktivitas()
                is UIState.Success-> setSuccessFetchAktivitas(result.data)
                is UIState.Failure-> setFailureFetchAktivitas(result.message)
            }
        }
    }

    private fun setSuccessFetchAktivitas(data: ArrayList<AktivitasModel>) {
        if(data.isNotEmpty()){
            setAdapterAktivitas(data)
        } else{
            Toast.makeText(requireContext(), "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
        setStoptShimmerAktivitas()
    }

    private fun setFailureFetchAktivitas(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.e("HomeFragmentTAG", "$message ")
        setStoptShimmerAktivitas()
    }

    private fun getMilestone() {
        viewModel.getMilestone.observe(viewLifecycleOwner){result->
            when(result){
                is UIState.Loading-> setStartShimmerMilestone()
                is UIState.Success-> setSuccessFetchMilestone(result.data)
                is UIState.Failure-> setFailureFetchMilestone(result.message)
            }
        }
    }

    private fun setSuccessFetchMilestone(data: ArrayList<MilestoneModel>) {
        if(data.isNotEmpty()){
            setAdapterMilestone(data)
        } else{
            Toast.makeText(requireContext(), "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
        setStoptShimmerMilestone()
    }

    private fun setFailureFetchMilestone(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.e("HomeFragmentTAG", "$message ")
        setStoptShimmerMilestone()
    }


    // Set adapter
    // Aktivitas
    private fun setAdapterAktivitas(data: ArrayList<AktivitasModel>) {
        adapterAktivitas = AktivitasAdapter(data, object : OnClickItem.ClickAktivitas{
            override fun clickAktivitas(aktivitas: AktivitasModel) {
                val i = Intent(requireContext(), DetailAktivitasActivity::class.java)
                i.putExtra("aktivitas", aktivitas)
                startActivity(i)
            }

        }, true)
        binding.rvAktivitas.apply {
            adapter = adapterAktivitas
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    // Milestone
    private fun setAdapterMilestone(data: ArrayList<MilestoneModel>) {
        adapterMilestone = MilestoneAdapter(data, object: OnClickItem.ClickMilestone{
            override fun clickMilestone(milestone: MilestoneModel) {

            }
        }, true)
        binding.rvMilestone.apply {
            adapter = adapterMilestone
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    // Shimmer
    private fun setStartShimmerAktivitas(){
        binding.apply {
            smAktivitas.startShimmer()
            smAktivitas.visibility = View.VISIBLE
            rvAktivitas.visibility = View.GONE
        }
    }
    private fun setStoptShimmerAktivitas(){
        binding.apply {
            smAktivitas.stopShimmer()
            smAktivitas.visibility = View.GONE
            rvAktivitas.visibility = View.VISIBLE
        }
    }

    private fun setStartShimmerMilestone(){
        binding.apply {
            smMilestone.startShimmer()
            smMilestone.visibility = View.VISIBLE
            rvMilestone.visibility = View.GONE
        }
    }
    private fun setStoptShimmerMilestone(){
        binding.apply {
            smMilestone.stopShimmer()
            smMilestone.visibility = View.GONE
            rvMilestone.visibility = View.VISIBLE
        }
    }

}