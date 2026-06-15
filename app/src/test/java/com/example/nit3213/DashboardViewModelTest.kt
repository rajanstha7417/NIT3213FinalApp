package com.example.nit3213

import com.example.nit3213.data.model.Entity
import com.example.nit3213.data.repository.NitRepository
import com.example.nit3213.ui.dashboard.DashboardState
import com.example.nit3213.ui.dashboard.DashboardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: NitRepository = mock()
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setUp() {
        viewModel = DashboardViewModel(repository)
    }

    @Test
    fun `loadDashboard emits Success with entities`() = runTest {
        val entity = Entity(linkedMapOf("name" to "Oak", "description" to "A tree"))
        whenever(repository.getDashboard(any()))
            .thenReturn(Result.success(listOf(entity)))

        viewModel.loadDashboard("Trees")

        val state = viewModel.state.value
        assertTrue(state is DashboardState.Success)
        assertEquals(1, (state as DashboardState.Success).entities.size)
    }

    @Test
    fun `loadDashboard emits Error on failure`() = runTest {
        whenever(repository.getDashboard(any()))
            .thenReturn(Result.failure(RuntimeException("500")))

        viewModel.loadDashboard("Trees")

        assertTrue(viewModel.state.value is DashboardState.Error)
    }
}
