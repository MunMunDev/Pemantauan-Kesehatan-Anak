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
import com.abcd.pemantauankesehatananak.adapter.MilestoneAdapter
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.databinding.FragmentMilestoneBinding
import com.abcd.pemantauankesehatananak.utils.LoadingAlertDialog
import com.abcd.pemantauankesehatananak.utils.OnClickItem
import com.abcd.pemantauankesehatananak.utils.SharedPreferencesLogin
import com.abcd.pemantauankesehatananak.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import javax.inject.Inject

@AndroidEntryPoint
class MilestoneFragment : Fragment() {
    private lateinit var binding: FragmentMilestoneBinding
    private val viewModel: MilestoneViewModel by viewModels()
    private lateinit var sp: SharedPreferencesLogin
    private lateinit var adapter: MilestoneAdapter
    @Inject lateinit var loading: LoadingAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sp = SharedPreferencesLogin(requireContext())
        fetchMilestone()
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
                    adapter.searchData(s.toString())
                }

            })
        }
    }


    private fun fetchMilestone() {
        viewModel.loadData(sp.getIdUser())
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
            setAdapter(data)
        } else{
            Toast.makeText(requireContext(), "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
        setStoptShimmerMilestone()
    }

    private fun setFailureFetchMilestone(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        setStoptShimmerMilestone()
    }

    private fun setAdapter(data: ArrayList<MilestoneModel>) {
        adapter = MilestoneAdapter(data, object: OnClickItem.ClickMilestone{

            override fun clickMilestone(milestone: MilestoneModel) {
                setUpdateMilestone(milestone.id_milestone!!)
            }
        }, false)

        binding.apply {
            rvMilestone.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvMilestone.adapter = adapter
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