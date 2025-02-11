package com.example.sample

import androidx.lifecycle.ViewModel

// our class that uses a dependency
class SampleViewModel: ViewModel() {
    val repository: DefaultSampleRepository = DefaultSampleRepository()

    init {
        repository.doSomething()
    }
}

class DefaultSampleRepository() {
    fun doSomething() {}
}
