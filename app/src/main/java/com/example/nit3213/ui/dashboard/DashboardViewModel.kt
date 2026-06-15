package com.example.nit3213.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nit3213.data.model.Entity
import com.example.nit3213.data.repository.NitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/** States for the Dashboard screen. */
sealed interface DashboardState {
    data object Loading : DashboardState
    data class Success(val entities: List<Entity>) : DashboardState
    data class Error(val message: String) : DashboardState
}

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: NitRepository
) : ViewModel() {

    private val _state = MutableStateFlow<DashboardState>(DashboardState.Loading)
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    fun loadDashboard(keypass: String) {
        viewModelScope.launch {
            _state.value = DashboardState.Loading
            repository.getDashboard(keypass)
                .onSuccess { _state.value = DashboardState.Success(it) }
                .onFailure { _state.value = DashboardState.Error("Could not load data. Please try again.") }
        }
    }
}
