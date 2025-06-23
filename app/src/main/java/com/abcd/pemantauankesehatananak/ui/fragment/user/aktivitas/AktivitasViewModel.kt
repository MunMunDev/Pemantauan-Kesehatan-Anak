package com.abcd.pemantauankesehatananak.ui.fragment.user.aktivitas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.pemantauankesehatananak.data.model.AktivitasModel
import com.abcd.pemantauankesehatananak.data.repository.AktivitasRepository
import com.abcd.pemantauankesehatananak.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AktivitasViewModel @Inject constructor(
    private val repository: AktivitasRepository
):ViewModel() {
    private val _aktivitas = MutableLiveData<UIState<ArrayList<AktivitasModel>>>()
    val getAktivitas : LiveData<UIState<ArrayList<AktivitasModel>>> = _aktivitas

    fun loadData(
        idUser:Int, umur:Int
    ){
        viewModelScope.launch {
            try {
                _aktivitas.postValue(UIState.Loading)
                delay(1_000)
                val data = repository.getAktivitas(idUser, umur)
                _aktivitas.postValue(UIState.Success(data))
            } catch (ex: Exception) {
                _aktivitas.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }
}