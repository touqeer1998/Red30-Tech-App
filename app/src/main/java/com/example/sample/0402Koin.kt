package com.example.sample

import androidx.lifecycle.ViewModel

// our class that uses a dependency
class SampleViewModel(repository: SampleRepository ): ViewModel() {
    init {
        repository.doSomething()
    }
}

interface SampleRepository {
    fun doSomething()
}

class DefaultSampleRepository() : SampleRepository {
    override fun doSomething() {}
}

class FakeSampleRepository() : SampleRepository {
    override fun doSomething() {}
}

val viewModel = SampleViewModel(DefaultSampleRepository())
val viewModelInTests = SampleViewModel(FakeSampleRepository())
