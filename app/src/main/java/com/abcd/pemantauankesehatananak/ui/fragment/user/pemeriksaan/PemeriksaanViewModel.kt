package com.abcd.pemantauankesehatananak.ui.fragment.user.pemeriksaan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.pemantauankesehatananak.data.model.PelayananModel
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.data.repository.PemeriksaanRepository
import com.abcd.pemantauankesehatananak.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PemeriksaanViewModel @Inject constructor (
    private val repositoryPemeriksaan: PemeriksaanRepository
): ViewModel() {

    private val _hasilPemeriksaan = MutableLiveData<UIState<ArrayList<PelayananModel>>>()
    val getPemeriksaan : LiveData<UIState<ArrayList<PelayananModel>>> = _hasilPemeriksaan

    fun fetchPemeriksaan(){
        viewModelScope.launch {
            try {
                _hasilPemeriksaan.postValue(UIState.Loading)
                delay(1_000)
                val data = repositoryPemeriksaan.getPemeriksaan()
                _hasilPemeriksaan.postValue(UIState.Success(data))
            } catch (ex: Exception) {
                _hasilPemeriksaan.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }
}