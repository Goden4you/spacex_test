package com.example.spacextest.presentation

import androidx.lifecycle.*
import com.example.spacextest.entities.SuccessedMission
import com.example.spacextest.mappers.SuccessedMissionsMapper
import com.spacex.domain.entities.Mission
import com.spacex.domain.entities.Result
import com.spacex.domain.usecases.GetMissionsUseCase
import kotlinx.coroutines.launch

class MissionsViewModel(
    private val getMissionsUseCase: GetMissionsUseCase,
    private val mapper: SuccessedMissionsMapper
) : ViewModel() {
    private val _dataLoading = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _missions = MutableLiveData<List<SuccessedMission>>()
    val missions = _missions

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _remoteMissions = arrayListOf<Mission>()

    private var isReversed = false;

    fun getMissions() {
        viewModelScope.launch {
            _dataLoading.postValue(true)
            when (val missionsResult = getMissionsUseCase.invoke()) {
                is Result.Success -> {
                    _remoteMissions.clear()
                    _remoteMissions.addAll(missionsResult.data)
                    missions.value = mapper.fromAllToSuccessedMissions(_remoteMissions)
                    _dataLoading.postValue(false)
                }

                is Result.Error -> {
                    _dataLoading.postValue(false)
                    missions.value = emptyList()
                    _error.postValue(missionsResult.exception.message)
                }
            }
        }
    }

    fun changeSortOrder() {
        viewModelScope.launch {
            _dataLoading.postValue(true)
            missions.value = mapper.changeMissionsOrder(missions.value!!, isReversed)
            isReversed = !isReversed
            _dataLoading.postValue(false)
        }
    }

    class MissionsViewModelFactory(
        private val getMissionsUseCase: GetMissionsUseCase,
        private val mapper: SuccessedMissionsMapper,
    ) :
        ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MissionsViewModel(
                getMissionsUseCase,
                mapper
            ) as T
        }
    }
}
