package com.abcd.pemantauankesehatananak.ui.fragment.user.perkembangan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.pemantauankesehatananak.data.model.MilestoneModel
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
    private val _mislestone = MutableLiveData<UIState<ArrayList<MilestoneModel>>>()
    val getMilestone: LiveData<UIState<ArrayList<MilestoneModel>>> = _mislestone

    fun loadData(
        idUser: Int,
    ) {
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
}