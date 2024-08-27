package com.evapharma.integrationwithwearables.features.vitals_data.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.evapharma.integrationwithwearables.DataStoreManager
import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.LoginRequest
import com.evapharma.integrationwithwearables.features.vitals_data.domain.use_cases.GetVitalsUseCase
import com.evapharma.integrationwithwearables.features.vitals_data.presentation.view.login.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val getVitalsUseCase: GetVitalsUseCase , private val dataStoreManager: DataStoreManager): ViewModel() {
    private val _uiState = MutableStateFlow<ViewState<String>>(ViewState.Loading)
    var uiState: StateFlow<ViewState<String>> = _uiState

    fun login(user: LoginRequest) {
        viewModelScope.launch {
            when (val response = getVitalsUseCase.login(user)) {
                is DataState.Success -> {
                    val token = response.data
                    dataStoreManager.saveUserId(token)
                    _uiState.value = ViewState.Success(token)
                }

                is DataState.Loading -> {
                    _uiState.value = ViewState.Loading
                }

                is DataState.ErrorV2 -> {
                    _uiState.value = ViewState.Error(response.exception?.toString() ?: "Unknown error")
                }

                else -> {
                    _uiState.value = ViewState.Error("Unexpected error")
                }
            }
        }
    }

}
