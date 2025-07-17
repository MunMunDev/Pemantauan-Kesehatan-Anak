package com.abcd.pemantauankesehatananak.ui.fragment.user.pemeriksaan

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.abcd.pemantauankesehatananak.data.model.PelayananModel
import com.abcd.pemantauankesehatananak.databinding.FragmentPemeriksaanBinding
import com.abcd.pemantauankesehatananak.utils.SharedPreferencesLogin
import com.abcd.pemantauankesehatananak.utils.network.UIState
import java.util.ArrayList

class PemeriksaanFragment : Fragment() {
    private lateinit var binding: FragmentPemeriksaanBinding
    private val viewModel: PemeriksaanViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferencesLogin

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

        getPemeriksaan()
    }

    private fun getPemeriksaan() {
        viewModel.getPemeriksaan.observe(viewLifecycleOwner){result->
            when(result){
                is UIState.Loading-> {}
                is UIState.Failure-> setFailurePemeriksaan(result.message)
                is UIState.Success-> setSuccessPemeriksaan(result.data)
            }
        }
    }

    private fun setFailurePemeriksaan(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessPemeriksaan(data: ArrayList<PelayananModel>) {
        if(data.isNotEmpty()){
            setAdapterPemeriksaan(data)
        } else{
            Toast.makeText(requireContext(), "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAdapterPemeriksaan(data: ArrayList<PelayananModel>) {

    }
}