package com.abcd.pemantauankesehatananak.ui.fragment.user.perkembangan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.pemantauankesehatananak.data.model.KategoriModel
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.data.repository.MilestoneRepository
import com.abcd.pemantauankesehatananak.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MilestoneViewModel @Inject constructor(
    private val repository: MilestoneRepository
):ViewModel() {

    private val _kategori = MutableLiveData<UIState<ArrayList<KategoriModel>>>()
    val getKategori: LiveData<UIState<ArrayList<KategoriModel>>> = _kategori

    private val _mislestone = MutableLiveData<UIState<ArrayList<MilestoneModel>>>()
    val getMilestone: LiveData<UIState<ArrayList<MilestoneModel>>> = _mislestone

    private val _updateCheck = MutableLiveData<UIState<ResponseModel>>()
    val getCheck: LiveData<UIState<ResponseModel>> = _updateCheck

    private fun fetchKategori(){
        viewModelScope.launch {
            try {
                _kategori.postValue(UIState.Loading)
                delay(1_000)
                val data = repository.getKategori()
                _kategori.postValue(UIState.Success(data))
            } catch (ex: Exception) {
                _kategori.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    private fun fetchMilestone(idUser:Int){
        viewModelScope.launch {
            try {
                _mislestone.postValue(UIState.Loading)
                delay(1_000)
                val data = repository.getMilestone(idUser)
                _mislestone.postValue(UIState.Success(data))
            } catch (ex: Exception) {
                _mislestone.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun loadData(
        idUser: Int,
    ) {
        fetchMilestone(idUser)
        fetchKategori()
    }

    fun postUpdateCheck(idUser: Int, idMilestone:Int){
        viewModelScope.launch {
            try {
                _updateCheck.postValue(UIState.Loading)
                delay(5_00)
                val data = repository.postUpdateCheck(idUser, idMilestone)
                _updateCheck.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _updateCheck.postValue(UIState.Failure("error: ${ex.message}"))
            }
        }
    }
}