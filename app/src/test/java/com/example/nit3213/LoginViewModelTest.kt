package com.example.nit3213

import com.example.nit3213.data.repository.NitRepository
import com.example.nit3213.ui.login.LoginState
import com.example.nit3213.ui.login.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever

/**
 * Unit tests for LoginViewModel. The repository is a Mockito mock, so no real
 * network call happens — this is exactly why we inject an interface.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: NitRepository = mock()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(repository)
    }

    @Test
    fun `successful login emits Success with keypass`() = runTest {
        whenever(repository.login(any(), any(), any()))
            .thenReturn(Result.success("Trees"))

        viewModel.login("footscray", "Rajan", "s12345678")

        val state = viewModel.state.value
        assertTrue(state is LoginState.Success)
        assertEquals("Trees", (state as LoginState.Success).keypass)
    }

    @Test
    fun `failed login emits Error`() = runTest {
        whenever(repository.login(any(), any(), any()))
            .thenReturn(Result.failure(RuntimeException("401")))

        viewModel.login("footscray", "Rajan", "wrong")

        assertTrue(viewModel.state.value is LoginState.Error)
    }

    @Test
    fun `blank fields emit Error and never call the repository`() = runTest {
        viewModel.login("footscray", "", "")

        assertTrue(viewModel.state.value is LoginState.Error)
        verifyNoInteractions(repository)
    }
}
