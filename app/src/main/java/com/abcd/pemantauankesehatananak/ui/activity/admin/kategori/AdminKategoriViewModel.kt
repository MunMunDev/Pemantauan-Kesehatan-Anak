package com.abcd.pemantauankesehatananak.ui.activity.admin.kategori

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.pemantauankesehatananak.data.model.KategoriModel
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.data.repository.KategoriRepository
import com.abcd.pemantauankesehatananak.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminKategoriViewModel @Inject constructor(
    private val kategoriRepository: KategoriRepository
): ViewModel() {

    private val _kategori = MutableLiveData<UIState<ArrayList<KategoriModel>>>()
    val getKategori: LiveData<UIState<ArrayList<KategoriModel>>> = _kategori

    private val _tambahKategori = MutableLiveData<UIState<ResponseModel>>()
    val getTambahKategori: LiveData<UIState<ResponseModel>> = _tambahKategori

    private val _updateKategori = MutableLiveData<UIState<ResponseModel>>()
    val getUpdateKategori: LiveData<UIState<ResponseModel>> = _updateKategori

    fun fetchKategori(){
        viewModelScope.launch {
            try {
                _kategori.postValue(UIState.Loading)
                delay(1_000)
                val data = kategoriRepository.getKategori()
                _kategori.postValue(UIState.Success(data))
            } catch (ex: Exception) {
                _kategori.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun postTambahKategori(
        kategori:String, deskripsi:String
    ){
        viewModelScope.launch {
            try {
                _tambahKategori.postValue(UIState.Loading)
                delay(5_00)
                val data = kategoriRepository.postTambahKategori(kategori, deskripsi)
                _tambahKategori.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _tambahKategori.postValue(UIState.Failure("error: ${ex.message}"))
            }
        }
    }

    fun postUpdateKategori(
        idKategori: Int, kategori:String, deskripsi:String
    ){
        viewModelScope.launch {
            try {
                _updateKategori.postValue(UIState.Loading)
                delay(5_00)
                val data = kategoriRepository.postUpdateKategori(idKategori, kategori, deskripsi)
                _updateKategori.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _updateKategori.postValue(UIState.Failure("error: ${ex.message}"))
            }
        }
    }

}