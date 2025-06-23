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
import com.abcd.pemantauankesehatananak.data.model.AktivitasModel
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
    private lateinit var adapter: AktivitasAdapter
    private var tanggalDanWaktu = TanggalDanWaktu()

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

        getAktivitas()
        setTopAppBar()
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
                    adapter.searchData(s.toString())
                }

            })
        }
    }

    private fun fetchAktivitas(idUser: Int, usia: Int) {
        viewModel.loadData(idUser, usia)
    }

    private fun getAktivitas() {
        viewModel.getAktivitas.observe(viewLifecycleOwner){result->
            when(result){
                is UIState.Loading->{}
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
    }

    private fun setFailureFetchAktivitas(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setAdapter(data: ArrayList<AktivitasModel>) {
        adapter = AktivitasAdapter(data, object: OnClickItem.ClickAktivitas{
            override fun clickAktivitas(aktivitas: AktivitasModel) {
                val i = Intent(requireContext(), DetailAktivitasActivity::class.java)
                i.putExtra("aktivitas", aktivitas)
                startActivity(i)
            }
        }, false)

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
        }
    }
}