package com.example.red30

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.red30.data.ConferenceRepository
import com.example.red30.data.Day
import com.example.red30.data.SessionInfo
import com.example.red30.data.fake2
import com.example.red30.data.favorites
import com.example.red30.data.sessionInfosByDay
import com.example.red30.fakes.FakeConferenceRepository
import com.example.red30.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
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
            assertThat(state.isLoading).isTrue()

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getUiState loaded state after initialization`() = runTest(testDispatcher) {
        val viewModel: MainViewModel by inject()

        viewModel.uiState.test {
            var state = awaitItem()
            assertThat(state.isLoading).isTrue()

            state = awaitItem()
            assertThat(state.isLoading).isFalse()

            assertThat(state.sessionInfos.size).isEqualTo(2)
            assertThat(state.day).isEqualTo(Day.Day1)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getUiState loaded state after day change`() = runTest(testDispatcher) {
        val viewModel: MainViewModel by inject()

        viewModel.setDay(day = Day.Day2)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.sessionInfosByDay.size).isEqualTo(1)
        assertThat(SessionInfo.fake2()).isIn(state.sessionInfosByDay)
        assertThat(state.day).isEqualTo(Day.Day2)
    }

    @Test
    fun `getSessionInfoById valid session ID`() = runTest(testDispatcher) {
        val viewModel: MainViewModel by inject()

        viewModel.getSessionInfoById(sessionId = 1)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.selectedSession).isNotNull()
        assertThat(state.selectedSession!!.session.id).isEqualTo(1)
    }

    @Test
    fun `getSessionInfoById invalid session ID`() = runTest(testDispatcher) {
        val viewModel: MainViewModel by inject()

        viewModel.getSessionInfoById(sessionId = 55)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.selectedSession).isNull()
    }

    @Test
    fun `toggleFavorite valid session ID`() = runTest(testDispatcher) {
        val viewModel: MainViewModel by inject()

        viewModel.toggleFavorite(sessionId = 2)
        advanceUntilIdle()
        viewModel.toggleFavorite(sessionId = 1)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.favorites.size).isEqualTo(2)
        assertThat(state.favorites[0].session.id).isEqualTo(1)
    }

    @Test
    fun `toggleFavorite invalid session ID`() = runTest(testDispatcher) {
        val viewModel: MainViewModel by inject()

        viewModel.toggleFavorite(sessionId = 55)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.favorites.isEmpty()).isTrue()
    }

    @Test
    fun `toggleFavorite empty favorites list`() = runTest(testDispatcher) {
        val viewModel: MainViewModel by inject()

        viewModel.toggleFavorite(sessionId = 1)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.favorites.size).isEqualTo(1)
        assertThat(state.favorites[0].session.id).isEqualTo(1)
        assertThat(state.snackbarMessage).isEqualTo(R.string.save_remove_favorites_success)
    }

    @Test
    fun `toggleFavorite should remove existing item`() = runTest(testDispatcher) {
        val viewModel: MainViewModel by inject()

        viewModel.toggleFavorite(sessionId = 1)
        advanceUntilIdle()
        viewModel.toggleFavorite(sessionId = 1)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.favorites.isEmpty()).isTrue()
    }

    @Test
    fun `toggleFavorite exception handling should not crash`() = runTest(testDispatcher) {
        val conferenceRepository: ConferenceRepository by inject()
        (conferenceRepository as FakeConferenceRepository).apply {
            throwFavoritesException = true
        }

        val viewModel = MainViewModel(
            savedStateHandle = SavedStateHandle(),
            conferenceRepository = conferenceRepository
        )

        viewModel.toggleFavorite(sessionId = 1)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.favorites.isEmpty()).isTrue()
        assertThat(state.snackbarMessage).isEqualTo(R.string.save_remove_favorites_error)
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
            assertThat(state.isLoading).isTrue()

            state = awaitItem()
            assertThat(state.isLoading).isFalse()
            assertThat(state.sessionInfosByDay.size).isEqualTo(1)
            assertThat(state.sessionInfosByDay[0].day).isEqualTo(Day.Day2)
            assertThat(state.day).isEqualTo(Day.Day2)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadConferenceInfo exception handling should not crash`() = runTest(testDispatcher) {
        val conferenceRepository: ConferenceRepository by inject()
        (conferenceRepository as FakeConferenceRepository).apply {
            throwLoadInfoException = true
        }

        val viewModel = MainViewModel(
            savedStateHandle = SavedStateHandle(),
            conferenceRepository = conferenceRepository
        )

        viewModel.uiState.test {
            var state = awaitItem()
            assertThat(state.isLoading).isTrue()

            state = awaitItem()
            assertThat(state.isLoading).isFalse()
            assertThat(state.sessionInfos.isEmpty()).isTrue()
            assertThat(state.errorMessage).isNotNull()

            cancelAndIgnoreRemainingEvents()
        }
    }
}
