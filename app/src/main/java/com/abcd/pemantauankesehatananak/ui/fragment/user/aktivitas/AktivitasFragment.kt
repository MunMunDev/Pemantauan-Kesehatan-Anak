package com.abcd.pemantauankesehatananak.ui.fragment.user.aktivitas

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.pemantauankesehatananak.adapter.AktivitasAdapter
import com.abcd.pemantauankesehatananak.adapter.KategoriAdapter
import com.abcd.pemantauankesehatananak.data.model.AktivitasModel
import com.abcd.pemantauankesehatananak.data.model.KategoriModel
import com.abcd.pemantauankesehatananak.databinding.FragmentAktivitasBinding
import com.abcd.pemantauankesehatananak.ui.activity.user.detail_aktivitas.DetailAktivitasActivity
import com.abcd.pemantauankesehatananak.utils.OnClickItem
import com.abcd.pemantauankesehatananak.utils.SharedPreferencesLogin
import com.abcd.pemantauankesehatananak.utils.TanggalDanWaktu
import com.abcd.pemantauankesehatananak.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class AktivitasFragment : Fragment() {
    private lateinit var binding: FragmentAktivitasBinding
    private val viewModel: AktivitasViewModel by viewModels()
    private lateinit var sp: SharedPreferencesLogin
    private lateinit var adapterAktivitas: AktivitasAdapter
    private lateinit var adapterKategori: KategoriAdapter
    private var tanggalDanWaktu = TanggalDanWaktu()
    private lateinit var arrayListKategori: ArrayList<KategoriModel>

    private var checkKategoriFirst = true
    private var nameKategori = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sp = SharedPreferencesLogin(requireContext())
        val usia = tanggalDanWaktu.hitungUsiaDalamBulan(sp.getTanggalLahir())
        fetchAktivitas(sp.getIdUser(), usia)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAktivitasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTopAppBar()
        getKategori()
        getAktivitas()
    }

    private fun setTopAppBar() {
        binding.myAppBar.apply {
//            etSearch.hint = "Cari judul, kategori, usia"
            etSearch.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    adapterAktivitas.searchData(s.toString())
                    if(!checkKategoriFirst){
                        adapterAktivitas.searchKategori(nameKategori)
                    }
                }

            })
        }
    }

    private fun fetchAktivitas(idUser: Int, usia: Int) {
        viewModel.loadData(idUser, usia)
    }

    private fun getKategori() {
        viewModel.getKategori.observe(viewLifecycleOwner){result->
            when(result){
                is UIState.Loading-> setStartShimmerKategori()
                is UIState.Success-> setSuccessFetchKategori(result.data)
                is UIState.Failure-> setFailureFetchKategori(result.message)
            }
        }
    }

    private fun setSuccessFetchKategori(data: ArrayList<KategoriModel>) {
        if(data.isNotEmpty()){
            arrayListKategori = arrayListOf()
            arrayListKategori.add(KategoriModel(0, "Semua", ""))
            arrayListKategori.addAll(data)
            setAdapterKategori(arrayListKategori)
//            setAdapterKategori(data)
        } else{
            Toast.makeText(requireContext(), "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
        setStoptShimmerKategori()
    }

    private fun setFailureFetchKategori(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        setStoptShimmerKategori()
    }

    private fun setAdapterKategori(data: ArrayList<KategoriModel>) {
        adapterKategori = KategoriAdapter(data, object: OnClickItem.ClickKategori{
            override fun clickKategori(kategori: KategoriModel) {
                checkKategoriFirst = kategori.id_kategori == 0
                if(checkKategoriFirst){
                    adapterAktivitas.searchKategori("")
                } else{
                    adapterAktivitas.searchKategori(kategori.kategori!!)
                }
                nameKategori = kategori.kategori!!
            }
        })

        binding.apply {
            rvKategori.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvKategori.adapter = adapterKategori
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
            setAdapter(data)
        } else{
            Toast.makeText(requireContext(), "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
        setStoptShimmerAktivitas()
    }

    private fun setFailureFetchAktivitas(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        setStoptShimmerAktivitas()
    }

    private fun setAdapter(data: ArrayList<AktivitasModel>) {
        adapterAktivitas = AktivitasAdapter(data, object: OnClickItem.ClickAktivitas{
            override fun clickAktivitas(aktivitas: AktivitasModel) {
                val i = Intent(requireContext(), DetailAktivitasActivity::class.java)
                i.putExtra("aktivitas", aktivitas)
                startActivity(i)
            }
        }, false)

        binding.apply {
            rvAktivitas.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvAktivitas.adapter = adapterAktivitas
        }
    }

    private fun setStartShimmerKategori(){
        binding.apply {
            smKategori.startShimmer()
            smKategori.visibility = View.VISIBLE
            rvKategori.visibility = View.GONE
        }
    }
    private fun setStoptShimmerKategori(){
        binding.apply {
            smKategori.stopShimmer()
            smKategori.visibility = View.GONE
            rvKategori.visibility = View.VISIBLE
        }
    }

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
}