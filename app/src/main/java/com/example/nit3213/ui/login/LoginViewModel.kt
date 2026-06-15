package com.example.nit3213.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nit3213.data.repository.NitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/** The possible states of the Login screen, exposed to the Activity as a Flow. */
sealed interface LoginState {
    data object Idle : LoginState
    data object Loading : LoginState
    data class Success(val keypass: String) : LoginState
    data class Error(val message: String) : LoginState
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: NitRepository
) : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun login(location: String, username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _state.value = LoginState.Error("Please enter both username and password")
            return
        }
        // viewModelScope: coroutine is automatically cancelled if the ViewModel dies.
        viewModelScope.launch {
            _state.value = LoginState.Loading
            repository.login(location, username, password)
                .onSuccess { keypass -> _state.value = LoginState.Success(keypass) }
                .onFailure { _state.value = LoginState.Error("Login failed. Check your details and try again.") }
        }
    }
}
