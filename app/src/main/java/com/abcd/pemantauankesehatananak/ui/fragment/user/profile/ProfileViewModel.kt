package com.abcd.pemantauankesehatananak.ui.fragment.user.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.data.repository.ProfileRepository
import com.abcd.pemantauankesehatananak.utils.network.UIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel  @Inject constructor(
    private val repository : ProfileRepository
): ViewModel(){
    private val _updateDataDiri = MutableLiveData<UIState<ResponseModel>>()
    val getResponseUpdateDataDiri : LiveData<UIState<ResponseModel>> = _updateDataDiri

    fun postUpdateDataDiri(
        idUser:Int, nama: String, nomorHp: String, alamat: String,
        namaAnak: String, tanggalLahir: String, jenisKelamin: String,
        username: String, password: String, usernameLama: String
    ){
        viewModelScope.launch {
            _updateDataDiri.postValue(UIState.Loading)
            delay(1_000)
            try {
                val data = repository.postDataDiri(
                    idUser, nama, alamat, nomorHp, namaAnak, tanggalLahir,
                    jenisKelamin, username, password, usernameLama
                )
                _updateDataDiri.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _updateDataDiri.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }
}