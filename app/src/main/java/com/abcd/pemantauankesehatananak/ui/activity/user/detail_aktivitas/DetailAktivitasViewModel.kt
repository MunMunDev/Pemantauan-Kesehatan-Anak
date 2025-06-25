package com.abcd.pemantauankesehatananak.ui.activity.user.detail_aktivitas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.pemantauankesehatananak.data.model.RiwayatAktivitasModel
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.data.repository.MilestoneRepository
import com.abcd.pemantauankesehatananak.data.repository.RiwayatAktivitasRepository
import com.abcd.pemantauankesehatananak.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailAktivitasViewModel @Inject constructor(
    private val repository: RiwayatAktivitasRepository
):ViewModel() {

    private val _riwayatAktivitas = MutableLiveData<UIState<ArrayList<RiwayatAktivitasModel>>>()
    val getRiwayatAktivitas: LiveData<UIState<ArrayList<RiwayatAktivitasModel>>> = _riwayatAktivitas

    private val _updateSelesaiHariIni = MutableLiveData<UIState<ResponseModel>>()
    val getUpdateSelesai: LiveData<UIState<ResponseModel>> = _updateSelesaiHariIni

    private fun fetchRiwayatAktivitas(
        idUser: Int, idAktivitas: Int
    ){
        viewModelScope.launch {
            try {
                _riwayatAktivitas.postValue(UIState.Loading)
                delay(1_000)
                val data = repository.getRiwayatAktivitas(idUser, idAktivitas)
                _riwayatAktivitas.postValue(UIState.Success(data))
            } catch (ex: Exception) {
                _riwayatAktivitas.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun loadData(
        idUser: Int,
        idAktivitas: Int,
    ) {
        fetchRiwayatAktivitas(idUser, idAktivitas)
    }

    fun postUpdateSelesai(
        idUser: Int,
        idAktivitas:Int
    ){
        viewModelScope.launch {
            try {
                _updateSelesaiHariIni.postValue(UIState.Loading)
                delay(5_00)
                val data = repository.postUpdateCheck(idUser, idAktivitas)
                _updateSelesaiHariIni.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _updateSelesaiHariIni.postValue(UIState.Failure("error: ${ex.message}"))
            }
        }
    }
}