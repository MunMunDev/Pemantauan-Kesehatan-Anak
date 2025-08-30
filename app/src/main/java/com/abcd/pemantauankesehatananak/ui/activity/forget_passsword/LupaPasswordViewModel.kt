package com.abcd.pemantauankesehatananak.ui.activity.forget_passsword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.pemantauankesehatananak.data.database.api.ApiService
import com.abcd.pemantauankesehatananak.data.model.ResponseModel
import com.abcd.pemantauankesehatananak.data.model.UserModel
import com.abcd.pemantauankesehatananak.data.repository.LupaPasswordRepository
import com.abcd.pemantauankesehatananak.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LupaPasswordViewModel @Inject constructor(
    private val repository: LupaPasswordRepository
): ViewModel() {
    private var _user = MutableLiveData<UIState<UserModel>>()
    fun getLupaPassword() : LiveData<UIState<UserModel>> = _user

    private var postKirimUsernamePassword = MutableLiveData<UIState<ResponseModel>>()
    fun getKirimUsernamePassword() : LiveData<UIState<ResponseModel>> = postKirimUsernamePassword

    fun fetchLupaPassword(
        email: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _user.postValue(UIState.Loading)
            delay(1_000)
            try {
                val data = repository.getLupaPassword(email)
                _user.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _user.postValue(UIState.Failure("Error : ${ex.message}"))
            }
        }
    }

    fun postKirimUsernamePassword(
        email: String, username: String, password: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            postKirimUsernamePassword.postValue(UIState.Loading)
            delay(1_000)
            try {
                val data = repository.postKirimUsernamePassword(email, username, password)
                postKirimUsernamePassword.postValue(UIState.Success(data))
            } catch (ex: Exception){
                postKirimUsernamePassword.postValue(UIState.Failure("Error : ${ex.message}"))
            }
        }
    }

}