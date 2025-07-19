package com.abcd.pemantauankesehatananak.ui.activity.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.pemantauankesehatananak.data.database.api.ApiService
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private var api: ApiService
): ViewModel(){
    private var _postRegister = MutableLiveData<UIState<ResponseModel>>()

    fun postRegister(
        noKtp: String, nama: String, alamat: String, nomorHp: String, namaAnak: String,
        tanggalLahir: String, jk: String, username: String, password: String,
    ) {
        viewModelScope.launch {
            _postRegister.postValue(UIState.Loading)
            delay(1_000)
            try {
                val postRegister = api.postRegister(
                    "", noKtp, nama, alamat, nomorHp, namaAnak,
                    tanggalLahir, jk, username, password
                )
                _postRegister.postValue(UIState.Success(postRegister))
            } catch (ex: Exception) {
                _postRegister.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun getRegister(): LiveData<UIState<ResponseModel>> = _postRegister
}