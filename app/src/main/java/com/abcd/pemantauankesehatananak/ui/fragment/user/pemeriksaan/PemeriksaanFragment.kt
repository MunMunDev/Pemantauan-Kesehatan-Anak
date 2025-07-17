package com.abcd.pemantauankesehatananak.ui.fragment.user.pemeriksaan

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.pemantauankesehatananak.adapter.PemeriksaanAdapter
import com.abcd.pemantauankesehatananak.data.model.PelayananModel
import com.abcd.pemantauankesehatananak.databinding.FragmentPemeriksaanBinding
import com.abcd.pemantauankesehatananak.utils.SharedPreferencesLogin
import com.abcd.pemantauankesehatananak.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class PemeriksaanFragment : Fragment() {
    private lateinit var binding: FragmentPemeriksaanBinding
    private val viewModel: PemeriksaanViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferencesLogin
    private lateinit var adapterPemeriksaan: PemeriksaanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = SharedPreferencesLogin(requireContext())
        viewModel.fetchPemeriksaan(sharedPreferences.getIdUser())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPemeriksaanBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTopBarSearch()
        getPemeriksaan()
    }

    private fun setTopBarSearch() {
        binding.myAppBar.apply {
            etSearch.hint = "Cari Pelayanan, Hasil, Tanggal dan Waktu"
            etSearch.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    adapterPemeriksaan.searchData(s?.trim().toString())
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
        }
    }

    private fun getPemeriksaan() {
        viewModel.getPemeriksaan.observe(viewLifecycleOwner){result->
            when(result){
                is UIState.Loading-> setStartShimmer()
                is UIState.Failure-> setFailurePemeriksaan(result.message)
                is UIState.Success-> setSuccessPemeriksaan(result.data)
            }
        }
    }

    private fun setFailurePemeriksaan(message: String) {
        setStopShimmer()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessPemeriksaan(data: ArrayList<PelayananModel>) {
        setStopShimmer()
        if(data.isNotEmpty()){
            setAdapterPemeriksaan(data)
        } else{
            Toast.makeText(requireContext(), "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAdapterPemeriksaan(data: ArrayList<PelayananModel>) {
        adapterPemeriksaan = PemeriksaanAdapter(data)
        binding.rvPemeriksaan.apply {
            adapter = adapterPemeriksaan
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setStartShimmer(){
        binding.apply {
            smPemeriksaan.startShimmer()
            smPemeriksaan.visibility = View.VISIBLE
            rvPemeriksaan.visibility = View.GONE
        }
    }

    private fun setStopShimmer(){
        binding.apply {
            smPemeriksaan.stopShimmer()
            smPemeriksaan.visibility = View.GONE
            rvPemeriksaan.visibility = View.VISIBLE
        }
    }
}