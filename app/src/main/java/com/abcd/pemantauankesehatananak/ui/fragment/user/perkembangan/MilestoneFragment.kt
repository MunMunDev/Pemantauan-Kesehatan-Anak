package com.abcd.pemantauankesehatananak.ui.fragment.user.perkembangan

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
import com.abcd.pemantauankesehatananak.adapter.KategoriAdapter
import com.abcd.pemantauankesehatananak.adapter.MilestoneAdapter
import com.abcd.pemantauankesehatananak.data.model.KategoriModel
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.databinding.FragmentMilestoneBinding
import com.abcd.pemantauankesehatananak.utils.LoadingAlertDialog
import com.abcd.pemantauankesehatananak.utils.OnClickItem
import com.abcd.pemantauankesehatananak.utils.SharedPreferencesLogin
import com.abcd.pemantauankesehatananak.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MilestoneFragment : Fragment() {
    private lateinit var binding: FragmentMilestoneBinding
    private val viewModel: MilestoneViewModel by viewModels()
    private lateinit var sp: SharedPreferencesLogin
    private lateinit var adapterMilestone: MilestoneAdapter
    private lateinit var adapterKategori: KategoriAdapter
    @Inject lateinit var loading: LoadingAlertDialog
    private lateinit var arrayListKategori: ArrayList<KategoriModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sp = SharedPreferencesLogin(requireContext())
        fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMilestoneBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTopAppBar()
        getKategori()
        getMilestone()
        getUpdateMilestone()
    }

    private fun setTopAppBar() {
        binding.myAppBar.apply {
            etSearch.hint = "Cari judul, kategori, usia ideal"
            etSearch.addTextChangedListener(object : TextWatcher {
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
                    adapterMilestone.searchData(s.toString())
                }

            })
        }
    }

    private fun fetchData() {
        viewModel.loadData(sp.getIdUser())
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
        Toast.makeText(requireContext(), "${data.size}", Toast.LENGTH_SHORT).show()
        adapterKategori = KategoriAdapter(data, object: OnClickItem.ClickKategori{
            override fun clickKategori(kategori: KategoriModel) {

            }
        })

        binding.apply {
            rvKategori.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvKategori.adapter = adapterKategori
        }
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
        setStoptShimmerMilestone()
    }

    private fun setAdapterMilestone(data: ArrayList<MilestoneModel>) {
        adapterMilestone = MilestoneAdapter(data, object: OnClickItem.ClickMilestone{

            override fun clickMilestone(milestone: MilestoneModel) {
                setUpdateMilestone(milestone.id_milestone!!)
            }
        }, false)

        binding.apply {
            rvMilestone.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvMilestone.adapter = adapterMilestone
        }
    }

    private fun setUpdateMilestone(idAktivitas: Int) {
        viewModel.postUpdateCheck(sp.getIdUser(), idAktivitas)
    }

    private fun getUpdateMilestone(){
        viewModel.getCheck.observe(viewLifecycleOwner){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(requireContext())
                is UIState.Success-> setSuccessUpdateCheck(result.data)
                is UIState.Failure-> setFailureUpdateCheck(result.message)
            }
        }
    }

    private fun setSuccessUpdateCheck(data: ResponseModel) {
        if(data.status=="0"){
            Toast.makeText(requireContext(), "Berhasil", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(requireContext(), data.message_response, Toast.LENGTH_SHORT).show()
        }
        loading.alertDialogCancel()
    }

    private fun setFailureUpdateCheck(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
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