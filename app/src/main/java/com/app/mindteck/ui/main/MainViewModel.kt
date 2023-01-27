package com.app.mindteck.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.mindteck.comman.Resource
import com.app.mindteck.domain.UseCase
import com.app.mindteck.model.Country

import com.app.mindteck.utils.log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: UseCase) : ViewModel() {

    private val gson by lazy { Gson() }

    private val _countryList = MutableStateFlow<List<Country>>(emptyList())
    val countryList: StateFlow<List<Country>?> = _countryList

    private val _loading = MutableStateFlow<Boolean>(false)
    val loading: StateFlow<Boolean?> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error



    init {
        apiCountry()
    }

    private fun apiCountry(endPoint: String = "all") {
        viewModelScope.launch {
            val response = useCase.invoke<ArrayList<Country>>(endPoint)
            response.collect {
                when (it) {
                    is Resource.Success -> {
                        _loading.value = false
                        it.data?.let { userData ->
                            val groupListType = object : TypeToken<ArrayList<Country>>() {}.type
                            val countryDataList = gson.fromJson<ArrayList<Country>>(
                                gson.toJson(userData),
                                groupListType
                            )
                            _countryList.value = countryDataList
                        }

                    }
                    is Resource.Error -> {
                        _loading.value = false
                        _error.value = it.message
                        _countryList.value = emptyList()

                    }
                    is Resource.Loading -> {
                        _loading.value = true
                    }
                }

            }

        }

    }

}