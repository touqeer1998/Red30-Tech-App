package com.example.red30

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.red30.data.ConferenceDataUiState
import com.example.red30.data.ConferenceRepository
import com.example.red30.data.Day
import com.example.red30.data.favorites
import com.example.red30.data.sessionInfosByDay
import com.example.red30.fakes.FakeConferenceRepository
import com.example.red30.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest : KoinTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        this.modules(
            module {
                singleOf(::FakeConferenceRepository) bind ConferenceRepository::class
                factoryOf<SavedStateHandle>(::SavedStateHandle)
                viewModelOf(::MainViewModel)
            }
        )
    }

    @Test
    fun `getUiState initial state should be loading`() = runTest(testDispatcher) {
        val viewModel: MainViewModel by inject()

        viewModel.uiState.test {
            var state = awaitItem()
            assertTrue(state is ConferenceDataUiState.Loading)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getUiState loaded state after initialization`() = runTest(testDispatcher) {
        val viewModel: MainViewModel by inject()

        viewModel.uiState.test {
            var state = awaitItem()
            assertTrue(state is ConferenceDataUiState.Loading)

            state = awaitItem()
            assertTrue(state is ConferenceDataUiState.Loaded)

            assertEquals(2, state.sessionInfos.size)
            assertEquals(Day.Day1, state.day)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getUiState loaded state after day change`() = runTest(testDispatcher) {
        val viewModel: MainViewModel by inject()

        viewModel.setDay(day = Day.Day2)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is ConferenceDataUiState.Loaded)

        assertEquals(1, state.sessionInfosByDay.size)
        assertEquals(Day.Day2, state.sessionInfosByDay[0].day)
        assertEquals(Day.Day2, state.day)
    }

    @Test
    fun `getSessionInfoById valid session ID`() = runTest(testDispatcher) {
        val viewModel: MainViewModel by inject()

        viewModel.getSessionInfoById(sessionId = 1)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is ConferenceDataUiState.Loaded)

        assertNotNull(state.selectedSession)
        assertEquals(1, state.selectedSession.session.id)
    }

    @Test
    fun `getSessionInfoById invalid session ID`() = runTest(testDispatcher) {
        val viewModel: MainViewModel by inject()

        viewModel.getSessionInfoById(sessionId = 55)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is ConferenceDataUiState.Loaded)

        assertNull(state.selectedSession)
    }

    @Test
    fun `toggleFavorite valid session ID`() = runTest(testDispatcher) {
        val viewModel: MainViewModel by inject()

        viewModel.toggleFavorite(sessionId = 2)
        advanceUntilIdle()
        viewModel.toggleFavorite(sessionId = 1)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is ConferenceDataUiState.Loaded)

        assertEquals(2, state.favorites.size)
        assertEquals(1, state.favorites[0].session.id)
    }

    @Test
    fun `toggleFavorite invalid session ID`() = runTest(testDispatcher) {
        val viewModel: MainViewModel by inject()

        viewModel.toggleFavorite(sessionId = 55)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is ConferenceDataUiState.Loaded)

        assertTrue(state.favorites.isEmpty())
    }

    @Test
    fun `toggleFavorite empty favorites list`() = runTest(testDispatcher) {
        val viewModel: MainViewModel by inject()

        viewModel.toggleFavorite(sessionId = 1)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is ConferenceDataUiState.Loaded)

        assertEquals(1, state.favorites.size)
        assertEquals(1, state.favorites[0].session.id)
    }

    @Test
    fun `toggleFavorite should remove existing item`() = runTest(testDispatcher) {
        val viewModel: MainViewModel by inject()

        viewModel.toggleFavorite(sessionId = 1)
        advanceUntilIdle()
        viewModel.toggleFavorite(sessionId = 1)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is ConferenceDataUiState.Loaded)

        assertTrue(state.favorites.isEmpty())
    }

    @Test
    fun `toggleFavorite exception handling should not crash`() = runTest(testDispatcher) {
        val conferenceRepository: ConferenceRepository by inject()
        (conferenceRepository as FakeConferenceRepository).throwFavoritesException = true

        val viewModel = MainViewModel(
            savedStateHandle = SavedStateHandle(),
            conferenceRepository = conferenceRepository
        )

        viewModel.toggleFavorite(sessionId = 1)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is ConferenceDataUiState.Loaded)

        assertTrue(state.favorites.isEmpty())
    }

    @Test
    fun `initial state handle should load correct day sessions`() = runTest(testDispatcher) {
        val conferenceRepository: ConferenceRepository by inject()
        val viewModel = MainViewModel(
            savedStateHandle = SavedStateHandle(mapOf("day" to Day.Day2)),
            conferenceRepository = conferenceRepository
        )

        viewModel.uiState.test {
            var state = awaitItem()
            assertTrue(state is ConferenceDataUiState.Loading)

            state = awaitItem()
            assertTrue(state is ConferenceDataUiState.Loaded)

            assertEquals(1, state.sessionInfosByDay.size)
            assertEquals(Day.Day2, state.sessionInfosByDay[0].day)
            assertEquals(Day.Day2, state.day)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadConferenceInfo exception handling should not crash`() = runTest(testDispatcher) {
        val conferenceRepository: ConferenceRepository by inject()
        (conferenceRepository as FakeConferenceRepository).throwLoadInfoException = true

        val viewModel = MainViewModel(
            savedStateHandle = SavedStateHandle(),
            conferenceRepository = conferenceRepository
        )

        viewModel.uiState.test {
            var state = awaitItem()
            assertTrue(state is ConferenceDataUiState.Loading)

            state = awaitItem()
            assertTrue(state is ConferenceDataUiState.Loaded)

            assertTrue(state.sessionInfos.isEmpty())

            cancelAndIgnoreRemainingEvents()
        }
    }
}
