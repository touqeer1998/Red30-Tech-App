package com.example.sample

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.red30.compose.theme.Red30TechTheme
import com.example.red30.data.ConferenceRepository
import kotlinx.coroutines.launch
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel

private const val TAG = "MainViewModel"

private class MainViewModel(
    private val conferenceRepository: ConferenceRepository
): ViewModel() {

    init {
        viewModelScope.launch {
            val sessionInfos = conferenceRepository.loadConferenceInfo()
            Log.i(TAG, "initialized: $sessionInfos")
        }
    }

    fun doSomething() {}
}

@Composable
private fun MainApp(modifier: Modifier = Modifier) {
    KoinAndroidContext {
        val viewModel = koinViewModel<MainViewModel>(
            viewModelStoreOwner = LocalContext.current as ComponentActivity
        )

        Scaffold { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                ElevatedButton(
                    onClick = { viewModel.doSomething() }
                ) {
                    Text("do something")
                }
            }
        }
    }
}

@Preview
@Composable
private fun AppPreview() {
    Red30TechTheme {
        MainApp()
    }
}
