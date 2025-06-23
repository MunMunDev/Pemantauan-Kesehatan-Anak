package com.abcd.pemantauankesehatananak.ui.fragment.user.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.pemantauankesehatananak.data.model.AktivitasModel
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.data.repository.AktivitasRepository
import com.abcd.pemantauankesehatananak.data.repository.MilestoneRepository
import com.abcd.pemantauankesehatananak.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repositoryAktivitas: AktivitasRepository,
    private val repositoryMilestone: MilestoneRepository,
): ViewModel() {

    private val _aktivitas = MutableLiveData<UIState<ArrayList<AktivitasModel>>>()
    private val _mislestone = MutableLiveData<UIState<ArrayList<MilestoneModel>>>()

    val getAktivitas : LiveData<UIState<ArrayList<AktivitasModel>>> = _aktivitas
    val getMilestone: LiveData<UIState<ArrayList<MilestoneModel>>> = _mislestone

    fun loadData(
        idUser:Int, umur:Int
    ){
        fetchAktivitas(idUser, umur)
        fetchMilestone(idUser, umur)
    }

    private fun fetchAktivitas(idUser: Int, umur: Int){
        viewModelScope.launch {
            try {
                _aktivitas.postValue(UIState.Loading)
                delay(1_000)
                val data = repositoryAktivitas.getAktivitas(idUser, umur)
                _aktivitas.postValue(UIState.Success(data))
            } catch (ex: Exception) {
                _aktivitas.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    private fun fetchMilestone(idUser: Int, umur: Int){
        viewModelScope.launch {
            try {
                _mislestone.postValue(UIState.Loading)
                delay(1_000)
                val data = repositoryMilestone.getMilestone(idUser)
                _mislestone.postValue(UIState.Success(data))
            } catch (ex: Exception) {
                _mislestone.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }
}